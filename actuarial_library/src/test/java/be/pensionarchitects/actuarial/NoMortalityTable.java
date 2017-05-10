package be.pensionarchitects.actuarial;

public class NoMortalityTable extends MortalityTableImpl {

	public NoMortalityTable() {
		super("MR");
	}

	@Override
	public int getMaxAge() {
		return 130;
	}

	@Override
	public double lx(int ageYears, int ageMonths) {
		return 1000000.;
	}
}
