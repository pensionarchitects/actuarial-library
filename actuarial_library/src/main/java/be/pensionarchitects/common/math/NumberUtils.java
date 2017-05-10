package be.pensionarchitects.common.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.apache.commons.lang.Validate;

/**
 * Utility functions for working with numerical values.
 *
 * Code for computation of machine precision and testing floating-point values for equality taken from
 * http://www.roseindia.net/javatutorials/object_oriented_implementation_of_numerical_methods.shtml.
 */
public final class NumberUtils {

	public static final int DEF_SCALE = 2;

	public static final RoundingMode DEF_ROUNDING_MODE = RoundingMode.HALF_EVEN;

	/** Radix used by floating-point numbers. */
	private static final int RADIX = computeRadix();

	/** Largest positive value which, when added to 1.0, yields 0. */
	private static final double MACHINE_PRECISION = computeMachinePrecision();

	/** Typical meaningful precision for numerical calculations. */
	private static final double DEFAULT_NUMERICAL_PRECISION = Math.sqrt(MACHINE_PRECISION);

	private NumberUtils() {
	}

	/**
	 * Test the given floating-point values for numerical equality with the default precision.
	 *
	 * @return true if the relative difference between a and b is less than the default numerical precision
	 */
	public static boolean equal(double a, double b) {
		return NumberUtils.equal(a, b, DEFAULT_NUMERICAL_PRECISION);
	}

	/**
	 * Test the given floating-point values for numerical equality with a specified precision.
	 *
	 * @return true if the relative difference between a and b is less than precision
	 */
	public static boolean equal(double a, double b, double precision) {
		double norm = Math.max(Math.abs(a), Math.abs(b));
		return norm < precision || Math.abs(a - b) < precision * norm;
	}

	/**
	 * Round the given floating-point value to the {@value #DEF_SCALE default scale} using the
	 * {@link #DEF_ROUNDING_MODE default rounding mode}.
	 *
	 * @param value the value to round
	 * @return a {@link BigDecimal} instance that represents the rounded value
	 */
	public static BigDecimal round(double value) {
		return NumberUtils.round(value, DEF_SCALE, DEF_ROUNDING_MODE);
	}

	/**
	 * Round the given floating-point value to the given scale using the specified rounding mode.
	 *
	 * @param value the value to round
	 * @param scale the desired scale (the number of fractional digits)
	 * @param roundingMode the rounding mode to use
	 * @return a {@link BigDecimal} instance that represents the rounded value
	 */
	public static BigDecimal round(double value, int scale, RoundingMode roundingMode) {
		return BigDecimal.valueOf(value).setScale(scale, roundingMode);
	}

	/**
	 * Round the given floating-point value to an {@code int} value using the
	 * {@link #DEF_ROUNDING_MODE default rounding mode}.
	 *
	 * @param value the value to round
	 * @param throws {@link ArithmeticException} if the result will not fit in an {@code int}
	 */
	public static int roundToInt(double value) {
		return NumberUtils.roundToInt(value, DEF_ROUNDING_MODE);
	}

	/**
	 * Round the given floating-point value to an {@code int} value using the specified rounding mode.
	 *
	 * @param value the value to round
	 * @param roundingMode the rounding mode to use
	 * @param throws {@link ArithmeticException} if the result will not fit in an {@code int}
	 */
	public static int roundToInt(double value, RoundingMode roundingMode) {
		return NumberUtils.round(value, 0, roundingMode).intValueExact();
	}

	/**
	 * Scale the given integer type {@link Number} instance to the {@link #DEF_SCALE default scale}.
	 *
	 * @param value the integer number to scale
	 * @return a {@link BigDecimal} instance that represents the scaled value
	 * @throws IllegalArgumentException if the given number is not a Byte, Short, Integer, Long or BigInteger
	 */
	public static BigDecimal scaleInteger(Number value) {
		return scaleInteger(value, DEF_SCALE);
	}

	/**
	 * Scale the given integer type {@link Number} instance to the specified scale.
	 *
	 * @param value the integer number to scale
	 * @param scale the desired scale
	 * @return a {@link BigDecimal} instance that represents the scaled value
	 * @throws IllegalArgumentException if the given number is not a Byte, Short, Integer, Long or BigInteger
	 */
	public static BigDecimal scaleInteger(Number value, int scale) {
		if (value == null) return null;
		Validate.isTrue(
				value instanceof Byte ||
				value instanceof Short ||
				value instanceof Integer ||
				value instanceof Long ||
				value instanceof BigInteger,
				"Number must be an integer type value");
		return BigDecimal.valueOf(value.longValue(), scale);
	}

	private static int computeRadix() {
		int radix = 0;
		double a = 1.0d;
		double tmp1;
		double tmp2;
		do {
			a += a;
			tmp1 = a + 1.0d;
			tmp2 = tmp1 - a;
		} while (tmp2 - 1.0d != 0.0d);
		double b = 1.0d;
		while (radix == 0) {
			b += b;
			tmp1 = a + b;
			radix = (int)(tmp1 - a);
		}
		return radix;
	}

	private static double computeMachinePrecision() {
		double floatingRadix = RADIX;
		double inverseRadix = 1.0d / floatingRadix;
		double machinePrecision = 1.0d;
		double tmp = 1.0d + machinePrecision;
		while (tmp - 1.0d != 0.0d) {
			machinePrecision *= inverseRadix;
			tmp = 1.0d + machinePrecision;
		}
		return machinePrecision;
	}
}
