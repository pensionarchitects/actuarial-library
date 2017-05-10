package be.pensionarchitects.actuarial;

import be.pensionarchitects.actuarial.LifeInsurance.PaymentTime;
import junit.framework.TestCase;

public class LifeInsuranceTest extends TestCase {
	
	private static final MortalityTable MR = new MortalityTableImpl("MR");
	private static final MortalityTable FR = new MortalityTableImpl("FR");
	
	private static final MortalityTable MR3 = new MortalityTableImpl("MR", -3);
	private static final MortalityTable FR3 = new MortalityTableImpl("FR", -3);
	
	private static final MortalityTable MR5 = new MortalityTableImpl("MR", -5);
	private static final MortalityTable FR5 = new MortalityTableImpl("FR", -5);

	public void testA_x() {
		assertEquals(0.347222835, LifeInsurance.A_x(MR, 45, 0, 0.0325, 0, true, PaymentTime.END_OF_YEAR), 0.001);
		assertEquals(0.564930561, LifeInsurance.A_x(MR3, 50, 2, 0.0325, 0.01, false, PaymentTime.MIDDLE_OF_YEAR), 0.001);
		assertEquals(0.789060839, LifeInsurance.A_x(MR, 55, 4, 0.0325, 0.02, false, PaymentTime.CONTINUOUS), 0.001);
		assertEquals(0.458027141, LifeInsurance.A_x(MR5, 60, 6, 0.0325, 0, true, PaymentTime.END_OF_YEAR), 0.001);
		assertEquals(0.714097364, LifeInsurance.A_x(MR, 65, 8, 0.0325, 0.01, false, PaymentTime.MIDDLE_OF_YEAR), 0.001);
		assertEquals(0.871364205, LifeInsurance.A_x(MR3, 75, 10, 0.0325, 0.02, false, PaymentTime.CONTINUOUS), 0.001);
		assertEquals(0.175572049, LifeInsurance.A_x(MR, 20, 11, 0.0325, 0, true, PaymentTime.END_OF_YEAR), 0.001);
		assertEquals(0.427149722, LifeInsurance.A_x(MR5, 25, 0, 0.0325, 0.01, false, PaymentTime.MIDDLE_OF_YEAR), 0.001);
		assertEquals(0.687798744, LifeInsurance.A_x(FR, 30, 2, 0.0325, 0.02, false, PaymentTime.CONTINUOUS), 0.001);
		assertEquals(0.202943271, LifeInsurance.A_x(FR3, 35, 4, 0.0325, 0, true, PaymentTime.END_OF_YEAR), 0.001);
		assertEquals(0.488625531, LifeInsurance.A_x(FR, 40, 6, 0.0325, 0.01, false, PaymentTime.MIDDLE_OF_YEAR), 0.001);
		assertEquals(0.714517351, LifeInsurance.A_x(FR5, 45, 8, 0.0325, 0.02, false, PaymentTime.CONTINUOUS), 0.001);
		assertEquals(0.350689685, LifeInsurance.A_x(FR, 50, 10, 0.0325, 0, true, PaymentTime.END_OF_YEAR), 0.001);
		assertEquals(0.567635757, LifeInsurance.A_x(FR3, 55, 11, 0.0325, 0.01, false, PaymentTime.MIDDLE_OF_YEAR), 0.001);
		assertEquals(0.787786927, LifeInsurance.A_x(FR, 60, 0, 0.0325, 0.02, false, PaymentTime.CONTINUOUS), 0.001);
		assertEquals(0.454796443, LifeInsurance.A_x(FR5, 65, 2, 0.0325, 0, true, PaymentTime.END_OF_YEAR), 0.001);		
	}
}
