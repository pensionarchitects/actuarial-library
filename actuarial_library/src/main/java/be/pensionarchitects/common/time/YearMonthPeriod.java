package be.pensionarchitects.common.time;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.YearMonth;

/**
 * <p>
 *   An immutable time period, expressed as a number of years and months. The class provides methods to calculate the
 *   total number of months (with each full year counting for 12 months) and the fractional number of years in a period.
 * </p>
 * <p>
 *   Even though some constructors are provided that accept {@link LocalDate} or {@link DateTime} instances, a
 *   {@link YearMonthPeriod} regards months as the smallest time instants and disregards the day and time fields.
 *   It can therefore be used to calculate the number of years and months between two specific months (For instance,
 *   2 years and 5 months between February 2000 and July 2002), but not between two dates. A Joda {@link Period} can be
 *   used for that purpose.
 * </p>
 */
public class YearMonthPeriod {

	private static final PeriodType PERIOD_TYPE = PeriodType.forFields(
			new DurationFieldType[] { DurationFieldType.years(), DurationFieldType.months() });

	private static final int NUM_MONTHS_IN_YEAR = 12;

	private final Period period;

	/**
	 * Create a new instance from the given start and end months. The end month is exlusive.
	 *
	 * @param start the start month of the period (inclusive)
	 * @param end the end month of the period (exclusive), not before the start month
	 */
	public YearMonthPeriod(YearMonth start, YearMonth end) {
		Validate.notNull(start, "Start date is required");
		Validate.notNull(end, "End date is required");
		Validate.isTrue(!end.isBefore(start), "End date must not be before start date");
		period = new Period(start, end, PERIOD_TYPE);
	}

	/**
	 * Create a new instance from the given start and end months, expressed by {@link LocalDate} instances. This method
	 * only uses the year and the month fields and disregards the dayOfMonth fields. The end month is exclusive.
	 *
	 * @param start the start month of the period (inclusive)
	 * @param end the end month of the period (exclusive), not before the start month
	 */
	public YearMonthPeriod(LocalDate start, LocalDate end) {
		this(toYearMonth(start), toYearMonth(end));
	}

	/**
	 * Create a new instance from the given start and end months, expressed by {@link DateTime} instances. This method
	 * only uses the year and the month fields and disregards the dayOfMonth and time fields. The end month is
	 * exclusive.
	 *
	 * @param start the start month of the period (inclusive)
	 * @param end the end month of the period (exclusive), not before the start month
	 */
	public YearMonthPeriod(DateTime start, DateTime end) {
		this(start.toLocalDate(), end.toLocalDate());
	}

	/**
	 * Create a new instance with the given number of months. For instance, a number of 27 would specify a period of
	 * 2 years and 3 months.
	 *
	 * @param months a positive integer that specifies the number of months
	 */
	public YearMonthPeriod(int months) {
		Validate.isTrue(months >= 0, "Months must be positive");
		period = new Period(months / NUM_MONTHS_IN_YEAR, months % NUM_MONTHS_IN_YEAR, 0, 0, 0, 0, 0, 0, PERIOD_TYPE);
	}

	/**
	 * Create a new instance with the given number of years and months. Any positive number is accepted for both
	 * fields; the period will always be normalized. For instance, when specifying 1 year and 15 months, a new instance
	 * of 2 years and 3 months will be returned.
	 *
	 * @param years a positive integer that specifies the number of years
	 * @param months a positive integer that specifies the number of months
	 */
	public YearMonthPeriod(int years, int months) {
		Validate.isTrue(years >= 0, "Years must be positive");
		Validate.isTrue(months >= 0, "Months must be positive");
		period = new Period(
				years + months / NUM_MONTHS_IN_YEAR, months % NUM_MONTHS_IN_YEAR, 0, 0, 0, 0, 0, 0, PERIOD_TYPE);
	}

	/**
	 * Get the number of whole years in this period.
	 */
	public int getYears() {
		return period.getYears();
	}

	/**
	 * Get the number of months in this period that do no make a full year. For instance, for a period of 2 years and 3
	 * months, this method would return 3.
	 */
	public int getMonths() {
		return period.getMonths();
	}

	/**
	 * Return this period expressed as a fractional number of years. This is calculated by dividing the total number
	 * of months (returned by {@link #inMonths()}) by 12. For instance, for a period of 2 years and 3 months, this
	 * method would return 2.25.
	 */
	public double inFractionalYears() {
		return inMonths() / (double)NUM_MONTHS_IN_YEAR;
	}

	/**
	 * Return the total number of months in this period. For instance, for a period of 2 years and 3 months, this
	 * method would return 27.
	 */
	public int inMonths() {
		return getYears() * NUM_MONTHS_IN_YEAR + getMonths();
	}

	/**
	 * Return the equivalent Joda {@link Period}.
	 */
	public Period toPeriod() {
		return period;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(8069, 8117).append(period).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof YearMonthPeriod)) return false;
		YearMonthPeriod other = (YearMonthPeriod)obj;
		return period.equals(other.period);
	}

	@Override
	public String toString() {
		return period.toString();
	}

	private static YearMonth toYearMonth(LocalDate date) {
		return new YearMonth(date.getYear(), date.getMonthOfYear());
	}
}
