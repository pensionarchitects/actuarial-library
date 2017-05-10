//package be.pensionarchitects.actuarial.utils;
//
//import junit.framework.TestCase;
//
//import org.joda.time.LocalDate;
//
//public class DateUtilsTest extends TestCase {
//
//	private final static LocalDate DEC_01_10 = new LocalDate(2010, 12, 1);
//	private final static LocalDate DEC_15_10 = new LocalDate(2010, 12, 15);
//	private final static LocalDate DEC_16_10 = new LocalDate(2010, 12, 16);
//	private final static LocalDate DEC_31_10 = new LocalDate(2010, 12, 31);
//
//	private final static LocalDate JAN_01_11 = new LocalDate(2011, 1, 1);
//	private final static LocalDate JAN_15_11 = new LocalDate(2011, 1, 15);
//	private final static LocalDate JAN_16_11 = new LocalDate(2011, 1, 16);
//	private final static LocalDate JAN_31_11 = new LocalDate(2011, 1, 31);
//	
//	private final static LocalDate DEC_01_11 = new LocalDate(2011, 12, 1);
//	private final static LocalDate DEC_15_11 = new LocalDate(2011, 12, 15);
//	private final static LocalDate DEC_16_11 = new LocalDate(2011, 12, 16);
//	private final static LocalDate DEC_31_11 = new LocalDate(2011, 12, 31);
//	
//	private final static LocalDate JAN_01_12 = new LocalDate(2012, 1, 1);
//	
//	private final static LocalDate RETIREMENT_60 = new LocalDate(2071, 1, 1);
//	private final static LocalDate RETIREMENT_65 = new LocalDate(2076, 1, 1);
//
//	private final static RetirementDateRef[] REF_RETIREMENT_DATE = {
//		new RetirementDateRef(new LocalDate(1970, 4, 14), 60, new LocalDate(2030, 5, 1)),
//		new RetirementDateRef(new LocalDate(1989, 5, 30), 61, new LocalDate(2050, 6, 1)),
//		new RetirementDateRef(new LocalDate(2003, 2, 20), 62, new LocalDate(2065, 3, 1)),
//		new RetirementDateRef(new LocalDate(1970, 10, 27), 63, new LocalDate(2033, 11, 1)),
//		new RetirementDateRef(new LocalDate(1980, 1, 1), 64, new LocalDate(2044, 2, 1)),
//		new RetirementDateRef(new LocalDate(1985, 12, 31), 65, new LocalDate(2051, 1, 1)),
//		new RetirementDateRef(new LocalDate(1954, 12, 7), 66, new LocalDate(2021, 1, 1))
//	};
//
//	private final static DateDiffRef[] REF_DATE_DIFF_DEC = {
//		new DateDiffRef(new LocalDate(1970, 4, 14), new LocalDate(2010, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 39.75),
//		new DateDiffRef(new LocalDate(1989, 5, 30), new LocalDate(2010, 7, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 21.08),
//		new DateDiffRef(new LocalDate(2003, 2, 20), new LocalDate(2010, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 6.92),
//		new DateDiffRef(new LocalDate(1970, 10, 27), new LocalDate(2010, 3, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 39.33),
//		new DateDiffRef(new LocalDate(1980, 1, 1), new LocalDate(2009, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 29.),
//		new DateDiffRef(new LocalDate(1985, 12, 31), new LocalDate(2009, 1, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 23.),
//		new DateDiffRef(new LocalDate(1954, 12, 7), new LocalDate(2009, 12, 1), DateRoundingMode.FIRST_OF_MONTH, 55.),
//	};
//
//	private final static DateDiffRef[] REF_DATE_DIFF_YEARS = {
//		new DateDiffRef(new LocalDate(1970, 4, 14), new LocalDate(2010, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 39),
//		new DateDiffRef(new LocalDate(1989, 5, 30), new LocalDate(2010, 7, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 21),
//		new DateDiffRef(new LocalDate(2003, 2, 20), new LocalDate(2010, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 6),
//		new DateDiffRef(new LocalDate(1970, 10, 27), new LocalDate(2010, 3, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 39),
//		new DateDiffRef(new LocalDate(1980, 1, 1), new LocalDate(2009, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 29),
//		new DateDiffRef(new LocalDate(1985, 12, 31), new LocalDate(2009, 1, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 23),
//		new DateDiffRef(new LocalDate(1954, 12, 7), new LocalDate(2009, 12, 1), DateRoundingMode.FIRST_OF_MONTH, 55)
//	};
//
//	private final static DateDiffRef[] REF_DATE_DIFF_MONTHS = {
//		new DateDiffRef(new LocalDate(1970, 4, 14), new LocalDate(2010, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 9),
//		new DateDiffRef(new LocalDate(1989, 5, 30), new LocalDate(2010, 7, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 1),
//		new DateDiffRef(new LocalDate(2003, 2, 20), new LocalDate(2010, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 11),
//		new DateDiffRef(new LocalDate(1970, 10, 27), new LocalDate(2010, 3, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 4),
//		new DateDiffRef(new LocalDate(1980, 1, 1), new LocalDate(2009, 1, 1), DateRoundingMode.FIRST_OF_MONTH, 0),
//		new DateDiffRef(new LocalDate(1985, 12, 31), new LocalDate(2009, 1, 1), DateRoundingMode.FIRST_OF_NEXT_MONTH, 0),
//		new DateDiffRef(new LocalDate(1954, 12, 7), new LocalDate(2009, 12, 1), DateRoundingMode.FIRST_OF_MONTH, 0),
//	};
//
//	public void testRetirementDateValidation() {
//		try {
//			DateUtils.retirementDate(null, 65);
//			fail("Expected IllegalArgumentException");
//		} catch (Exception e) {
//			//OK
//		}
//		try {
//			DateUtils.retirementDate(DEC_01_10, 0);
//			fail("Expected IllegalArgumentException");
//		} catch (Exception e) {
//			//OK
//		}
//		try {
//			DateUtils.retirementDate(DEC_01_10, -1);
//			fail("Expected IllegalArgumentException");
//		} catch (Exception e) {
//			//OK
//		}
//	}
//
//	public void testRetirementDate() {
//		for (RetirementDateRef ref : REF_RETIREMENT_DATE) {
//			assertEquals(ref.getResult(), DateUtils.retirementDate(ref.getDateOfBirth(), ref.getRetirementAge()));
//		}
//		assertEquals(RETIREMENT_65, DateUtils.retirementDate(DEC_01_10, 65));
//		assertEquals(RETIREMENT_65, DateUtils.retirementDate(DEC_15_10, 65));
//		assertEquals(RETIREMENT_65, DateUtils.retirementDate(DEC_16_10, 65));
//		assertEquals(RETIREMENT_65, DateUtils.retirementDate(DEC_31_10, 65));
//		assertEquals(RETIREMENT_60, DateUtils.retirementDate(DEC_01_10, 60));
//		assertEquals(RETIREMENT_60, DateUtils.retirementDate(DEC_15_10, 60));
//		assertEquals(RETIREMENT_60, DateUtils.retirementDate(DEC_16_10, 60));
//		assertEquals(RETIREMENT_60, DateUtils.retirementDate(DEC_31_10, 60));
//		assertEquals(JAN_01_12, DateUtils.retirementDate(DEC_01_10, 1));
//		assertEquals(JAN_01_12, DateUtils.retirementDate(DEC_15_10, 1));
//		assertEquals(JAN_01_12, DateUtils.retirementDate(DEC_16_10, 1));
//		assertEquals(JAN_01_12, DateUtils.retirementDate(DEC_31_10, 1));
//	}
//
//	public void testPeriodValidation() {
//		try {
//			DateUtils.period(null, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH);
//			fail("Expected IllegalArgumentException");
//		} catch (Exception e) {
//			//OK
//		}
//		try {
//			DateUtils.period(DEC_01_10, null, DateRoundingMode.FIRST_OF_MONTH);
//			fail("Expected IllegalArgumentException");
//		} catch (Exception e) {
//			//OK
//		}
//		try {
//			DateUtils.period(DEC_01_10, RETIREMENT_65, null);
//			fail("Expected IllegalArgumentException");
//		} catch (Exception e) {
//			//OK
//		}
//		try {
//			DateUtils.period(RETIREMENT_65, DEC_01_10, DateRoundingMode.FIRST_OF_MONTH);
//			fail("Expected IllegalArgumentException (start date after end date)");
//		} catch (Exception e) {
//			//OK
//		}
//		try {
//			DateUtils.period(DEC_01_10, DEC_01_10, DateRoundingMode.FIRST_OF_NEXT_MONTH);
//			fail("Expected IllegalArgumentException (corrected start date after end date)");
//		} catch (Exception e) {
//			//OK
//		}
//	}
//
//	public void testPeriod_inFractionalYears() {
//		for (DateDiffRef ref : REF_DATE_DIFF_DEC) {
//			assertEquals(ref.getResult().doubleValue(), DateUtils.period(ref.getFrom(), ref.getTill(), ref.getRoundingMode()).inFractionalYears(), 0.01);
//		}
//	}
//
//	public void testPeriod_years() {
//		for (DateDiffRef ref : REF_DATE_DIFF_YEARS) {
//			assertEquals(ref.getResult().intValue(), DateUtils.period(ref.getFrom(), ref.getTill(), ref.getRoundingMode()).getYears());
//		}
//
//		assertEquals(65, DateUtils.period(DEC_01_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(65, DateUtils.period(DEC_15_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(65, DateUtils.period(DEC_16_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(65, DateUtils.period(DEC_31_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//
//		assertEquals(65, DateUtils.period(DEC_01_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(65, DateUtils.period(DEC_15_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(65, DateUtils.period(DEC_16_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(65, DateUtils.period(DEC_31_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//
//		assertEquals(65, DateUtils.period(JAN_01_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(65, DateUtils.period(JAN_15_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(65, DateUtils.period(JAN_16_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(65, DateUtils.period(JAN_31_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getYears());
//
//		assertEquals(64, DateUtils.period(JAN_01_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(64, DateUtils.period(JAN_15_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(64, DateUtils.period(JAN_16_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(64, DateUtils.period(JAN_31_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//
//		assertEquals(0, DateUtils.period(DEC_01_10, JAN_31_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_15_10, JAN_16_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_16_10, JAN_15_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_31_10, JAN_01_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		
//		assertEquals(0, DateUtils.period(DEC_01_10, JAN_31_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_15_10, JAN_16_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_16_10, JAN_15_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_31_10, JAN_01_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		
//		assertEquals(1, DateUtils.period(DEC_01_10, DEC_31_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(1, DateUtils.period(DEC_15_10, DEC_16_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(1, DateUtils.period(DEC_16_10, DEC_15_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//		assertEquals(1, DateUtils.period(DEC_31_10, DEC_01_11, DateRoundingMode.FIRST_OF_MONTH).getYears());
//
//		assertEquals(0, DateUtils.period(DEC_01_10, DEC_31_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_15_10, DEC_16_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_16_10, DEC_15_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//		assertEquals(0, DateUtils.period(DEC_31_10, DEC_01_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getYears());
//	}
//
//	public void testPeriod_months() {
//		for (DateDiffRef ref : REF_DATE_DIFF_MONTHS) {
//			assertEquals(ref.getResult().intValue(), DateUtils.period(ref.getFrom(), ref.getTill(), ref.getRoundingMode()).getMonths());
//		}
//
//		assertEquals(1, DateUtils.period(DEC_01_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(1, DateUtils.period(DEC_15_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(1, DateUtils.period(DEC_16_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(1, DateUtils.period(DEC_31_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//
//		assertEquals(0, DateUtils.period(DEC_01_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_15_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_16_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_31_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//
//		assertEquals(0, DateUtils.period(JAN_01_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(JAN_15_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(JAN_16_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(JAN_31_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//
//		assertEquals(11, DateUtils.period(JAN_01_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(11, DateUtils.period(JAN_15_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(11, DateUtils.period(JAN_16_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(11, DateUtils.period(JAN_31_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//
//		assertEquals(1, DateUtils.period(DEC_01_10, JAN_31_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(1, DateUtils.period(DEC_15_10, JAN_16_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(1, DateUtils.period(DEC_16_10, JAN_15_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(1, DateUtils.period(DEC_31_10, JAN_01_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		
//		assertEquals(0, DateUtils.period(DEC_01_10, JAN_31_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_15_10, JAN_16_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_16_10, JAN_15_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_31_10, JAN_01_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		
//		assertEquals(0, DateUtils.period(DEC_01_10, DEC_31_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_15_10, DEC_16_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_16_10, DEC_15_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//		assertEquals(0, DateUtils.period(DEC_31_10, DEC_01_11, DateRoundingMode.FIRST_OF_MONTH).getMonths());
//
//		assertEquals(11, DateUtils.period(DEC_01_10, DEC_31_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(11, DateUtils.period(DEC_15_10, DEC_16_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(11, DateUtils.period(DEC_16_10, DEC_15_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//		assertEquals(11, DateUtils.period(DEC_31_10, DEC_01_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).getMonths());
//	}
//
//	public void testPeriod_inMonths() {
//
//		assertEquals(65 * 12 + 1, DateUtils.period(DEC_01_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(65 * 12 + 1, DateUtils.period(DEC_15_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(65 * 12 + 1, DateUtils.period(DEC_16_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(65 * 12 + 1, DateUtils.period(DEC_31_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//
//		assertEquals(65 * 12, DateUtils.period(DEC_01_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(65 * 12, DateUtils.period(DEC_15_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(65 * 12, DateUtils.period(DEC_16_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(65 * 12, DateUtils.period(DEC_31_10, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//
//		assertEquals(65 * 12, DateUtils.period(JAN_01_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(65 * 12, DateUtils.period(JAN_15_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(65 * 12, DateUtils.period(JAN_16_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(65 * 12, DateUtils.period(JAN_31_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//
//		assertEquals(65 * 12 - 1, DateUtils.period(JAN_01_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(65 * 12 - 1, DateUtils.period(JAN_15_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(65 * 12 - 1, DateUtils.period(JAN_16_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(65 * 12 - 1, DateUtils.period(JAN_31_11, RETIREMENT_65, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//
//		assertEquals(1, DateUtils.period(DEC_01_10, JAN_31_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(1, DateUtils.period(DEC_15_10, JAN_16_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(1, DateUtils.period(DEC_16_10, JAN_15_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(1, DateUtils.period(DEC_31_10, JAN_01_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		
//		assertEquals(0, DateUtils.period(DEC_01_10, JAN_31_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(0, DateUtils.period(DEC_15_10, JAN_16_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(0, DateUtils.period(DEC_16_10, JAN_15_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(0, DateUtils.period(DEC_31_10, JAN_01_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		
//		assertEquals(12, DateUtils.period(DEC_01_10, DEC_31_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(12, DateUtils.period(DEC_15_10, DEC_16_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(12, DateUtils.period(DEC_16_10, DEC_15_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//		assertEquals(12, DateUtils.period(DEC_31_10, DEC_01_11, DateRoundingMode.FIRST_OF_MONTH).inMonths());
//
//		assertEquals(11, DateUtils.period(DEC_01_10, DEC_31_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(11, DateUtils.period(DEC_15_10, DEC_16_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(11, DateUtils.period(DEC_16_10, DEC_15_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//		assertEquals(11, DateUtils.period(DEC_31_10, DEC_01_11, DateRoundingMode.FIRST_OF_NEXT_MONTH).inMonths());
//	}
//
//	public void testRound() {
//		assertEquals(DEC_01_10, DateUtils.round(DEC_01_10, DateRoundingMode.FIRST_OF_MONTH));
//		assertEquals(DEC_01_10, DateUtils.round(DEC_15_10, DateRoundingMode.FIRST_OF_MONTH));
//		assertEquals(DEC_01_10, DateUtils.round(DEC_16_10, DateRoundingMode.FIRST_OF_MONTH));
//		assertEquals(DEC_01_10, DateUtils.round(DEC_31_10, DateRoundingMode.FIRST_OF_MONTH));
//		assertEquals(JAN_01_11, DateUtils.round(DEC_01_10, DateRoundingMode.FIRST_OF_NEXT_MONTH));
//		assertEquals(JAN_01_11, DateUtils.round(DEC_15_10, DateRoundingMode.FIRST_OF_NEXT_MONTH));
//		assertEquals(JAN_01_11, DateUtils.round(DEC_16_10, DateRoundingMode.FIRST_OF_NEXT_MONTH));
//		assertEquals(JAN_01_11, DateUtils.round(DEC_31_10, DateRoundingMode.FIRST_OF_NEXT_MONTH));
//		assertEquals(DEC_01_10, DateUtils.round(DEC_01_10, DateRoundingMode.MID_MONTH_PIVOT));
//		assertEquals(JAN_01_11, DateUtils.round(DEC_15_10, DateRoundingMode.MID_MONTH_PIVOT));
//		assertEquals(JAN_01_11, DateUtils.round(DEC_16_10, DateRoundingMode.MID_MONTH_PIVOT));
//		assertEquals(JAN_01_11, DateUtils.round(DEC_31_10, DateRoundingMode.MID_MONTH_PIVOT));
//	}
//}
