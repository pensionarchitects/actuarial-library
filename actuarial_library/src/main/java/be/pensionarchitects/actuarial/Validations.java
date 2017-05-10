package be.pensionarchitects.actuarial;

import static com.google.common.base.Preconditions.checkArgument;

final class Validations {

	static void validateAge(MortalityTable table, int ageYears, int ageMonths) {
		checkArgument(ageYears >= 0 && ageYears <= table.getMaxAge(),
				"The ageYears argument must be between 0 and %s (inclusive)", table.getMaxAge());
		checkArgument(ageMonths >= 0 && ageMonths < 12,
				"The ageMonths argument must be between 0 and 12 (exclusive)");
	}

	static void validatePeriod(MortalityTable table, int ageYears, int ageMonths, int periodYears, int periodMonths) {

		validateAge(table, ageYears, ageMonths);
		checkArgument(periodYears >= 0, "The periodYears argument must be a positive integer");
		checkArgument(periodMonths >= 0 && periodMonths < 12,
				"The periodMonths argument must be between 0 and 12 (exclusive)");

		int endAgeYears = ageYears + periodYears;
		int endAgeMonths = ageMonths + periodMonths;
		if (endAgeMonths > 11) endAgeYears++;

		checkArgument(endAgeYears >= 0 && endAgeYears <= table.getMaxAge(),
				"The calculated end age %s must be between 0 and %s (inclusive)", endAgeYears, table.getMaxAge());
	}

	private Validations() {
	}
}
