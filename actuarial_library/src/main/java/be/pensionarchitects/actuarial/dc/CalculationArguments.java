package be.pensionarchitects.actuarial.dc;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDate;

import be.pensionarchitects.common.time.Periodicity;

public class CalculationArguments {

	public static final BigDecimal DEF_WORK_PERCENTAGE = BigDecimal.ONE;
	public static final BigDecimal DEF_CURRENT_RESERVE = BigDecimal.ZERO;
	public static final BigDecimal DEF_ANNUAL_CEILING = BigDecimal.valueOf(Double.MAX_VALUE);
	public static final BigDecimal DEF_SALARY_MULTIPLIER = BigDecimal.ONE;
	public static final BigDecimal DEF_CONTRIBUTION_RATE_S2 = BigDecimal.ZERO;
	public static final BigDecimal DEF_FIXED_CONTRIBUTION = BigDecimal.ZERO;
	public static final BigDecimal DEF_EXPECTED_ANNUAL_SALARY_INC = BigDecimal.ZERO;
	public static final BigDecimal DEF_EXPECTED_CEILING_INC = BigDecimal.ZERO;
	public static final BigDecimal DEF_RESERVE_LOADINGS = BigDecimal.ZERO;
	public static final BigDecimal DEF_PREMIUM_LOADINGS = BigDecimal.ZERO;
	public static final ContributionRateProvider DEF_CONTRIB_RATES_PROVIDER = new ContributionRateProvider() {
		@Override public BigDecimal getRateS1(LocalDate validOn) { return BigDecimal.ZERO; }
		@Override public BigDecimal getRateS2(LocalDate validOn) { return BigDecimal.ZERO; }
	};

	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	private BigDecimal workPercentage = DEF_WORK_PERCENTAGE;

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal monthlySalary;

	@NotNull
	private LocalDate calculationDate = new LocalDate().withDayOfMonth(1);

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal currentReserve = DEF_CURRENT_RESERVE;

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal annualCeiling = DEF_ANNUAL_CEILING;

	@NotNull
	@DecimalMin("1.0")
	private BigDecimal salaryMultiplier = DEF_SALARY_MULTIPLIER;

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal fixedAnnualContribution = DEF_FIXED_CONTRIBUTION;

	@NotNull
	private Periodicity paymentPeriodicity;

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal interestRate;

	@NotNull
	private LocalDate retirementDate;

	@NotNull
	private LocalDate preRetirementDate;

	@DecimalMin("0.0")
	@DecimalMax("1.0")
	private BigDecimal newWorkPercentage;

	private LocalDate newWorkPercentageFrom;

	private LocalDate newWorkPercentageTill;

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal expectedAnualSalaryIncrease = DEF_EXPECTED_ANNUAL_SALARY_INC;

	@NotNull
	@DecimalMin("0.0")
	private BigDecimal expectedCeilingIncrease = DEF_EXPECTED_CEILING_INC;

	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("0.2")
	private BigDecimal reserveLoadings = DEF_RESERVE_LOADINGS;

	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("0.2")
	private BigDecimal premiumLoadings = DEF_PREMIUM_LOADINGS;

	@NotNull
	private AnnuityType annuityType;

	@NotNull
	private ContributionRateProvider contributionRateProvider = DEF_CONTRIB_RATES_PROVIDER;

	public BigDecimal getWorkPercentage() {
		return workPercentage;
	}

	public void setWorkPercentage(BigDecimal workPercentage) {
		this.workPercentage = workPercentage;
	}

	public BigDecimal getMonthlySalary() {
		return monthlySalary;
	}

	public void setMonthlySalary(BigDecimal monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	public LocalDate getCalculationDate() {
		return calculationDate;
	}

	public void setCalculationDate(LocalDate calculationDate) {
		this.calculationDate = calculationDate;
	}

	public BigDecimal getCurrentReserve() {
		return currentReserve;
	}

	public void setCurrentReserve(BigDecimal currentReserve) {
		this.currentReserve = currentReserve;
	}

	public BigDecimal getAnnualCeiling() {
		return annualCeiling;
	}

	public void setAnnualCeiling(BigDecimal annualCeiling) {
		this.annualCeiling = annualCeiling;
	}

	public BigDecimal getSalaryMultiplier() {
		return salaryMultiplier;
	}

	public void setSalaryMultiplier(BigDecimal salaryMultiplier) {
		this.salaryMultiplier = salaryMultiplier;
	}

	public BigDecimal getFixedAnnualContribution() {
		return fixedAnnualContribution;
	}

	public void setFixedAnnualContribution(BigDecimal fixedContribution) {
		this.fixedAnnualContribution = fixedContribution;
	}

	public Periodicity getPaymentPeriodicity() {
		return paymentPeriodicity;
	}

	public void setPaymentPeriodicity(Periodicity paymentPeriodicity) {
		this.paymentPeriodicity = paymentPeriodicity;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getNewWorkPercentage() {
		return newWorkPercentage;
	}

	public void setNewWorkPercentage(BigDecimal newWorkPercentage) {
		this.newWorkPercentage = newWorkPercentage;
	}

	public LocalDate getNewWorkPercentageFrom() {
		return newWorkPercentageFrom;
	}

	public void setNewWorkPercentageFrom(LocalDate newWorkPercentageFrom) {
		this.newWorkPercentageFrom = newWorkPercentageFrom;
	}

	public LocalDate getNewWorkPercentageTill() {
		return newWorkPercentageTill;
	}

	public void setNewWorkPercentageTill(LocalDate newWorkPercentageTill) {
		this.newWorkPercentageTill = newWorkPercentageTill;
	}

	public BigDecimal getExpectedAnualSalaryIncrease() {
		return expectedAnualSalaryIncrease;
	}

	public void setExpectedAnualSalaryIncrease(BigDecimal expectedAnualSalaryIncrease) {
		this.expectedAnualSalaryIncrease = expectedAnualSalaryIncrease;
	}

	public BigDecimal getExpectedCeilingIncrease() {
		return expectedCeilingIncrease;
	}

	public void setExpectedCeilingIncrease(BigDecimal expectedCeilingIncrease) {
		this.expectedCeilingIncrease = expectedCeilingIncrease;
	}

	public BigDecimal getReserveLoadings() {
		return reserveLoadings;
	}

	public void setReserveLoadings(BigDecimal reserveLoadings) {
		this.reserveLoadings = reserveLoadings;
	}

	public BigDecimal getPremiumLoadings() {
		return premiumLoadings;
	}

	public void setPremiumLoadings(BigDecimal premiumLoadings) {
		this.premiumLoadings = premiumLoadings;
	}

	public LocalDate getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(LocalDate date) {
		this.retirementDate = date;
	}

	public LocalDate getPreRetirementDate() {
		return preRetirementDate;
	}

	public void setPreRetirementDate(LocalDate date) {
		this.preRetirementDate = date;
	}

	public AnnuityType getAnnuityType() {
		return annuityType;
	}

	public void setAnnuityType(AnnuityType type) {
		this.annuityType = type;
	}

	public ContributionRateProvider getContributionRateProvider() {
		return contributionRateProvider;
	}

	public void setContributionRatesCalculator(ContributionRateProvider contributionRateProvider) {
		this.contributionRateProvider = contributionRateProvider;
	}

	protected void validate() {

		//derive defaults for some optional fields from the value of other fields
		if (getPreRetirementDate() == null) setPreRetirementDate(getRetirementDate());

		//base validation
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<CalculationArguments>> violations = validator.validate(this);
		if (!violations.isEmpty()) {
			ConstraintViolation<CalculationArguments> violation = violations.iterator().next();
			throw new IllegalArgumentException(
					"The property " + violation.getPropertyPath() + " is invalid: " + violation.getMessage());
		}

		//extended validation
		//normal timeline: calculationDate < preRetirementDate <= retirementDate
		Validate.isTrue(getCalculationDate().isBefore(getPreRetirementDate()),
				"The calculation date must be before the pre-retirement date");
		Validate.isTrue(!getPreRetirementDate().isAfter(getRetirementDate()),
				"The pre-retirement date cannot be after the retirement date");
		if (getNewWorkPercentage() != null) {
			//set default end date if necessary
			if (getNewWorkPercentageTill() == null) setNewWorkPercentageTill(getPreRetirementDate());
			//start date is required
			Validate.notNull(getNewWorkPercentageFrom(),
					"The property newWorkPercentageFrom is required " +
					"when a value is set for property newWorkPercentage");
			//normal timeline: calculationDate <= newWorkPercentageFrom < newWorkPercentageTill <= preRetirementDate
			Validate.isTrue(!getCalculationDate().isAfter(getNewWorkPercentageFrom()),
					"The property newWorkPercentageFrom is invalid: " +
					"the date must be greater than or equal to the calculation date");
			Validate.isTrue(getNewWorkPercentageFrom().isBefore(getNewWorkPercentageTill()),
					"The property newWorkPercentageFrom is invalid: the date must be before newWorkPercentageTill");
			Validate.isTrue(!getNewWorkPercentageTill().isAfter(getPreRetirementDate()),
					"The property newWorkPercentageFrom is invalid: " +
					"the date must not be after the pre-retirement date");
		}
	}
}
