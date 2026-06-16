import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DateTest {

  @Test
  void nextDate_tc01() {
    Date today = new Date(1700, 6, 20);
    Date expectedTomorrow = new Date(1700, 6, 21);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc02() {
    Date today = new Date(2005, 4, 15);
    Date expectedTomorrow = new Date(2005, 4, 16);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc03() {
    Date today = new Date(1901, 7, 20);
    Date expectedTomorrow = new Date(1901, 7, 21);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc04() {
    Date today = new Date(3456, 3, 27);
    Date expectedTomorrow = new Date(3456, 3, 28);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc05() {
    Date today = new Date(1500, 2, 17);
    Date expectedTomorrow = new Date(1500, 2, 18);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc06() {
    Date today = new Date(1700, 6, 29);
    Date expectedTomorrow = new Date(1700, 6, 30);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc07() {
    Date today = new Date(1800, 11, 29);
    Date expectedTomorrow = new Date(1800, 11, 30);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc08() {
    Date today = new Date(3453, 1, 29);
    Date expectedTomorrow = new Date(3453, 1, 30);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc09() {
    Date today = new Date(444, 2, 29);
    Date expectedTomorrow = new Date(444, 3, 1);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc10() {
    Date today = new Date(2005, 4, 30);
    Date expectedTomorrow = new Date(2005, 5, 1);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc11() {
    Date today = new Date(3453, 1, 30);
    Date expectedTomorrow = new Date(3453, 1, 31);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc12() {
    Date today = new Date(3456, 3, 30);
    Date expectedTomorrow = new Date(3456, 3, 31);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc13() {
    Date today = new Date(1901, 7, 31);
    Date expectedTomorrow = new Date(1901, 8, 1);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc14() {
    Date today = new Date(3453, 1, 31);
    Date expectedTomorrow = new Date(3453, 2, 1);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_tc15() {
    Date today = new Date(3456, 12, 31);
    Date expectedTomorrow = new Date(3457, 1, 1);
    assertEquals(expectedTomorrow, today.nextDate());
  }

  @Test
  void nextDate_invalid_tc16() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Date(1500, 2, 31)
    );
  }

  @Test
  void nextDate_invalid_tc17() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Date(1500, 2, 29)
    );
  }

  @Test
  void nextDate_invalid_tc18() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Date(-1, 10, 20)
    );
  }

  @Test
  void nextDate_invalid_tc19() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Date(1458, 15, 12)
    );
  }

  @Test
  void nextDate_invalid_tc20() {
    assertThrows(
      IllegalArgumentException.class,
      () -> new Date(1975, 6, -50)
    );
  }

  @Test
  void leapYear_century_notDivisibleBy400() {
      Date d = new Date(1900, 2, 28);
      Date next = d.nextDate();
      assertEquals(new Date(1900, 3, 1), next);
  }

  @Test
  void leapYear_divisibleBy400() {
      Date d = new Date(2000, 2, 28);
      Date next = d.nextDate();
      assertEquals(new Date(2000, 2, 29), next);
  }

  @Test
  void leapYear_feb29_toMarch1() {
      Date d = new Date(2000, 2, 29);
      Date next = d.nextDate();
      assertEquals(new Date(2000, 3, 1), next);
  }

  @Test
  void invalid_day_zero() {
      assertThrows(IllegalArgumentException.class,
          () -> new Date(2023, 5, 0));
  }

  @Test
  void invalid_day_too_large() {
      assertThrows(IllegalArgumentException.class,
          () -> new Date(2023, 5, 32));
  }

  @Test
  void invalid_30day_month_overflow() {
      assertThrows(IllegalArgumentException.class,
          () -> new Date(2023, 4, 31)); // April has 30 days
  }

  @Test
  void invalid_feb_nonLeap_day29() {
      assertThrows(IllegalArgumentException.class,
          () -> new Date(2021, 2, 29));
  }

  @Test
  void invalid_feb_leap_day30() {
      assertThrows(IllegalArgumentException.class,
          () -> new Date(2020, 2, 30));
  }

  @Test
  void equals_true_case() {
      Date d1 = new Date(2023, 5, 10);
      Date d2 = new Date(2023, 5, 10);
      assertTrue(d1.equals(d2));
  }

  @Test
  void equals_false_case() {
      Date d1 = new Date(2023, 5, 10);
      Date d2 = new Date(2023, 5, 11);
      assertFalse(d1.equals(d2));
  }

  @Test
  void equals_wrong_object_type() {
      Date d = new Date(2023, 5, 10);
      assertFalse(d.equals("not a date"));
  }

  @Test
  void toString_test() {
      Date d = new Date(2023, 5, 10);
      assertEquals("2023/May/10", d.toString());
  }
  @Test
  void leapYear_normal_true() {
      Date d = new Date(2024, 2, 28);
      assertEquals(new Date(2024, 2, 29), d.nextDate());
  }
  @Test
  void leapYear_century_false() {
      Date d = new Date(1900, 2, 28);
      assertEquals(new Date(1900, 3, 1), d.nextDate());
  }
  @Test
  void leapYear_century_true() {
      Date d = new Date(2000, 2, 28);
      assertEquals(new Date(2000, 2, 29), d.nextDate());
  }
  @Test
  void endOfMonth_30day() {
      Date d = new Date(2023, 4, 30);
      assertEquals(new Date(2023, 5, 1), d.nextDate());
  }
  @Test
  void endOfMonth_feb_leap29() {
      Date d = new Date(2024, 2, 29);
      assertEquals(new Date(2024, 3, 1), d.nextDate());
  }
  @Test
  void equals_null_branch() {
      Date d = new Date(2023, 5, 10);
      assertFalse(d.equals(null));
  }
  @Test
  void thirtyDayMonth_branch() {
      Date d = new Date(2023, 6, 30);
      assertEquals(new Date(2023, 7, 1), d.nextDate());
  }
  @Test
  void toString_branch() {
      Date d = new Date(2023, 6, 10);
      d.toString();
  }
  @Test
  void test_toString() {
      Date d = new Date(2023, 6, 10);
      assertEquals("2023/June/10", d.toString());
  }
  @Test
  void equals_null_object() {
      Date d = new Date(2023, 6, 10);
      assertFalse(d.equals(null));
  }
  @Test
  void equals_different_type() {
      Date d = new Date(2023, 6, 10);
      assertFalse(d.equals("not a date"));
  }
  @Test
  void invalid_month_low() {
      assertThrows(IllegalArgumentException.class,
          () -> new Date(2023, 0, 10));
  }

  @Test
  void invalid_month_high() {
      assertThrows(IllegalArgumentException.class,
          () -> new Date(2023, 13, 10));
  }
  @Test
  void leap_year_divisible_by_4() {
      assertTrue(new Date(2024, 1, 1).isLeapYear());
  }

  @Test
  void not_leap_century_1900() {
      assertFalse(new Date(1900, 1, 1).isLeapYear());
  }

  @Test
  void leap_century_2000() {
      assertTrue(new Date(2000, 1, 1).isLeapYear());
  }
  @Test
  void end_of_month_feb_non_leap() {
      Date d = new Date(2021, 2, 28);
      assertEquals(new Date(2021, 3, 1), d.nextDate());
  }

  @Test
  void end_of_month_april() {
      Date d = new Date(2023, 4, 30);
      assertEquals(new Date(2023, 5, 1), d.nextDate());
  }
  @Test
  void equals_self_and_other() {
      Date d1 = new Date(2023, 6, 10);
      Date d2 = new Date(2023, 6, 10);

      assertTrue(d1.equals(d2));
      assertTrue(d1.equals(d1));
  }
  @Test
  void toString_force() {
      Date d = new Date(2023, 12, 31);
      String s = d.toString();
      assertNotNull(s);
  }
  @Test
  void constructor_and_getters() {
      Date d = new Date(2024, 5, 20);

      assertEquals(2024, d.getYear());
      assertEquals(5, d.getMonth());
      assertEquals(20, d.getDay());
  }
  @Test
  void boundary_recheck_setDay() {
      Date d = new Date(2023, 4, 30); // 30-day month boundary
      Date next = d.nextDate();
      assertEquals(new Date(2023, 5, 1), next);
  }
}