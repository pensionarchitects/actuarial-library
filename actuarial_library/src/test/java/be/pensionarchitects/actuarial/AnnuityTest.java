package be.pensionarchitects.actuarial;

import junit.framework.TestCase;
import be.pensionarchitects.common.math.NumberUtils;
import be.pensionarchitects.common.time.Periodicity;

public class AnnuityTest extends TestCase {

	private static final MortalityTable MR = new MortalityTableImpl("MR");
	private static final MortalityTable FR = new MortalityTableImpl("FR");

	private static final MortalityTable MR_3 = new MortalityTableImpl("MR", -3);
	private static final MortalityTable FR_3 = new MortalityTableImpl("FR", -3);

	private static final MortalityTable MR_5 = new MortalityTableImpl("MR", -5);
	private static final MortalityTable FR_5 = new MortalityTableImpl("FR", -5);

	private static final AxnRef[] REF_AXN = {
		new AxnRef(MR, 15, 0, 10, 0, 0.0325, 0, Periodicity.ANNUM, true, 8.670081592),
		new AxnRef(FR, 25, 2, 15, 2, 0.0325, 0, Periodicity.MONTH, true, 143.8935446),
		new AxnRef(MR_3, 35, 4, 20, 4, 0.0325, 0, Periodicity.ANNUM, true, 14.96500386),
		new AxnRef(FR_3, 45, 6, 25, 6, 0.0325, 0, Periodicity.MONTH, true, 204.740726),
		new AxnRef(MR_5, 55, 8, 10, 8, 0.0325, 0.02, Periodicity.ANNUM, true, 9.812517848),
		new AxnRef(FR_5, 65, 10, 15, 10, 0.0325, 0.02, Periodicity.MONTH, true, 161.6957393),
		new AxnRef(MR, 75, 11, 21, 0, 0, 0.01, Periodicity.ANNUM, true, 11.91040628),
		new AxnRef(MR, 15, 0, 10, 0, 0.0325, 0, Periodicity.ANNUM, false, 8.39098875),
		new AxnRef(FR, 25, 2, 15, 2, 0.0325, 0, Periodicity.MONTH, false, 143.5040819),
		new AxnRef(MR_3, 35, 4, 20, 4, 0.0325, 0, Periodicity.ANNUM, false, 14.46253409),
		new AxnRef(FR_3, 45, 6, 25, 6, 0.0325, 0, Periodicity.MONTH, false, 204.1442531),
		new AxnRef(MR_5, 55, 8, 10, 8, 0.0325, 0.02, Periodicity.ANNUM, false, 9.443348541),
		new AxnRef(FR_5, 65, 10, 15, 10, 0.0325, 0.02, Periodicity.MONTH, false, 161.1071376),
		new AxnRef(MR, 75, 11, 21, 0, 0, 0.01, Periodicity.ANNUM, false, 10.89098559)
	};

	private static final AxRef[] REF_AX = {
		new AxRef(MR, 15, 0, 0.0325, 0, Periodicity.ANNUM, true, 27.05408276),
		new AxRef(FR, 25, 2, 0.0325, 0, Periodicity.MONTH, true, 313.2790762),
		new AxRef(MR_3, 35, 4, 0.0325, 0, Periodicity.ANNUM, true, 24.03784794),
		new AxRef(FR_3, 45, 6, 0.0325, 0, Periodicity.MONTH, true, 270.901468),
		new AxRef(MR_5, 55, 8, 0.0325, 0.02, Periodicity.ANNUM, true, 25.03101565),
		new AxRef(FR_5, 65, 10, 0.0325, 0.02, Periodicity.MONTH, true, 254.9750379),
		new AxRef(MR, 75, 11, 0, 0.01, Periodicity.ANNUM, true, 12.20781386),
		new AxRef(MR, 15, 0, 0.0325, 0, Periodicity.ANNUM, false, 26.05408276),
		new AxRef(FR, 25, 2, 0.0325, 0, Periodicity.MONTH, false, 312.2790762),
		new AxRef(MR_3, 35, 4, 0.0325, 0, Periodicity.ANNUM, false, 23.03784794),
		new AxRef(FR_3, 45, 6, 0.0325, 0, Periodicity.MONTH, false, 269.901468),
		new AxRef(MR_5, 55, 8, 0.0325, 0.02, Periodicity.ANNUM, false, 23.55981927),
		new AxRef(FR_5, 65, 10, 0.0325, 0.02, Periodicity.MONTH, false, 253.5562692),
		new AxRef(MR, 75, 11, 0, 0.01, Periodicity.ANNUM, false, 11.0968454)
	};

	public void testAxrev() {
		assertTrue("", NumberUtils.equal(167.2385386162, Annuity.axrev(MR_3,75, 10, FR_3, 75, 10,0.5, 0.0325, 0.02, Periodicity.MONTH,true), 0.001));
		assertTrue("", NumberUtils.equal(39.63874497184, Annuity.axrev(FR,30, 2, MR, 40, 2, 0,0.0325, 0.02, Periodicity.ANNUM,true), 0.001));
		assertTrue("", NumberUtils.equal(305.1350853460, Annuity.axrev(FR_3,35, 4, MR_3, 35, 4,0.5, 0.0325, 0, Periodicity.MONTH,true), 0.001));
		assertTrue("", NumberUtils.equal(30.75764689775, Annuity.axrev(FR,40, 6, MR, 30, 6,0.75, 0.0325, 0.01, Periodicity.ANNUM,true), 0.001));
		assertTrue("", NumberUtils.equal(509.6208627698, Annuity.axrev(FR_5,45, 8, MR_5, 25, 8,1, 0.0325, 0.02, Periodicity.MONTH,true), 0.001));
		assertTrue("", NumberUtils.equal(20.62800433091, Annuity.axrev(FR,50, 10, MR, 20, 10,0, 0.0325, 0, Periodicity.ANNUM,true), 0.001));
		assertTrue("", NumberUtils.equal(289.3007453801, Annuity.axrev(FR_3,55, 11, MR_3, 50, 11,0.5, 0.0325, 0.01, Periodicity.MONTH,true), 0.001));
		assertTrue("", NumberUtils.equal(26.83882374943, Annuity.axrev(FR,60, 0, MR, 50, 0,0.75, 0.0325, 0.02, Periodicity.ANNUM,true), 0.001));
		assertTrue("", NumberUtils.equal(257.9735909435, Annuity.axrev(FR_5,65, 2, MR_5, 50, 2,1, 0.0325, 0, Periodicity.MONTH,true), 0.001));
		assertTrue("", NumberUtils.equal(298.0608369337, Annuity.axrev(MR_3,50, 2, FR_3, 50, 2,0.5, 0.0325, 0.01, Periodicity.MONTH,false), 0.001));
		assertTrue("", NumberUtils.equal(23.86327058591, Annuity.axrev(MR,55, 4, FR, 60, 4,0.75, 0.0325, 0.02, Periodicity.ANNUM,false), 0.001));
		assertTrue("", NumberUtils.equal(232.5879967401, Annuity.axrev(MR_5,60, 6, FR_5, 65, 6,1, 0.0325, 0, Periodicity.MONTH,false), 0.001));
		assertTrue("", NumberUtils.equal(13.41167374076, Annuity.axrev(MR,65, 8,FR,  70, 8, 0,0.0325, 0.01, Periodicity.ANNUM,false), 0.001));
		assertTrue("", NumberUtils.equal(165.9644348588, Annuity.axrev(MR_3,75, 10, FR_3, 75, 10,0.5, 0.0325, 0.02, Periodicity.MONTH,false), 0.001));
		assertTrue("", NumberUtils.equal(25.50704432493, Annuity.axrev(MR,20, 11, FR, 50, 11,0.75, 0.0325, 0, Periodicity.ANNUM,false), 0.001));
		assertTrue("", NumberUtils.equal(397.9784418527, Annuity.axrev(MR_5,25, 0, FR_5, 45, 0,1, 0.0325, 0.01, Periodicity.MONTH,false), 0.001));
		assertTrue("", NumberUtils.equal(37.88112252141, Annuity.axrev(FR,30, 2, MR, 40, 2, 0,0.0325, 0.02, Periodicity.ANNUM,false), 0.001));
		assertTrue("", NumberUtils.equal(304.1350853460, Annuity.axrev(FR_3,35, 4, MR_3, 35, 4,0.5, 0.0325, 0, Periodicity.MONTH,false), 0.001));
	}

	public void testAxy() {
		assertTrue("", NumberUtils.equal(246.26632514836, Annuity.axy(MR_3,50, 2, FR_3, 50, 2,  0.0325, 0.01, Periodicity.MONTH, false), 0.001));
		assertTrue("", NumberUtils.equal(18.238564660535, Annuity.axy(MR,45, 0, FR,45, 0,  0.0325, 0, Periodicity.ANNUM, false), 0.001));
		assertTrue("", NumberUtils.equal(16.504165608239, Annuity.axy(MR,55, 4, FR, 60, 4,  0.0325, 0.02, Periodicity.ANNUM, false), 0.001));
		assertTrue("", NumberUtils.equal(167.21776876495, Annuity.axy(MR_5,60, 6, FR_5, 65, 6,  0.0325, 0, Periodicity.MONTH, false), 0.001));
		assertTrue("", NumberUtils.equal(9.9207566622891, Annuity.axy(MR,65, 8, FR, 70, 8,  0.0325, 0.01, Periodicity.ANNUM, false), 0.001));
		assertTrue("", NumberUtils.equal(320.12053803396, Annuity.axy(MR_5,25, 0, FR_5, 45, 0,  0.0325, 0.01, Periodicity.MONTH, false), 0.001));
		assertTrue("", NumberUtils.equal(28.452560941803, Annuity.axy(FR,30, 2, MR, 40, 2,  0.0325, 0.02, Periodicity.ANNUM, false), 0.001));
		assertTrue("", NumberUtils.equal(268.29174350205, Annuity.axy(FR_3,35, 4, MR_3, 35, 4,  0.0325, 0, Periodicity.MONTH, false), 0.001));
		assertTrue("", NumberUtils.equal(24.778685111091, Annuity.axy(FR,40, 6,MR,  30, 6,  0.0325, 0.01, Periodicity.ANNUM, false), 0.001));
		assertTrue("", NumberUtils.equal(382.98135696358, Annuity.axy(FR_5,45, 8, MR_5, 25, 8,  0.0325, 0.02, Periodicity.MONTH, false), 0.001));
		assertTrue("", NumberUtils.equal(19.207058715098, Annuity.axy( FR, 50, 10,MR,20, 10,  0.0325, 0, Periodicity.ANNUM, false), 0.001));
		assertTrue("", NumberUtils.equal(229.45454662391, Annuity.axy(FR_3,55, 11, MR_3, 50, 11,  0.0325, 0.01, Periodicity.MONTH, false), 0.001));
		assertTrue("", NumberUtils.equal(17.943887783486, Annuity.axy(FR,60, 0, MR, 50, 0,  0.0325, 0.02, Periodicity.ANNUM, false), 0.001));
		assertTrue("", NumberUtils.equal(186.03065508085, Annuity.axy(FR_5,65, 2, MR_5, 50, 2,  0.0325, 0, Periodicity.MONTH, false), 0.001));
		
		assertTrue("", NumberUtils.equal(19.238564660535, Annuity.axy(MR,45, 0, FR, 45, 0,  0.0325, 0, Periodicity.ANNUM, true), 0.001));
		assertTrue("", NumberUtils.equal(247.47061245135, Annuity.axy(MR_3,50, 2, FR_3, 50, 2,  0.0325, 0.01, Periodicity.MONTH, true), 0.001));
		assertTrue("", NumberUtils.equal(17.834248920404, Annuity.axy(MR,55, 4, FR, 60, 4,  0.0325, 0.02, Periodicity.ANNUM, true), 0.001));
		assertTrue("", NumberUtils.equal(168.21776876495, Annuity.axy(MR_5,60, 6, FR_5, 65, 6,  0.0325, 0, Periodicity.MONTH, true), 0.001));
		assertTrue("", NumberUtils.equal(11.019964228912, Annuity.axy(MR,65, 8,  FR,70, 8,  0.0325, 0.01, Periodicity.ANNUM, true), 0.001));
		assertTrue("", NumberUtils.equal(107.41800221420, Annuity.axy(MR_3,75, 10, FR_3, 75, 10,  0.0325, 0.02, Periodicity.MONTH, true), 0.001));
		assertTrue("", NumberUtils.equal(20.180750718676, Annuity.axy(MR,20, 11, FR, 50, 11,  0.0325, 0, Periodicity.ANNUM, true), 0.001));
		assertTrue("", NumberUtils.equal(321.38609022144, Annuity.axy(MR_5,25, 0, FR_5, 45, 0,  0.0325, 0.01, Periodicity.MONTH, true), 0.001));
		assertTrue("", NumberUtils.equal(30.021612160639, Annuity.axy(FR,30, 2, MR, 40, 2,  0.0325, 0.02, Periodicity.ANNUM, true), 0.001));
		assertTrue("", NumberUtils.equal(269.29174350205, Annuity.axy(FR_3,35, 4, MR_3, 35, 4,  0.0325, 0, Periodicity.MONTH, true), 0.001));
		assertTrue("", NumberUtils.equal(26.026471962202, Annuity.axy(FR,40, 6, MR, 30, 6,  0.0325, 0.01, Periodicity.ANNUM, true), 0.001));
		assertTrue("", NumberUtils.equal(384.61388181173, Annuity.axy(FR_5,45, 8, MR_5, 25, 8,  0.0325, 0.02, Periodicity.MONTH, true), 0.001));
		assertTrue("", NumberUtils.equal(20.207058715098, Annuity.axy(FR,50, 10, MR, 20, 10,  0.0325, 0, Periodicity.ANNUM, true), 0.001));
		assertTrue("", NumberUtils.equal(230.64488791584, Annuity.axy(FR_3,55, 11, MR_3, 50, 11,  0.0325, 0.01, Periodicity.MONTH, true), 0.001));
		assertTrue("", NumberUtils.equal(19.302765539156, Annuity.axy(FR,60, 0, MR, 50, 0,  0.0325, 0.02, Periodicity.ANNUM, true), 0.001));
	}

	public void testAxyn() {
		assertEquals(1.0804074408746, Annuity.axyn(MR,45, 0, FR,45, 0, 1,1, 0.0325, 0, Periodicity.ANNUM,true), 0.001);
		assertEquals(25.271714546831, Annuity.axyn(MR_3,50, 2, FR_3, 50, 2, 2,2, 0.0325, 0.01, Periodicity.MONTH,true), 0.001);
		assertEquals(4.1628409806431, Annuity.axyn(MR,55, 4, FR, 60, 4, 4,4, 0.0325, 0.02,Periodicity.ANNUM,true), 0.001);
		assertEquals(67.687103844311, Annuity.axyn(MR_5,60, 6, FR_5, 65, 6, 6,6, 0.0325, 0, Periodicity.MONTH,true), 0.001);
		assertEquals(6.9665698702366, Annuity.axyn(MR,65, 8, FR, 70, 8, 8,8, 0.0325, 0.01, Periodicity.ANNUM,true), 0.001);
		assertEquals(87.792901104790, Annuity.axyn(MR_3,75, 10, FR_3, 75, 10, 10,10, 0.0325, 0.02, Periodicity.MONTH,true), 0.001);
		assertEquals(142.83575153335, Annuity.axyn(MR_5,25, 0, FR_5, 45, 0, 14,0, 0.0325, 0.01, Periodicity.MONTH,true), 0.001);
		assertEquals(14.409849933582, Annuity.axyn(FR,30, 2, MR, 40, 2, 16,2, 0.0325, 0.02, Periodicity.ANNUM,true), 0.001);
		assertEquals(163.40758795350, Annuity.axyn(FR_3,35, 4, MR_3, 35, 4, 18,4, 0.0325, 0, Periodicity.MONTH,true), 0.001);
		assertEquals(16.236970704145, Annuity.axyn(FR,40, 6, MR, 30, 6, 20,6, 0.0325, 0.01, Periodicity.ANNUM,true), 0.001);
		assertEquals(231.19122876262, Annuity.axyn(FR_5,45, 8, MR_5, 25, 8, 22,8, 0.0325, 0.02, Periodicity.MONTH,true), 0.001);
		assertEquals(16.470428777181, Annuity.axyn(FR,50, 10, MR, 20, 10, 24,10, 0.0325, 0, Periodicity.ANNUM,true), 0.001);
		assertEquals(18.280184835053, Annuity.axyn(FR,60, 0,  MR, 50, 0,28,0, 0.0325, 0.02, Periodicity.ANNUM,true), 0.001);
		assertEquals(182.51228262415, Annuity.axyn(FR_5,65, 2, MR_5, 50, 2, 30,2, 0.0325, 0, Periodicity.MONTH,true), 0.001);
		assertEquals(1.0424500686591, Annuity.axyn(MR,45, 0, FR, 45, 0, 1,1, 0.0325, 0, Periodicity.ANNUM,false), 0.001);
		assertEquals(25.194600415277, Annuity.axyn(MR_3,50, 2, FR_3, 50, 2, 2,2, 0.0325, 0.01, Periodicity.MONTH,false), 0.001);
		assertEquals(3.9803319164329, Annuity.axyn(MR,55, 4, FR, 60, 4, 4,4, 0.0325, 0.02,Periodicity.ANNUM,false), 0.001);
		assertEquals(67.425722948379, Annuity.axyn(MR_5,60, 6, FR_5, 65, 6, 6,6, 0.0325, 0, Periodicity.MONTH,false), 0.001);
		assertEquals(6.4597042106702, Annuity.axyn(MR,65, 8, FR, 70, 8, 8,8, 0.0325, 0.01, Periodicity.ANNUM,false), 0.001);
		assertEquals(87.004241655995, Annuity.axyn(MR_3,75, 10, FR_3, 75, 10, 10,10, 0.0325, 0.02, Periodicity.MONTH,false), 0.001);
		assertEquals(142.42866102776, Annuity.axyn(MR_5,25, 0, FR_5, 45, 0, 14,0, 0.0325, 0.01, Periodicity.MONTH,false), 0.001);
		assertEquals(13.898460280104, Annuity.axyn(FR,30, 2, MR, 40, 2, 16,2, 0.0325, 0.02, Periodicity.ANNUM,false), 0.001);
		assertEquals(162.93330082344, Annuity.axyn(FR_3,35, 4,MR_3,  35, 4, 18,4, 0.0325, 0, Periodicity.MONTH,false), 0.001);
		assertEquals(15.664329183289, Annuity.axyn(FR,40, 6, MR, 30, 6, 20,6, 0.0325, 0.01, Periodicity.ANNUM,false), 0.001);
		assertEquals(230.50792558447, Annuity.axyn(FR_5,45, 8,MR_5,  25, 8, 22,8, 0.0325, 0.02, Periodicity.MONTH,false), 0.001);
		assertEquals(15.827482742411, Annuity.axyn(FR,50, 10, MR, 20, 10, 24,10, 0.0325, 0, Periodicity.ANNUM,false), 0.001);
		assertEquals(17.140815166826, Annuity.axyn(FR,60, 0, MR, 50, 0, 28,0, 0.0325, 0.02, Periodicity.ANNUM,false), 0.001);
		assertEquals(181.60708605269, Annuity.axyn(FR_5,65, 2, MR_5, 50, 2, 30,2, 0.0325, 0, Periodicity.MONTH,false), 0.001);
	}

	public void testAxn() {
		int i = 1;
		for (AxnRef ref : REF_AXN) {
			assertEquals("reference case " + i, ref.getResult(), Annuity.axn(ref.getTable(), ref.getAgeY(), ref.getAgeM(),
					ref.getPeriodY(), ref.getPeriodM(), ref.getAnnualInterest(), ref.getAnnualIndexRate(), ref.getPaymentPeriodicity(),
					ref.isBeginningOfPeriod()), 0.0000001);
			i++;
		}
	}

	public void testAx() {
		int i = 1;
		for (AxRef ref : REF_AX) {
			//evaluate procentual difference (error in VBA code?)
			double ax = Annuity.ax(
					ref.getTable(), ref.getAgeY(), ref.getAgeM(),
					ref.getAnnualInterest(), ref.getAnnualIndexRate(), ref.getPaymentPeriodicity(), ref.isBeginningOfPeriod());
			assertTrue("reference case " + i + ": reference value = " + ref.getResult() + "; calculated value = " + ax,
					NumberUtils.equal(ref.getResult(), ax, 0.001));
			i++;
		}
	}
}
