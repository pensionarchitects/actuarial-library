package be.pensionarchitects.actuarial;

public interface MortalityTable {

	int getAgeCorrection();
	int getMinAge();
	int getMaxAge();
	int[] getMaxPeriod(int ageYears, int ageMonths);

	/**
	 * Return the number of survivors in a mortality table.
	 *
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 */
	double lx(int ageYears, int ageMonths);

	/**
	 * Return the probability to survive n years.
	 *
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 * @param periodYears the period in years
	 * @param periodMonths the period in months
	 */
	double npx(int ageYears, int ageMonths, int periodYears, int periodMonths);

	/**
	 * Return the life expectancy.
	 *
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 */
	double ex(int ageYears, int ageMonths);

	/**
	 * Return the death probability in the next n years.
	 *
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 * @param periodYears the period in years
	 * @param periodMonths the period in months
	 */
	double nqx(int ageYears, int ageMonths, int periodYears, int periodMonths);

	/**
	 * Return the death probability in exactly n years.
	 *
	 * @param ageYears the age in years
	 * @param ageMonths the age in months
	 * @param periodYears the period in years
	 * @param periodMonths the period in months
	 * @return
	 */
	double deferredQx(int ageYears, int ageMonths, int periodYears, int periodMonths);
}
