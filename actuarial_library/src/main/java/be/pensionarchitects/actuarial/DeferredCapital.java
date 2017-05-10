package be.pensionarchitects.actuarial;

import org.apache.commons.lang.Validate;

import be.pensionarchitects.common.time.Periodicity;

public final class DeferredCapital {

	/**
	 * Return the present value of a deferred pension amount.
	 *
	 * @param table the mortality table to use
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 * @param periodYears the period in years
	 * @param periodMonths the period in months
	 * @param annualInterestRate the annual interest rate
	 * @param lifeLoadings
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double nEx(
			MortalityTable table, int ageYears, int ageMonths, int periodYears, int periodMonths,
			double annualInterestRate, double lifeLoadings, boolean beginningOfPeriod) {

		Validate.notNull(table, "The table argument must not be null");
		Validations.validatePeriod(table, ageYears, ageMonths, periodYears, periodMonths);

		return (1. - ageMonths / 12.) * (1. - periodMonths / 12.)
					* mEx(table, ageYears, periodYears, annualInterestRate, lifeLoadings, beginningOfPeriod)
				+ (1. - ageMonths / 12.) * (periodMonths / 12.)
					* mEx(table, ageYears, periodYears + 1, annualInterestRate, lifeLoadings, beginningOfPeriod)
				+ (ageMonths / 12.) * (1. - periodMonths / 12.)
					* mEx(table, ageYears + 1, periodYears, annualInterestRate, lifeLoadings, beginningOfPeriod)
				+ (ageMonths / 12.) * (periodMonths / 12.)
					* mEx(table, ageYears + 1, periodYears + 1, annualInterestRate, lifeLoadings, beginningOfPeriod);
	}

	private static double mEx(
			MortalityTable table, int ageYears, int periodYears,
			double annualInterestRate, double lifeLoadings, boolean beginningOfPeriod) {

		double axn = Annuity.axn(
				table, ageYears, 0, periodYears, 0,
				annualInterestRate, 0, Periodicity.ANNUM, beginningOfPeriod);

		return table.lx(ageYears + periodYears, 0)
				/ (table.lx(ageYears, 0) * Math.pow(1 + annualInterestRate, periodYears))
				+ lifeLoadings * axn;
	}

	private DeferredCapital() {
	}
}
