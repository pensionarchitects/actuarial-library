package be.pensionarchitects.actuarial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.Validate;

public class MortalityTableImpl implements MortalityTable {

	private List<Double> values = new ArrayList<Double>();
	private int ageCorrection;

	public MortalityTableImpl(String tableName) {
		load(tableName);
	}

	public MortalityTableImpl(String tableName, int ageCorrection) {
		this(tableName);
		this.ageCorrection = ageCorrection;
	}

	@Override
	public int getAgeCorrection() {
		return ageCorrection;
	}

	@Override
	public int getMinAge() {
		return -getAgeCorrection();
	}

	@Override
	public int getMaxAge() {
		int highestAge = values.size() - 1;
		if(getAgeCorrection() < 0)
			highestAge += getAgeCorrection(); 
		return highestAge - 2; // -1 for ages with months > 0, and -1 for deferredQx
	}

	@Override
	public int[] getMaxPeriod(int ageYears, int ageMonths) {
		validateAge(ageYears, ageMonths);
		int periodYears = getMaxAge() - ageYears;
		int periodMonths = 11 - ageMonths;
		if (periodMonths < 0) {
			periodYears--;
			periodMonths += 12;
		}
		if (periodYears < 0) {
			return new int[] { 0, 0 };
		}
		return new int[] { periodYears, periodMonths };
	}

	@Override
	public double lx(int ageYears, int ageMonths) {
		validateAge(ageYears, ageMonths);

		double lx = values.get(ageYears + getAgeCorrection());
		double lx1 = values.get(ageYears + getAgeCorrection() + 1);

		double dx = BigDecimal.valueOf(lx).setScale(0, RoundingMode.HALF_EVEN).intValue()
					- BigDecimal.valueOf(lx1).setScale(0, RoundingMode.HALF_EVEN).intValue();

		return lx - (ageMonths / 12.) * dx;
	}

	@Override
	public double npx(int ageYears, int ageMonths, int periodYears, int periodMonths) {
		validateAge(ageYears, ageMonths, periodYears, periodMonths);
		int[] endAge = computeEndAge(ageYears, ageMonths, periodYears, periodMonths);
		return lx(endAge[0], endAge[1]) / lx(ageYears, ageMonths);
	}

	@Override
	public double ex(int ageYears, int ageMonths) {
		validateAge(ageYears, ageMonths);

		double ex = 0;
		for (int j = 1; j <= getMaxAge() - ageYears; j++) {
			ex += lx(ageYears + j, ageMonths) / lx(ageYears, ageMonths);
		}

		return ex;
	}

	@Override
	public double nqx(int ageYears, int ageMonths, int periodYears, int periodMonths) {
		return 1 - npx(ageYears, ageMonths, periodYears, periodMonths);
	}

	@Override
	public double deferredQx(int ageYears, int ageMonths, int periodYears, int periodMonths) {
		validateAge(ageYears, ageMonths, periodYears, periodMonths);
		int[] endAge = computeEndAge(ageYears, ageMonths, periodYears, periodMonths);
		return (lx(endAge[0], endAge[1]) - lx(endAge[0] + 1, endAge[1])) / lx(ageYears, ageMonths);
	}

	private void validateAge(int ageYears, int ageMonths) {
		Validate.isTrue(ageYears >= getMinAge() && ageYears <= getMaxAge(),
				"The ageYears argument must be between " + getMinAge() + " and " + getMaxAge() + " (inclusive)");
		Validate.isTrue(ageMonths >= 0 && ageMonths < 12,
				"The ageMonths argument must be between 0 and 12 (exclusive)");
	}

	private void validateAge(int ageYears, int ageMonths, int periodYears, int periodMonths) {
		validateAge(ageYears, ageMonths);
		Validate.isTrue(periodYears >= 0, "The periodYears argument must be a positive integer");
		Validate.isTrue(periodMonths >= 0 && periodMonths < 12,
				"The periodMonths argument must be between 0 and 12 (exclusive)");
		int[] endAge = computeEndAge(ageYears, ageMonths, periodYears, periodMonths);
		Validate.isTrue(endAge[0] >= getMinAge() && endAge[0] <= getMaxAge(),
				"The calculated end age (" + endAge[0] + ") must be between " +
						getMinAge() + " and " + getMaxAge() + " (inclusive)");
	}

	private int[] computeEndAge(int ageYears, int ageMonths, int periodYears, int periodMonths) {
		int endAgeYears = ageYears + periodYears;
		int endAgeMonths = ageMonths + periodMonths;
		if (endAgeMonths > 11) {
			endAgeYears++;
			endAgeMonths -= 12;
		}
		return new int[] { endAgeYears, endAgeMonths };
	}

	private void load(String tableName) {

		String tableNameUC = tableName.toUpperCase(Locale.ENGLISH);
		InputStream resourceStream =
				MortalityTableImpl.class.getResourceAsStream("/be/pensionarchitects/actuarial/" + tableNameUC);

		if (resourceStream == null) throw new IllegalArgumentException("Mortality table " + tableName + " not found");

		BufferedReader in = new BufferedReader(new InputStreamReader(resourceStream, Charset.forName("ISO-8859-1")));
		try {
			String line = null;
			while ((line = in.readLine()) != null) {
				String[] splitLine = line.split(";");
				values.add(Double.parseDouble(splitLine[1]));
			}
		} catch (IOException e) {
			throw new RuntimeException("Error loading mortality table " + tableName, e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
