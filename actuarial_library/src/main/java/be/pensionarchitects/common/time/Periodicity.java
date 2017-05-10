package be.pensionarchitects.common.time;

/**
 * Specifies at what intervals an event, such as an interest calculation, takes place.
 */
public enum Periodicity {

	/**
	 * Once a year.
	 */
	ANNUM(1),

	/**
	 * Every semester (twice a year).
	 */
	SEMESTER(2),

	/**
	 * Every quarter (four times a year).
	 */
	QUARTER(4),

	/**
	 * Every month (twelve times a year).
	 */
	MONTH(12);

	private static final int NUM_MONTHS_IN_YEAR = 12;

	private int numPeriodsInYear;

	Periodicity(int numPeriodsInYear) {
		this.numPeriodsInYear = numPeriodsInYear;
	}

	/**
	 * Get the number of months in this period.
	 */
	public int inMonths() {
		return NUM_MONTHS_IN_YEAR / getNumPeriodsInYear();
	}

	/**
	 * Get the number of periods in a year that corresponds to this periodicity.
	 */
	public int getNumPeriodsInYear() {
		return numPeriodsInYear;
	}

	/**
	 * Get the periodicity that corresponds with the specified number of periods in a year.
	 *
	 * @param numPeriodsInYear the number of periods in a year
	 * @throws IllegalArgumentException if there is no corresponding periodicity
	 */
	public static Periodicity withNumPeriodsInYear(int numPeriodsInYear) {
		for (Periodicity periodicity : Periodicity.values()) {
			if (periodicity.getNumPeriodsInYear() == numPeriodsInYear) {
				return periodicity;
			}
		}
		throw new IllegalArgumentException(
				"Cannot find a periodicity with " + numPeriodsInYear + " periods in a year");
	}
}
