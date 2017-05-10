package be.pensionarchitects.actuarial.dc;

import java.math.BigDecimal;

import org.apache.commons.lang.Validate;

import be.pensionarchitects.common.time.YearMonthPeriod;

public class CalculationResults {

	private final YearMonthPeriod calculationPeriod;
	private final BigDecimal[][] reserveAmounts;
	private final BigDecimal[] monthlySalaries;

	/**
	 * Create a {@link CalculationResults} instance with the given results of a simulation.
	 *
	 * @param reserveAmounts a multidimensional array containing the evolution of the reserve on a monthly basis
	 *     (first dimension). Past and future contributions are regarded separately (second dimension). The values at
	 *     index 0 (first dimension) contain the reserve balance at the start of the simulation. The calculated values
	 *     start at index 1 (first dimension).
	 * @param monthlySalaries an array containing the evolution of the monthly salary on a monthly basis. The values at
	 *     index 0 contain the monthly salary at the start of the simulation. The calculated values start at index 1.
	 * @throws IllegalArgumentException if any of the arrays is null or contains null values, or if the arrays are of
	 *     different length (first dimension).
	 */
	CalculationResults(BigDecimal[][] reserveAmounts, BigDecimal[] monthlySalaries) {
		Validate.noNullElements(reserveAmounts,
				"The reserveAmounts argument must not be null nor contain null values");
		Validate.noNullElements(monthlySalaries,
				"The monthlySalaries argument must not be null nor contain null values");
		Validate.isTrue(reserveAmounts.length == monthlySalaries.length,
				"The first dimension of the reserveAmounts array " +
				"must have the same length as the monthlySalaries array");
		// the arrays contain an additional element for the values at the start of the simulation
		this.calculationPeriod = new YearMonthPeriod(reserveAmounts.length - 1);
		this.reserveAmounts = reserveAmounts;
		this.monthlySalaries = monthlySalaries;
	}

	public YearMonthPeriod getCalculationPeriod() {
		return calculationPeriod;
	}

	public BigDecimal getFinalReserveAmount() {
		return getReserveAmount(calculationPeriod.inMonths());
	}

	public BigDecimal getFinalReserveAmount(ContributionTime contributionTime) {
		return getReserveAmount(calculationPeriod.inMonths(), contributionTime);
	}

	public BigDecimal getReserveAmount(int month) {
		return getReserveAmount(month, ContributionTime.PAST).add(getReserveAmount(month, ContributionTime.FUTURE));
	}

	public BigDecimal getReserveAmount(int month, ContributionTime contributionTime) {
		Validate.notNull(contributionTime, "The contributionTime argument must not be null");
		Validate.isTrue(month >= 0 && month <= calculationPeriod.inMonths(),
				"The month argument must be between 0 and " + calculationPeriod.inMonths() + " (inclusive)");
		return reserveAmounts[month][contributionTime.ordinal()];
	}

	public BigDecimal getLastMonthlySalary() {
		return getMonthlySalary(calculationPeriod.inMonths());
	}

	public BigDecimal getMonthlySalary(int month) {
		Validate.isTrue(month >= 0 && month <= calculationPeriod.inMonths(),
				"The month argument must be between 0 and " + calculationPeriod.inMonths() + " (inclusive)");
		return monthlySalaries[month];
	}
}
