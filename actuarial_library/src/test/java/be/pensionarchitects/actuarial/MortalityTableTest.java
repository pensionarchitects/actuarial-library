package be.pensionarchitects.actuarial;

import junit.framework.TestCase;

public class MortalityTableTest extends TestCase {
	
	private static final MortalityTable FR = new MortalityTableImpl("FR");
	private static final MortalityTable FR3 = new MortalityTableImpl("FR", -3);
	private static final MortalityTable FR5 = new MortalityTableImpl("FR", -5);
	private static final MortalityTable MR = new MortalityTableImpl("MR");
	private static final MortalityTable MR3 = new MortalityTableImpl("MR", -3);
	private static final MortalityTable MR5 = new MortalityTableImpl("MR", -5);

	public void testUnknownTableName() {
		try {
			new MortalityTableImpl("BAD");
			fail("Expected IllegalArgumentException for unknown table name");
		} catch (IllegalArgumentException e) {
			// test OK
		}
	}

	public void testLx() {
		try {
			FR.lx(FR.getMinAge() - 1, 0);
			fail("Expected IllegalArgumentException for ageYears < minAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.lx(FR.getMaxAge() + 1, 0);
			fail("Expected IllegalArgumentException for ageYears > maxAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.lx(22, -1);
			fail("Expected IllegalArgumentException for negative ageMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.lx(22, 12);
			fail("Expected IllegalArgumentException for ageMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}

		assertEquals(994001.8543, MR.lx(10, 0), 0.0001);
		assertEquals(752974.9909, MR.lx(70, 10), 0.0001);
		assertEquals(12761.87266, MR.lx(100, 8), 0.0001);

		assertEquals(992638.5391, MR3.lx(15, 2), 0.0001);
		assertEquals(962326.9949, MR3.lx(45, 0), 0.0001);

		assertEquals(990580.2669, MR5.lx(20, 4), 0.0001);
		assertEquals(955418.2744, MR5.lx(50, 2), 0.0001);
		assertEquals(665954.4527, MR5.lx(80, 0), 0.0001);

		assertEquals(990854.2293, FR.lx(25, 6), 0.0001);
		assertEquals(960621.5904, FR.lx(55, 4), 0.0001);
		assertEquals(538071.2719, FR.lx(85, 2), 0.0001);

		assertEquals(989927.5699, FR3.lx(30, 8), 0.0001);
		assertEquals(954250.9211, FR3.lx(60, 6), 0.0001);
		assertEquals(458195.4437, FR3.lx(90, 4), 0.0001);

		assertEquals(988466.354, FR5.lx(35, 10), 0.0001);
		assertEquals(942195.1009, FR5.lx(65, 8), 0.0001);
		assertEquals(334264.0475, FR5.lx(95, 6), 0.0001);
	}

	public void testNpx() {
		try {
			FR.npx(FR.getMinAge() - 1, 6, 20, 0);
			fail("Expected IllegalArgumentException for ageYears < minAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.npx(FR.getMaxAge() + 1, 6, 20, 0);
			fail("Expected IllegalArgumentException for ageYears > maxAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.npx(20, -1, 20, 0);
			fail("Expected IllegalArgumentException for negative ageMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.npx(20, 12, 20, 0);
			fail("Expected IllegalArgumentException for ageMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		
		try {
			FR.npx(20, 6, -1, 0);
			fail("Expected IllegalArgumentException for negative periodYears value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.npx(20, 6, 20, -1);
			fail("Expected IllegalArgumentException for negative periodMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.npx(20, 6, 20, 12);
			fail("Expected IllegalArgumentException for periodMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}

		int[] maxPeriod = FR.getMaxPeriod(20, 6);
		try {
			FR.npx(20, 6, maxPeriod[0] + 1, maxPeriod[1]);
			fail("Expected IllegalArgumentException for period that's too large");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		
		assertEquals(0.892874837, MR.npx(10, 0, 50, 0), 0.0001);
		assertEquals(0.903407414, MR.npx(40, 11, 21, 0), 0.0001);
		assertEquals(0.924670023, MR.npx(70, 10, 2, 10), 0.0001);
		assertEquals(0.044700227, MR.npx(100, 8, 5, 8), 0.0001);

		assertEquals(0.913002411, MR3.npx(15, 2, 45, 2), 0.0001);
		assertEquals(0.943979248, MR3.npx(45, 0, 15, 0), 0.0001);
		assertEquals(0.566283176, MR3.npx(75, 11, 11, 0), 0.0001);

		assertEquals(0.924847075, MR5.npx(20, 4, 40, 4), 0.0001);
		assertEquals(0.960782609, MR5.npx(50, 2, 10, 2), 0.0001);
		assertEquals(0.550746621, MR5.npx(80, 0, 10, 0), 0.0001);

		assertEquals(0.949426872, FR.npx(25, 6, 35, 6), 0.0001);
		assertEquals(0.98081816, FR.npx(55, 4, 5, 4), 0.0001);
		assertEquals(0.633445293, FR.npx(85, 2, 5, 2), 0.0001);

		assertEquals(0.961112514, FR3.npx(30, 8, 30, 8), 0.0001);
		assertEquals(0.998296592, FR3.npx(60, 6, 0, 6), 0.0001);
		assertEquals(0.972105062, FR3.npx(90, 4, 0, 4), 0.0001);

		assertEquals(0.968026445, FR5.npx(35, 10, 25, 10), 0.0001);
		assertEquals(0.911692461, FR5.npx(65, 8, 10, 8), 0.0001);
		assertEquals(0.096548311, FR5.npx(95, 6, 10, 6), 0.0001);
	}

	public void testEx() {
		try {
			FR.ex(FR.getMinAge() - 1, 0);
			fail("Expected IllegalArgumentException for ageYears < minAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.ex(FR.getMaxAge() + 1, 0);
			fail("Expected IllegalArgumentException for ageYears > maxAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.ex(22, -1);
			fail("Expected IllegalArgumentException for negative ageMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.ex(22, 12);
			fail("Expected IllegalArgumentException for ageMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}

		assertEquals(67.65138902, MR.ex(10, 0), 0.0001);
		assertEquals(13.51875698, MR.ex(70, 10), 0.0001);
		assertEquals(1.556972262, MR.ex(100, 8), 0.0001);

		assertEquals(65.5768749, MR3.ex(15, 2), 0.0001);
		assertEquals(37.26532338, MR3.ex(45, 0), 0.0001);

		assertEquals(62.5442221, MR5.ex(20, 4), 0.0001);
		assertEquals(34.36006971, MR5.ex(50, 2), 0.0001);
		assertEquals(10.89799381, MR5.ex(80, 0), 0.0001);

		assertEquals(58.15333421, FR.ex(25, 6), 0.0001);
		assertEquals(29.54838852, FR.ex(55, 4), 0.0001);
		assertEquals(7.058826101, FR.ex(85, 2), 0.0001);

		assertEquals(56.04054438, FR3.ex(30, 8), 0.0001);
		assertEquals(27.574938, FR3.ex(60, 6), 0.0001);
		assertEquals(6.022454808, FR3.ex(90, 4), 0.0001);

		assertEquals(52.9550653, FR5.ex(35, 10), 0.0001);
		assertEquals(24.74624844, FR5.ex(65, 8), 0.0001);
		assertEquals(4.685744825, FR5.ex(95, 6), 0.0001);
	}

	public void testNqx() {
		try {
			FR.nqx(FR.getMinAge() - 1, 6, 20, 0);
			fail("Expected IllegalArgumentException for ageYears < minAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.nqx(FR.getMaxAge() + 1, 6, 20, 0);
			fail("Expected IllegalArgumentException for ageYears > maxAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.nqx(20, -1, 20, 0);
			fail("Expected IllegalArgumentException for negative ageMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.nqx(20, 12, 20, 0);
			fail("Expected IllegalArgumentException for ageMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		
		try {
			FR.nqx(20, 6, -1, 0);
			fail("Expected IllegalArgumentException for negative periodYears value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.nqx(20, 6, 20, -1);
			fail("Expected IllegalArgumentException for negative periodMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.nqx(20, 6, 20, 12);
			fail("Expected IllegalArgumentException for periodMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}

		int[] maxPeriod = FR.getMaxPeriod(20, 6);
		try {
			FR.nqx(20, 6, maxPeriod[0] + 1, maxPeriod[1]);
			fail("Expected IllegalArgumentException for period that's too large");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		
		assertEquals(0.107125163, MR.nqx(10, 0, 50, 0), 0.0001);
		assertEquals(0.096592586, MR.nqx(40, 11, 21, 0), 0.0001);
		assertEquals(0.075329977, MR.nqx(70, 10, 2, 10), 0.0001);
		assertEquals(0.955299773, MR.nqx(100, 8, 5, 8), 0.0001);

		assertEquals(0.086997589, MR3.nqx(15, 2, 45, 2), 0.0001);
		assertEquals(0.056020752, MR3.nqx(45, 0, 15, 0), 0.0001);
		assertEquals(0.433716824, MR3.nqx(75, 11, 11, 0), 0.0001);

		assertEquals(0.075152925, MR5.nqx(20, 4, 40, 4), 0.0001);
		assertEquals(0.039217391, MR5.nqx(50, 2, 10, 2), 0.0001);
		assertEquals(0.449253379, MR5.nqx(80, 0, 10, 0), 0.0001);

		assertEquals(0.050573128, FR.nqx(25, 6, 35, 6), 0.0001);
		assertEquals(0.01918184, FR.nqx(55, 4, 5, 4), 0.0001);
		assertEquals(0.366554707, FR.nqx(85, 2, 5, 2), 0.0001);

		assertEquals(0.038887486, FR3.nqx(30, 8, 30, 8), 0.0001);
		assertEquals(0.001703408, FR3.nqx(60, 6, 0, 6), 0.0001);
		assertEquals(0.027894938, FR3.nqx(90, 4, 0, 4), 0.0001);

		assertEquals(0.031973555, FR5.nqx(35, 10, 25, 10), 0.0001);
		assertEquals(0.088307539, FR5.nqx(65, 8, 10, 8), 0.0001);
		assertEquals(0.903451689, FR5.nqx(95, 6, 10, 6), 0.0001);
	}

	public void testDeferredQx() {
		try {
			FR.deferredQx(FR.getMinAge() - 1, 6, 20, 0);
			fail("Expected IllegalArgumentException for ageYears < minAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.deferredQx(FR.getMaxAge() + 1, 6, 20, 0);
			fail("Expected IllegalArgumentException for ageYears > maxAge");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.deferredQx(20, -1, 20, 0);
			fail("Expected IllegalArgumentException for negative ageMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.deferredQx(20, 12, 20, 0);
			fail("Expected IllegalArgumentException for ageMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		
		try {
			FR.deferredQx(20, 6, -1, 0);
			fail("Expected IllegalArgumentException for negative periodYears value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.deferredQx(20, 6, 20, -1);
			fail("Expected IllegalArgumentException for negative periodMonths value");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		try {
			FR.deferredQx(20, 6, 20, 12);
			fail("Expected IllegalArgumentException for periodMonths value of 12");
		} catch (IllegalArgumentException e) {
			// test OK
		}

		int[] maxPeriod = FR.getMaxPeriod(20, 6);
		try {
			FR.deferredQx(20, 6, maxPeriod[0] + 1, maxPeriod[1]);
			fail("Expected IllegalArgumentException for period that's too large");
		} catch (IllegalArgumentException e) {
			// test OK
		}
		
		assertEquals(0.008229562, MR.deferredQx(10, 0, 50, 0), 0.0001);
		assertEquals(0.009905787, MR.deferredQx(40, 11, 21, 0), 0.0001);
		assertEquals(0.030026227, MR.deferredQx(70, 10, 2, 10), 0.0001);
		assertEquals(0.023441741, MR.deferredQx(100, 8, 5, 8), 0.0001);

		assertEquals(0.006636355, MR3.deferredQx(15, 2, 45, 2), 0.0001);
		assertEquals(0.00665661, MR3.deferredQx(45, 0, 15, 0), 0.0001);
		assertEquals(0.047482132, MR3.deferredQx(75, 11, 11, 0), 0.0001);

		assertEquals(0.005805162, MR5.deferredQx(20, 4, 40, 4), 0.0001);
		assertEquals(0.005857621, MR5.deferredQx(50, 2, 10, 2), 0.0001);
		assertEquals(0.050997999, MR5.deferredQx(80, 0, 10, 0), 0.0001);

		assertEquals(0.004845695, FR.deferredQx(25, 6, 35, 6), 0.0001);
		assertEquals(0.004843533, FR.deferredQx(55, 4, 5, 4), 0.0001);
		assertEquals(0.07295697, FR.deferredQx(85, 2, 5, 2), 0.0001);

		assertEquals(0.003741563, FR3.deferredQx(30, 8, 30, 8), 0.0001);
		assertEquals(0.00375325, FR3.deferredQx(60, 6, 0, 6), 0.0001);
		assertEquals(0.084824155, FR3.deferredQx(90, 4, 0, 4), 0.0001);

		assertEquals(0.003189435, FR5.deferredQx(35, 10, 25, 10), 0.0001);
		assertEquals(0.013871875, FR5.deferredQx(65, 8, 10, 8), 0.0001);
		assertEquals(0.031664837, FR5.deferredQx(95, 6, 10, 6), 0.0001);
	}
}
