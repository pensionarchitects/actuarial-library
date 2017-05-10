package be.pensionarchitects.actuarial;

import org.apache.commons.lang.Validate;

import be.pensionarchitects.common.time.Periodicity;

public final class LifeInsurance {

	public enum PaymentTime {
		MIDDLE_OF_YEAR,
		END_OF_YEAR,
		CONTINUOUS;
	}

	/**
	 * Return the present value of an insurance whose benefits are payable on death, no matter when the insured dies.
	 * The insurance pays a benefit of €1.00 at the end of the year of death (END_OF_YEAR), in the middle of the year of
	 * death (MIDDLE_OF_YEAR), or at the date of death (CONTINUOUS).
	 *
	 * @param table the mortality table to use
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 * @param annualInterestRate the annual interest rate
	 * @param deathLoadings
	 * @param deathLoadingsAtBeginningOfPeriod
	 * @param paymentTime
	 */
	public static double A_x(
			MortalityTable table, int ageYears, int ageMonths,
			double annualInterestRate, double deathLoadings, boolean deathLoadingsAtBeginningOfPeriod,
			PaymentTime paymentTime) {

		Validate.notNull(table, "The table argument must not be null");
		Validations.validateAge(table, ageYears, ageMonths);

		double ax = Annuity.ax(
				table, ageYears, ageMonths, annualInterestRate, 0, Periodicity.ANNUM, deathLoadingsAtBeginningOfPeriod);

		double a_xtemp = 0;

		switch (paymentTime) {
		case MIDDLE_OF_YEAR:
			for (int i = ageYears; i < table.getMaxAge(); i++) {
				a_xtemp += (table.lx(i, ageMonths) - table.lx(i + 1, ageMonths))
						/ (table.lx(ageYears, ageMonths) * Math.pow(1. + annualInterestRate, i + 0.5 - ageYears));
			}
			return a_xtemp + deathLoadings * ax;
		case END_OF_YEAR:
			for (int i = ageYears; i < table.getMaxAge(); i++) {
				a_xtemp += (table.lx(i, ageMonths) - table.lx(i + 1, ageMonths))
						/ (table.lx(ageYears, ageMonths) * Math.pow(1. + annualInterestRate, i + 1. - ageYears));
			}
			return a_xtemp + deathLoadings * ax;
		case CONTINUOUS:
			return annualInterestRate
					* A_x(
							table, ageYears, ageMonths, annualInterestRate,
							0, deathLoadingsAtBeginningOfPeriod, PaymentTime.END_OF_YEAR)
					/ Math.log(1. + annualInterestRate)
					+ deathLoadings * ax;
		default:
			throw new IllegalArgumentException("Unknown value " + paymentTime + "for argument paymentTime");
		}
	}

	private LifeInsurance() {
	}
}
