package be.pensionarchitects.common.time;

/**
 * Specifies how to round a date. To be used in specific contexts such as calculating a retirement date.
 */
public enum DateRoundingMode {

	/**
	 * Round to the first of the month. For instance, 2000-05-30 is rounded to 2000-05-01.
	 */
	FIRST_OF_MONTH,

	/**
	 * Round to the first of the next month. The month is always incremented, even when the date is already the first
	 * of the month. For instance, 2000-05-01 rounds to 2000-06-01.
	 */
	FIRST_OF_NEXT_MONTH,

	/**
	 * Round to the first of the month if the day is before the 15th, and to the first of next month otherwise. For
	 * instance, 2000-05-14 rounds to 2000-05-01, while 2000-05-15 rounds to 2000-06-01.
	 */
	MID_MONTH_PIVOT,
}
