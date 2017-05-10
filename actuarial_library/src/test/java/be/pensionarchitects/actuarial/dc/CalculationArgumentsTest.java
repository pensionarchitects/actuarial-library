package be.pensionarchitects.actuarial.dc;

import junit.framework.TestCase;

import org.joda.time.LocalDate;

public class CalculationArgumentsTest extends TestCase {

	public void testValidate() {
		CalculationArguments args = new CalculationArguments();
		try {
			args.validate();
			fail("Expected IllegalArgumentException");
		} catch (Exception e) {
			// as expected
		}
		args.setPreRetirementDate(new LocalDate());
	}
}
