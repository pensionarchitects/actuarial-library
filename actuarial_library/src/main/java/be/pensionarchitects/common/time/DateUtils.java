package be.pensionarchitects.common.time;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateUtils {

	private static final DateTimeFormatter DATE_TIME_FMT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter DATE_FMT = DateTimeFormat.forPattern("yyyy-MM-dd");

	private static final String MSG_BOTH_DATES_REQ = "Both dates are required";
	private static final int PIVOT_DAY = 15;

	/**
	 * Return the default date formatter.
	 */
	public static DateTimeFormatter defaultDateFormat() {
		return DATE_FMT;
	}

	/**
	 * Return the default date/time formatter.
	 */
	public static DateTimeFormatter defaultDateTimeFormat() {
		return DATE_TIME_FMT;
	}

	/**
	 * Format the given {@link DateTime} instant using the {@link #defaultDateTimeFormat() default date/time formatter}.
	 */
	public static String formatDateTime(DateTime datetime) {
		return DATE_TIME_FMT.print(datetime);
	}

	/**
	 * Format the given {@link LocalDate} using the {@link #defaultDateFormat() default date formatter}.
	 */
	public static String formatDate(LocalDate date) {
		return DATE_FMT.print(date);
	}

	/**
	 * Format the given {@link DateTime} instant using the {@link #defaultDateFormat() default date formatter},
	 * ignoring the time fields.
	 */
	public static String formatDate(DateTime date) {
		return DATE_FMT.print(date);
	}

	/**
	 * Round the given date to the month using the specified {@link DateRoundingMode rounding mode}. To be used in
	 * specific contexts such as calculating a retirement date.
	 *
	 * @param date the date to round
	 * @param roundingMode the rounding mode to use
	 * @return the rounded date value
	 */
	public static LocalDate round(LocalDate date, DateRoundingMode roundingMode) {

		Validate.notNull(date, "The date argument must not be null");
		Validate.notNull(roundingMode, "The roundingMode argument must not be null");

		switch (roundingMode) {
		case FIRST_OF_MONTH:
			return date.withDayOfMonth(1);
		case FIRST_OF_NEXT_MONTH:
			return date.plusMonths(1).withDayOfMonth(1);
		case MID_MONTH_PIVOT:
			if (date.getDayOfMonth() < PIVOT_DAY) {
				return date.withDayOfMonth(1);
			} else {
				return date.plusMonths(1).withDayOfMonth(1);
			}
		default:
			throw new IllegalArgumentException("Unknown date rounding mode " + roundingMode);
		}
	}

	/**
	 * Round the given date to the specified field using the specified {@link DateRoundingMode2 rounding mode}. This
	 * method delegates to the Joda Time rounding methods in the {@link LocalDate.Property} class.
	 *
	 * @param date the date to round
	 * @param roundTo the type of the field to round to
	 * @param roundingMode the rounding mode to use
	 * @return the rounded date value
	 */
	public static LocalDate round(LocalDate date, DateTimeFieldType roundTo, DateRoundingMode2 roundingMode) {

		Validate.notNull(date, "Date is required");
		Validate.notNull(roundTo, "The round to field type is required");
		Validate.notNull(roundingMode, "Rounding mode is required");

		switch (roundingMode) {
		case EARLIER:
			return date.property(roundTo).roundFloorCopy();
		case LATER:
			return date.property(roundTo).roundCeilingCopy();
		case HALF_EVEN:
			return date.property(roundTo).roundHalfEvenCopy();
		case HALF_EARLIER:
			return date.property(roundTo).roundHalfFloorCopy();
		case HALF_LATER:
			return date.property(roundTo).roundHalfCeilingCopy();
		default:
			throw new IllegalArgumentException("Unsupported rounding mode " + roundingMode);
		}
	}

	/**
	 * Round the given date to the specified field using the specified {@link DateRoundingMode2 rounding mode}. This
	 * method delegates to the Joda Time rounding methods in the {@link DateTime.Property} class.
	 *
	 * @param date the date to round
	 * @param roundTo the type of the field to round to
	 * @param roundingMode the rounding mode to use
	 * @return the rounded date value
	 */
	public static DateTime round(DateTime date, DateTimeFieldType field, DateRoundingMode2 roundingMode) {

		Validate.notNull(date, "Date is required");
		Validate.notNull(field, "Field to round to is required");
		Validate.notNull(roundingMode, "Rounding mode is required");

		switch (roundingMode) {
		case EARLIER:
			return date.property(field).roundFloorCopy();
		case LATER:
			return date.property(field).roundCeilingCopy();
		case HALF_EVEN:
			return date.property(field).roundHalfEvenCopy();
		case HALF_EARLIER:
			return date.property(field).roundHalfFloorCopy();
		case HALF_LATER:
			return date.property(field).roundHalfCeilingCopy();
		default:
			throw new IllegalArgumentException("Unsupported rounding mode " + roundingMode);
		}
	}

	/**
	 * Return the earliest of two given dates. Both dates are required.
	 */
	public static DateTime min(DateTime dt1, DateTime dt2) {
		Validate.notNull(dt1, MSG_BOTH_DATES_REQ);
		Validate.notNull(dt2, MSG_BOTH_DATES_REQ);
		return dt1.isBefore(dt2) ? dt1 : dt2;
	}

	/**
	 * Return the latest of two given dates. Both dates are required.
	 */
	public static DateTime max(DateTime dt1, DateTime dt2) {
		Validate.notNull(dt1, MSG_BOTH_DATES_REQ);
		Validate.notNull(dt2, MSG_BOTH_DATES_REQ);
		return dt1.isAfter(dt2) ? dt1 : dt2;
	}

	/**
	 * Return the earliest of two given dates. Both dates are required.
	 */
	public static LocalDate min(LocalDate d1, LocalDate d2) {
		Validate.notNull(d1, MSG_BOTH_DATES_REQ);
		Validate.notNull(d2, MSG_BOTH_DATES_REQ);
		return d1.isBefore(d2) ? d1 : d2;
	}

	/**
	 * Return the latest of two given dates. Both dates are required.
	 */
	public static LocalDate max(LocalDate d1, LocalDate d2) {
		Validate.notNull(d1, MSG_BOTH_DATES_REQ);
		Validate.notNull(d2, MSG_BOTH_DATES_REQ);
		return d1.isAfter(d2) ? d1 : d2;
	}

	private DateUtils() {
	}
}
