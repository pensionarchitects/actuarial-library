package be.pensionarchitects.actuarial;

import be.pensionarchitects.common.time.Periodicity;

public class AxnRef extends AxRef {

	private int periodY;
	private int periodM;

	public AxnRef(MortalityTable table, int ageY, int ageM, int periodY, int periodM,
			double annualInterest, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod, double result) {
		super(table, ageY, ageM, annualInterest, annualIndexRate, paymentPeriodicity, beginningOfPeriod, result);
		this.periodY = periodY;
		this.periodM = periodM;
	}

	public int getPeriodY() {
		return periodY;
	}

	public int getPeriodM() {
		return periodM;
	}
}
