package be.pensionarchitects.actuarial;

import org.apache.commons.lang.Validate;

import be.pensionarchitects.common.time.Periodicity;

public final class Annuity {

	public static int[] getMaxPeriod(
			MortalityTable table1, int ageYears1, int ageMonths1,
			MortalityTable table2, int ageYears2, int ageMonths2) {

		int[] p1 = getMaxPeriod(table1, ageYears1, ageMonths1);
		int[] p2 = getMaxPeriod(table2, ageYears2, ageMonths2);

		//check years
		if (p1[0] < p2[0]) {
			return p1;
		}
		if (p1[0] > p2[0]) {
			return p2;
		}
		//check months
		if (p1[1] < p2[1]) {
			return p1;
		}
		if (p1[1] > p2[1]) {
			return p2;
		}

		return p1;
	}

	public static int[] getMaxPeriod(MortalityTable table, int ageYears, int ageMonths) {

		Validate.notNull(table, "The table argument must not be null");
		Validations.validateAge(table, ageYears, ageMonths);

		int[] maxTablePeriod = table.getMaxPeriod(ageYears, ageMonths);
		int periodYears = maxTablePeriod[0] - 2; //-1 period is increased by 1 for interpolation, -1 first period is 1 not zero
		int periodMonths = maxTablePeriod[1];
		if (periodYears < 0) {
			return new int[] { 0, 0 };
		}

		return new int[] { periodYears, periodMonths };
	}

	/**
	 * Return the present value of a whole-life periodic payment of €1. The annuity is payable only in the case the
	 * insured is still alive. The annuities are paid at the beginning or the end of the period. The indexation of the
	 * periodic payment starts immediately after the first payment.
	 *
	 * @param table the mortality table to use
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 * @param annualInterestRate the annual interest rate
	 * @param annualIndexRate the annual index rate
	 * @param paymentPeriodicity
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double ax(
			MortalityTable table, int ageYears, int ageMonths,
			double annualInterestRate, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod) {

		Validate.notNull(table, "The table argument must not be null");
		Validations.validateAge(table, ageYears, ageMonths);

		int[] period = getMaxPeriod(table, ageYears, ageMonths);
		return axn(
				table, ageYears, ageMonths, period[0], period[1],
				annualInterestRate, annualIndexRate, paymentPeriodicity,
				beginningOfPeriod);
	}

	/**
	 * Return the present value of a temporary periodic payment of €1. The annuity is payable only in the case the
	 * insured is still alive. In any case the annuity ends after the maturity period. The annuities are paid at the
	 * beginning or the end of the period. The indexation of the periodic payment starts immediately after the first
	 * payment.
	 *
	 * @param table the mortality table to use
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 * @param periodYears the period in years
	 * @param periodMonths the period in months
	 * @param annualInterestRate the annual interest rate
	 * @param annualIndexRate the annual index rate
	 * @param paymentPeriodicity
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double axn(
			MortalityTable table, int ageYears, int ageMonths, int periodYears, int periodMonths,
			double annualInterestRate, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod) {

		Validate.notNull(table, "The table argument must not be null");
		Validations.validatePeriod(table, ageYears, ageMonths, periodYears, periodMonths);
		Validate.notNull(paymentPeriodicity, "The paymentPeriodicity argument must not be null");
		
		return (1 - ageMonths / 12.) * (1 - periodMonths / 12.) * axnBase(table, ageYears, periodYears,
						annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				+ (1 - ageMonths / 12.) * (periodMonths / 12.) * axnBase(table, ageYears, periodYears + 1,
						annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				+ (ageMonths / 12.) * (1 - periodMonths / 12.) * axnBase(table, ageYears + 1, periodYears,
						annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				+ (ageMonths / 12.) * (periodMonths / 12.) * axnBase(table, ageYears + 1, periodYears + 1,
						annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod);
	}

	/**
	 * Return the present value of a whole-life periodic payment of €1. The annuity is payable only in the case the
	 * insured and his partner are still alive. When the insured or the partner dies, the annuity is not payable
	 * anymore. The annuities are paid at the beginning or the end of the period. The indexation of the periodic
	 * payment starts immediately after the first payment.
	 *
	 * @param table1 the mortality table of the retiree
	 * @param ageYears1 the age in years of the retiree
	 * @param ageMonths1 the age in months of the retiree
	 * @param table2 the mortality table of the partner
	 * @param ageYears2 the age in years of the partner
	 * @param ageMonths2 the age in months of the partner
	 * @param annualInterestRate the annual interest rate
	 * @param annualIndexRate the annual index rate
	 * @param paymentPeriodicity
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double axy(
			MortalityTable table1, int ageYears1, int ageMonths1,
			MortalityTable table2, int ageYears2, int ageMonths2,
			double annualInterestRate, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod) {

		Validate.notNull(table1, "The table1 argument must not be null");
		Validations.validateAge(table1, ageYears1, ageMonths1);
		Validate.notNull(table2, "The table2 argument must not be null");
		Validations.validateAge(table2, ageYears2, ageMonths2);

		int[] period = getMaxPeriod(table1, ageYears1, ageMonths1, table2, ageYears2, ageMonths2);
		return axyn(
				table1, ageYears1, ageMonths1,
				table2, ageYears2, ageMonths2, period[0], period[1],
				annualInterestRate, annualIndexRate, paymentPeriodicity,
				beginningOfPeriod);
	}

	/**
	 * Return the present value of a temporary periodic payment of €1. The annuity is payable only in the case the
	 * insured and his partner are still alive. When the insured or the partner dies, the annuity is not payable
	 * anymore. In any case the annuity ends after the maturity period. The annuities are paid at the beginning or the
	 * end of the period. The indexation of the periodic payment starts immediately after the first payment.
	 *
	 * @param table1 the mortality table of the retiree
	 * @param ageYears1 the age in years of the retiree
	 * @param ageMonths1 the age in months of the retiree
	 * @param table2 the mortality table of the partner
	 * @param ageYears2 the age in years of the partner
	 * @param ageMonths2 the age in months of the partner
	 * @param periodYears the period in years
	 * @param periodMonths the period in months
	 * @param annualInterestRate the annual interest rate
	 * @param annualIndexRate the annual index rate
	 * @param paymentPeriodicity
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double axyn(
			MortalityTable table1, int ageYears1, int ageMonths1,
			MortalityTable table2, int ageYears2, int ageMonths2, int periodYears, int periodMonths,
			double annualInterestRate, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod) {

		Validate.notNull(table1, "The table1 argument must not be null");
		Validations.validatePeriod(table1, ageYears1, ageMonths1, periodYears, periodMonths);
		Validate.notNull(table2, "The table2 argument must not be null");
		Validations.validatePeriod(table2, ageYears2, ageMonths2, periodYears, periodMonths);
		Validate.notNull(paymentPeriodicity, "The paymentPeriodicity argument must not be null");

		double t = ageMonths1 / 12.;
		double z = ageMonths2 / 12.;
		int n = periodYears;
		double v = periodMonths / 12.;

		double axyn = (1. - t) * (1. - z) * (1. - v) * axynBase(
							table1, ageYears1, table2, ageYears2,
							n, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
					+ (1. - t) * (1. - z) * v * axynBase(
							table1, ageYears1, table2, ageYears2,
							n + 1, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				    + (1. - t) * z * (1. - v) * axynBase(
				    		table1, ageYears1, table2, ageYears2 + 1,
				    		n, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
					+ (1. - t) * z * v * axynBase(
							table1, ageYears1, table2, ageYears2 + 1, 
							n + 1, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				    + t * (1. - z) * (1. - v) * axynBase(
				    		table1, ageYears1 + 1, table2, ageYears2, 
				    		n, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				    + t * (1. - z) * v * axynBase(
				    		table1, ageYears1 + 1, table2, ageYears2, 
				    		n + 1, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				    + t * z * (1. - v) * axynBase(
				    		table1, ageYears1 + 1, table2, ageYears2 + 1, 
				    		n, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod)
				    + t * z * v * axynBase(
				    		table1, ageYears1 + 1, table2, ageYears2 + 1, 
				    		n + 1, annualInterestRate, annualIndexRate, paymentPeriodicity, beginningOfPeriod);
		return axyn;
	}

	/**
	 * Return the present value of a whole-life periodic payment of €1. The annuity is payable only in the case the
	 * insured or his partner is still alive. When the insured dies, a portion of the annuity is payable until his
	 * partner dies. The annuities are paid at the beginning or the end of the period. The indexation of the periodic
	 * payment starts immediately after the first payment.
	 *
	 * @param table1 the mortality table of the retiree
	 * @param ageYears1 the age in years of the retiree
	 * @param ageMonths1 the age in months of the retiree
	 * @param table2 the mortality table of the partner
	 * @param ageYears2 the age in years of the partner
	 * @param ageMonths2 the age in months of the partner
	 * @param reversion
	 * @param annualInterestRate the annual interest rate
	 * @param annualIndexRate the annual index rate
	 * @param paymentPeriodicity
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double axrev(
			MortalityTable table1, int ageYears1, int ageMonths1,
			MortalityTable table2, int ageYears2, int ageMonths2,
			double reversion, double annualInterestRate, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod) {

		return ax(
				table1, ageYears1, ageMonths1,
				annualInterestRate, annualIndexRate, paymentPeriodicity,
				beginningOfPeriod)
				+ reversion * (ax(
						table2, ageYears2, ageMonths2,
						annualInterestRate, annualIndexRate, paymentPeriodicity,
						beginningOfPeriod)
						- axy(
								table1, ageYears1, ageMonths1,
								table2, ageYears2, ageMonths2,
								annualInterestRate, annualIndexRate, paymentPeriodicity,
								beginningOfPeriod));
	}

	private static double axynBase(
			MortalityTable table1, int ageYears1, 
			MortalityTable table2, int ageYears2, int periodYears, 
			double annualInterestRate, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod) {
		
		double numPeriodsInYear = (double)paymentPeriodicity.getNumPeriodsInYear();
		int duration = periodYears * paymentPeriodicity.getNumPeriodsInYear();
		int ageYears1J = ageYears1;
		int ageYears2J = ageYears2;
		int ageMonths1J = paymentPeriodicity.inMonths();
		int ageMonths2J = paymentPeriodicity.inMonths();
		double lx01 = table1.lx(ageYears1J, 0);
		double lx02 = table2.lx(ageYears2J, 0);

		double axyn = 0;

		if (beginningOfPeriod) {
			axyn++;
			duration--;
		}

		for (int j = 1; j <= duration; j++) {

			if (ageMonths1J >= 12) {
				ageMonths1J -= 12;
				ageYears1J++;
			}

			if (ageMonths2J >= 12) {
				ageMonths2J -= 12;
				ageYears2J++;
			}

			double powInterest= j / numPeriodsInYear;
			double powIndex  = beginningOfPeriod ? powInterest : (j - 1) / numPeriodsInYear;

			axyn += table1.lx(ageYears1J, ageMonths1J) * table2.lx(ageYears2J, ageMonths2J)
					* Math.pow(1. + annualIndexRate, powIndex)
					/ (lx01 * lx02 * Math.pow(1. + annualInterestRate, powInterest));

			ageMonths1J += paymentPeriodicity.inMonths();
			ageMonths2J += paymentPeriodicity.inMonths();
		}

		return axyn;
	}

	private static double axnBase(
			MortalityTable table, int ageYears, int periodYears,
			double annualInterestRate, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod) {
		double numPeriodsInYear = (double)paymentPeriodicity.getNumPeriodsInYear();
		int numPayments = periodYears * paymentPeriodicity.getNumPeriodsInYear();
		// time of first payment
		int ageJY = ageYears;
		int ageJM = paymentPeriodicity.inMonths();
		double lx0 = table.lx(ageJY, 0);
		
		
		double axn = 0;
		if (beginningOfPeriod) {
			axn++;
			numPayments--;
		}

		for (int j = 1; j <= numPayments; j++) {
			// normalize payment time
			if (ageJM >= 12) {
				ageJM -= 12;
				ageJY++;
			}

			double powInterest= j / numPeriodsInYear;
			double powIndex  = beginningOfPeriod ? powInterest : (j - 1) / numPeriodsInYear;

			axn += (table.lx(ageJY, ageJM) * Math.pow(1 + annualIndexRate, powIndex))
					/ (lx0 * Math.pow(1 + annualInterestRate, powInterest));

			ageJM += paymentPeriodicity.inMonths();
		}

		return axn;
	}
	
	private Annuity() {
	}
}
