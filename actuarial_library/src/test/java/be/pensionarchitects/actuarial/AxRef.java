package be.pensionarchitects.actuarial;

import be.pensionarchitects.common.time.Periodicity;

public class AxRef {

	private MortalityTable table;
	private int ageY;
	private int ageM;
	private double annualInterest;
	private double annualIndexRate;
	private Periodicity paymentPeriodicity;
	private boolean beginningOfPeriod;
	private double result;

	public AxRef(
			MortalityTable table, int ageY, int ageM,
			double annualInterest, double annualIndexRate, Periodicity paymentPeriodicity,
			boolean beginningOfPeriod, double result) {
		this.table = table;
		this.ageY = ageY;
		this.ageM = ageM;
		this.annualInterest = annualInterest;
		this.annualIndexRate = annualIndexRate;
		this.paymentPeriodicity = paymentPeriodicity;
		this.beginningOfPeriod = beginningOfPeriod;
		this.result = result;
	}

	public MortalityTable getTable() {
		return table;
	}

	public int getAgeY() {
		return ageY;
	}

	public int getAgeM() {
		return ageM;
	}

	public double getAnnualInterest() {
		return annualInterest;
	}

	public double getAnnualIndexRate() {
		return annualIndexRate;
	}

	public Periodicity getPaymentPeriodicity() {
		return paymentPeriodicity;
	}

	public boolean isBeginningOfPeriod() {
		return beginningOfPeriod;
	}

	public double getResult() {
		return result;
	}
}