package be.pensionarchitects.actuarial;

import junit.framework.TestCase;

public class DeferredCapitalTest extends TestCase {
	
	private static final MortalityTable FR = new MortalityTableImpl("FR");
	private static final MortalityTable FR3 = new MortalityTableImpl("FR", -3);
	private static final MortalityTable FR5 = new MortalityTableImpl("FR", -5);
	private static final MortalityTable MR = new MortalityTableImpl("MR");
	private static final MortalityTable MR3 = new MortalityTableImpl("MR", -3);
	private static final MortalityTable MR5 = new MortalityTableImpl("MR", -5);
	
	public void testnEx() {		
		assertEquals(1, DeferredCapital.nEx(MR, 15, 0, 0, 0, 0.0325, 0., true), 0.001);
		assertEquals(0.707240575, DeferredCapital.nEx(MR3, 35, 4, 10, 4, 0.0325, 0., false), 0.001);			
		assertEquals(0.591618711, DeferredCapital.nEx(FR3, 45, 6, 15, 6, 0.0325, 0., true), 0.001);		
		assertEquals(0.521794541, DeferredCapital.nEx(FR5, 65, 10, 25, 10, 0.0325, 0.02, false), 0.001);
		assertEquals(0.310153399, DeferredCapital.nEx(MR, 15, 0, 35, 0, 0.0325, 0., true), 0.001);	
		assertEquals(0.255819743, DeferredCapital.nEx(FR, 25, 2, 40, 2, 0.0325, 0., false), 0.001);
		assertEquals(0.989131685, DeferredCapital.nEx(MR3, 35, 4, 0, 4, 0.0325, 0., true), 0.001);	
		assertEquals(0.836231667, DeferredCapital.nEx(MR5, 55, 8, 10, 8, 0.0325, 0.02, false), 0.001);						
	}
}
