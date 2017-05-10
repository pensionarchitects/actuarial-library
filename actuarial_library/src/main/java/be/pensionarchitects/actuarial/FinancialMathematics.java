package be.pensionarchitects.actuarial;

import be.pensionarchitects.common.time.Periodicity;

public final class FinancialMathematics {

	/**
	 * Return the present value of €1 at maturity date.
	 *
	 * @param annualInterestRate the annual interest rate
	 * @param maturity
	 */
	public static double vn(double annualInterestRate, double maturity) {
		return 1. / Math.pow(1. + annualInterestRate, maturity);
	}

	/**
	 * Return the future value of a periodic payment of €1. The annuities are paid at the beginning or the end of the
	 * period. The indexation of the periodic payment starts immediately after the first payment.
	 *
	 * @param maturity
	 * @param annualInterestRate the annual interest rate
	 * @param annualIndexRate the annual index rate
	 * @param paymentPeriodicity
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double sn(
			double maturity, double annualInterestRate, double annualIndexRate,
			Periodicity paymentPeriodicity, boolean beginningOfPeriod) {

		if (beginningOfPeriod) {
			return Math.pow(1. + annualInterestRate, 1. / paymentPeriodicity.getNumPeriodsInYear())
					* sn(maturity, annualInterestRate, annualIndexRate, paymentPeriodicity, false);
		} else {
			if (annualInterestRate == 0) {
				return maturity;
			} else {
				double k = (1. + annualInterestRate) / (1. + annualIndexRate) - 1.;
				double kcut = paymentPeriodicity.getNumPeriodsInYear()
						* (Math.pow(1. + k, 1. / paymentPeriodicity.getNumPeriodsInYear()) - 1.);
				return paymentPeriodicity.getNumPeriodsInYear()
						* Math.pow(
								1. + annualIndexRate,
								(paymentPeriodicity.getNumPeriodsInYear() * maturity - 1.)
										/ paymentPeriodicity.getNumPeriodsInYear())
						* (Math.pow(1. + k, maturity) - 1.)
						/ kcut;
			}
		}
	}

	/**
	 * Return the present value of a periodic payment of €1. The annuities are paid at the beginning or the end of the
	 * period. The indexation of the periodic payment starts immediately after the first payment.
	 * @param maturity
	 * @param annualInterestRate the annual interest rate
	 * @param annualIndexRate the annual index rate
	 * @param paymentPeriodicity
	 * @param beginningOfPeriod {@code true} if payments are made at the beginning of the month, {@code false} if
	 *        payments are made at the end of the month
	 */
	public static double an(
			double maturity, double annualInterestRate, double annualIndexRate,
			Periodicity paymentPeriodicity, boolean beginingOfPeriod) {

		if (beginingOfPeriod) {
			return Math.pow(1. + annualInterestRate, 1. / paymentPeriodicity.getNumPeriodsInYear())
					* an(maturity, annualInterestRate, annualIndexRate, paymentPeriodicity, false);
		} else {
			if (annualInterestRate == 0) {
				return maturity;
			} else {
				double k = (1. + annualInterestRate) / (1. + annualIndexRate) - 1.;
				double kCut = paymentPeriodicity.getNumPeriodsInYear()
						* (Math.pow(1. + k, 1. / paymentPeriodicity.getNumPeriodsInYear()) - 1.);
				return paymentPeriodicity.getNumPeriodsInYear()
						* (1. / Math.pow(1. + annualIndexRate, 1. / paymentPeriodicity.getNumPeriodsInYear()))
						* (1. - vn(k, maturity))
						/ kCut;
			}
		}
	}

	private FinancialMathematics() {
	}
}
