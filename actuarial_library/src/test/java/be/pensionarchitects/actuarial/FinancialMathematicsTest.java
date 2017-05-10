package be.pensionarchitects.actuarial;

import junit.framework.TestCase;
import be.pensionarchitects.common.time.Periodicity;

public class FinancialMathematicsTest extends TestCase {

	public void testVn() {
		assertEquals(1, FinancialMathematics.vn(0, 2), 0.001);
		assertEquals(0.960980344, FinancialMathematics.vn(0.01, 4), 0.001);
		assertEquals(0.879222682, FinancialMathematics.vn(0.02, 6.5), 0.001);
		assertEquals(0.789409234, FinancialMathematics.vn(0.03, 8), 0.001);
		assertEquals(0.675564169, FinancialMathematics.vn(0.04, 10), 0.001);
		assertEquals(0.543417677, FinancialMathematics.vn(0.05, 12.5), 0.001);
		assertEquals(0.174110131, FinancialMathematics.vn(0.06, 30), 0.001);
	}

	public void testAn() {
		assertEquals(2, FinancialMathematics.an(2, 0, 0, Periodicity.ANNUM, true), 0.001);
		assertEquals(48.93827325, FinancialMathematics.an(4, 0.01, 0.02, Periodicity.MONTH, true), 0.01);
		assertEquals(6.159643202, FinancialMathematics.an(6.5, 0.02, 0, Periodicity.ANNUM, true), 0.001);
		assertEquals(92.38676937, FinancialMathematics.an(8, 0.03, 0.02, Periodicity.MONTH, true), 0.001);
		assertEquals(8.435331611, FinancialMathematics.an(10, 0.04, 0, Periodicity.ANNUM, true), 0.001);
		assertEquals(125.9815342, FinancialMathematics.an(12.5, 0.05, 0.02, Periodicity.MONTH, true), 0.001);
		assertEquals(14.59072102, FinancialMathematics.an(30, 0.06, 0, Periodicity.ANNUM, true), 0.001);

		assertEquals(2, FinancialMathematics.an(2, 0, 0, Periodicity.ANNUM, false), 0.001);
		assertEquals(48.89771073, FinancialMathematics.an(4, 0.01, 0.02, Periodicity.MONTH, false), 0.01);
		assertEquals(6.038865885, FinancialMathematics.an(6.5, 0.02, 0, Periodicity.ANNUM, false), 0.001);
		assertEquals(92.15947923, FinancialMathematics.an(8, 0.03, 0.02, Periodicity.MONTH, false), 0.001);
		assertEquals(8.110895779, FinancialMathematics.an(10, 0.04, 0, Periodicity.ANNUM, false), 0.001);
		assertEquals(125.4703524, FinancialMathematics.an(12.5, 0.05, 0.02, Periodicity.MONTH, false), 0.001);
		assertEquals(13.76483115, FinancialMathematics.an(30, 0.06, 0, Periodicity.ANNUM, false), 0.001);
	}

	public void testSn() {
		assertEquals(2, FinancialMathematics.sn(2, 0, 0, Periodicity.ANNUM, false), 0.001);
		assertEquals(50.88315387, FinancialMathematics.sn(4, 0.01, 0.02, Periodicity.MONTH, false), 0.01);
		assertEquals(6.868414574, FinancialMathematics.sn(6.5, 0.02, 0, Periodicity.ANNUM, false), 0.001);
		assertEquals(116.744871, FinancialMathematics.sn(8, 0.03, 0.02, Periodicity.MONTH, false), 0.001);
		assertEquals(12.00610712, FinancialMathematics.sn(10, 0.04, 0, Periodicity.ANNUM, false), 0.001);
		assertEquals(230.8911869, FinancialMathematics.sn(12.5, 0.05, 0.02, Periodicity.MONTH, false), 0.001);
		assertEquals(79.05818622, FinancialMathematics.sn(30, 0.06, 0, Periodicity.ANNUM, false), 0.001);

		assertEquals(2, FinancialMathematics.sn(2, 0, 0, Periodicity.ANNUM, true), 0.001);
		assertEquals(50.92536338, FinancialMathematics.sn(4, 0.01, 0.02, Periodicity.MONTH, true), 0.01);
		assertEquals(7.005782865, FinancialMathematics.sn(6.5, 0.02, 0, Periodicity.ANNUM, true), 0.001);
		assertEquals(117.0327954, FinancialMathematics.sn(8, 0.03, 0.02, Periodicity.MONTH, true), 0.001);
		assertEquals(12.48635141, FinancialMathematics.sn(10, 0.04, 0, Periodicity.ANNUM, true), 0.001);
		assertEquals(231.8318661, FinancialMathematics.sn(12.5, 0.05, 0.02, Periodicity.MONTH, true), 0.001);
		assertEquals(83.80167739, FinancialMathematics.sn(30, 0.06, 0, Periodicity.ANNUM, true), 0.001);
	}
}
