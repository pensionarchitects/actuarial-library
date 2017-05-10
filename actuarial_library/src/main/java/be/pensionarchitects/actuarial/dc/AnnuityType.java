package be.pensionarchitects.actuarial.dc;

/**
 * Specifies if an annuity payment is made at the beginning of a period, or at the end.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Annuity">Annuity</a> article on Wikipedia
 */
public enum AnnuityType {

	/**
	 * An annuity-immediate. Annuity payment is made in arrears, i.e. at the end of the period (nl: postnumerando).
	 */
	IMMEDIATE,

	/**
	 * An annuity-due. Annuity payment is made in advance, i.e. at the beginning of the period (nl: prenumerando).
	 */
	DUE;
}
