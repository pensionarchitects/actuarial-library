package be.pensionarchitects.actuarial.dc;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

/**
 * A provider for contribution rates valid on a certain date.
 */
public interface ContributionRateProvider {

	/**
	 * Get the S1 contribution rate valid on the specified date.
	 */
	BigDecimal getRateS1(LocalDate validOn);

	/**
	 * Get the S2 contribution rate valid on the specified date.
	 */
	BigDecimal getRateS2(LocalDate validOn);
}
