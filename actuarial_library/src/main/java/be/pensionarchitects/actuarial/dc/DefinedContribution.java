package be.pensionarchitects.actuarial.dc;

import java.math.BigDecimal;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;

import be.pensionarchitects.common.math.NumberUtils;
import be.pensionarchitects.common.time.DateRoundingMode;
import be.pensionarchitects.common.time.DateUtils;
import be.pensionarchitects.common.time.YearMonthPeriod;

public final class DefinedContribution {

	/**
	 * Bereken de reserves ("account balance") die worden opgebouwd door de storting van bijdragen
	 * en de reeds opgebouwde reserves. De 2-dimensionale array is bv. [Jan/2009 .. May/2035][0..1]
	 * waarbij Jan/2009 de begindatum is, en May/2035 de pensioendatum voor deze persoon
	 * en 0 voor de opbouw van nieuwe bijdragen en 1 voor de opbouw van bestaande reserves
	 */
	public static CalculationResults calculate(CalculationArguments args) {

		Validate.notNull(args, "Calculation arguments are required");
		args.validate();

		//calculation date, newWorkPercentageFrom and newWorkPercentageTill are rounded to the first of the month
		LocalDate calculationDate = DateUtils.round(args.getCalculationDate(), DateRoundingMode.FIRST_OF_MONTH);
		LocalDate newWorkPercentageFrom = args.getNewWorkPercentage() == null ?
				null : DateUtils.round(args.getNewWorkPercentageFrom(), DateRoundingMode.FIRST_OF_MONTH);
		LocalDate newWorkPercentageTill = args.getNewWorkPercentage() == null ?
				null : DateUtils.round(args.getNewWorkPercentageTill(), DateRoundingMode.FIRST_OF_MONTH);

		int calculationYear = calculationDate.getYear();
		int monthsToRetirement = new YearMonthPeriod(
				DateUtils.round(calculationDate, DateRoundingMode.FIRST_OF_MONTH), args.getRetirementDate()).inMonths();
		double monthlyInterestRate = Math.pow(
				1 + args.getInterestRate().doubleValue() / (1 + args.getReserveLoadings().doubleValue()), 1. / 12.) - 1;

		//create arrays to hold results on a monthly basis. The start values are stored at index 0.
		double[][] accountBalance = new double[monthsToRetirement + 1][2];
		BigDecimal[][] reserveAmounts = new BigDecimal[monthsToRetirement + 1][2];
		BigDecimal[] monthlySalaries = new BigDecimal[monthsToRetirement + 1];

		accountBalance[0][ContributionTime.FUTURE.ordinal()] = 0.;
		accountBalance[0][ContributionTime.PAST.ordinal()] = args.getCurrentReserve().doubleValue();

		reserveAmounts[0][ContributionTime.FUTURE.ordinal()] =
			NumberUtils.round(accountBalance[0][ContributionTime.FUTURE.ordinal()]);
		reserveAmounts[0][ContributionTime.PAST.ordinal()] =
			NumberUtils.round(accountBalance[0][ContributionTime.PAST.ordinal()]);

		monthlySalaries[0] = BigDecimal.ZERO;

		//initialize workDate loop variable
		LocalDate workDate = calculationDate;

		for (int i = 1; i <= monthsToRetirement; i++) {

			int workYear = workDate.getYear();

			//we assume that salary and ceiling are indexed on the first of January

			double annualSalary = args.getMonthlySalary().doubleValue()
					* Math.pow(1 + args.getExpectedAnualSalaryIncrease().doubleValue(),
							workYear - calculationYear) * args.getSalaryMultiplier().doubleValue();

			double ceiling = args.getAnnualCeiling().doubleValue()
					* Math.pow(1 + args.getExpectedCeilingIncrease().doubleValue(),
							workYear - calculationYear);

			double ftSalary = args.getWorkPercentage().compareTo(BigDecimal.ZERO) == 0 ?
					0.0 : annualSalary / args.getWorkPercentage().doubleValue();
			double fixedAnnualContribution = Math.pow(1 + args.getExpectedAnualSalaryIncrease().doubleValue(),
					workYear - calculationYear) * args.getFixedAnnualContribution().doubleValue();

			double contributions = 0;

			if (!workDate.isAfter(args.getPreRetirementDate())) {
				double s1 = Math.min(ftSalary, ceiling);
				double s2 = Math.max(0, ftSalary - s1);
				contributions = (args.getContributionRateProvider().getRateS1(workDate).doubleValue() * s1
								+ args.getContributionRateProvider().getRateS2(workDate).doubleValue() * s2
								+ fixedAnnualContribution)
						/ args.getPaymentPeriodicity().getNumPeriodsInYear()
						* (1 - args.getPremiumLoadings().doubleValue());
			}

			double ftPct = args.getWorkPercentage().doubleValue();
			if (newWorkPercentageFrom != null
					&& !workDate.isBefore(newWorkPercentageFrom)
					&& workDate.isBefore(newWorkPercentageTill)) {
				ftPct = args.getNewWorkPercentage().doubleValue();
			}

			contributions = contributions * ftPct;

			// add a month of interest if contributions are paid in advance
			if (args.getAnnuityType() == AnnuityType.DUE) contributions *= (1 + monthlyInterestRate);

			accountBalance[i][ContributionTime.FUTURE.ordinal()] =
					(i % (12 / args.getPaymentPeriodicity().getNumPeriodsInYear()) == 0 ? contributions : 0)
				+ accountBalance[i - 1][ContributionTime.FUTURE.ordinal()] * (1 + monthlyInterestRate);
			accountBalance[i][ContributionTime.PAST.ordinal()] =
				accountBalance[i - 1][ContributionTime.PAST.ordinal()] * (1 + monthlyInterestRate);

			reserveAmounts[i][ContributionTime.FUTURE.ordinal()] =
				NumberUtils.round(accountBalance[i][ContributionTime.FUTURE.ordinal()]);
			reserveAmounts[i][ContributionTime.PAST.ordinal()] =
				NumberUtils.round(accountBalance[i][ContributionTime.PAST.ordinal()]);

			monthlySalaries[i] = NumberUtils.round(ftSalary / args.getSalaryMultiplier().doubleValue() * ftPct);

			workDate = workDate.plusMonths(1);
		}

		return new CalculationResults(reserveAmounts, monthlySalaries);
	}

	private DefinedContribution() {
	}
}
