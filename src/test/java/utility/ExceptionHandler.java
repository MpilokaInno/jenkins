package utility;

import org.junit.Assert;

import static org.junit.Assert.fail;

public class ExceptionHandler {

        //	Function to handle exceptions
        public static void HandleException(Exception e, String sError) {
            try {
                System.out.println(sError);
                fail(sError);
            } catch (Exception ex) {
                System.out.println(ex.getStackTrace().toString());
                Assert.fail(sError);
            }
        }
        public static void HandleAssertion(String sError) {
            try {
                System.out.println(sError);
                fail(sError);
            } catch (Exception e) {
                System.out.println(e.getStackTrace().toString());
                Assert.fail(sError);
            }
        }

        public static void HandleScreenShotException (Exception e, String sError) {
            try {
                System.out.println(sError);
                System.out.println(e.getStackTrace());
                Assert.fail(sError);
            } catch (Exception ex) {
                System.out.println(ex.getStackTrace().toString());
                Assert.fail(sError);
            }
        }

    }

