package be.pensionarchitects.common.time;

import org.joda.time.DateTime;

/**
 * Specifies how to round a date. The members of this enumeration correspond to the Joda Time rounding methods.
 */
public enum DateRoundingMode2 {

	/**
	 * Round towards the later date. Corresponds to the {@link DateTime.Property#roundCeilingCopy()} method.
	 */
	LATER,

	/**
	 * Round towards the earlier date. Corresponds to the {@link DateTime.Property#roundFloorCopy()} method.
	 */
	EARLIER,

	/**
	 * Round towards the nearest date, favouring the one with the even rounding field if equidistant. Corresponds to
	 * the {@link DateTime.Property#roundHalfEvenCopy()} method.
	 */
	HALF_EVEN,

	/**
	 * Round towards the nearest date, favouring the later date if equidistant. Corresponds to the
	 * {@link DateTime.Property#roundHalfCeilingCopy()} method.
	 */
	HALF_LATER,

	/**
	 * Round towards the nearest date, favouring the earlier date if equidistant. Corresponds to the
	 * {@link DateTime.Property#roundHalfFloorCopy()} method.
	 */
	HALF_EARLIER
}
