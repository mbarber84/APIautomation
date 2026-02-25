package utils;

import org.testng.asserts.SoftAssert;

public class softAssertionUtil {

        private static SoftAssert softAssertInstance;

        private softAssertionUtil(){
        }

    public static SoftAssert getInstance() {
        if (softAssertInstance == null) {
            softAssertInstance = new SoftAssert();
        }
        return softAssertInstance;
    }


    public static void assertTrue(boolean condition, String message) {
            try {
                getInstance().assertTrue(condition, message);
            } catch (AssertionError e) {
                getInstance().fail(message);
            }
        }

        public static void assertEquals(Object actual, Object expected, String message) {
            try {
                getInstance().assertEquals(actual, expected, message);
            } catch (AssertionError e) {
                getInstance().fail(message);
            }
        }

        public static void assertNotEquals(Object actual, Object expected, String message) {
            try {
                getInstance().assertNotEquals(actual, expected, message);
            } catch (AssertionError e) {
                getInstance().fail(message);
            }
        }

        public static void assertFalse(boolean condition, String message) {
            try {
                getInstance().assertFalse(condition, message);
         } catch (AssertionError e) {
                getInstance().fail(message);
            }
        }

        public static void assertAll() {
            getInstance().assertAll();
        }
    }






