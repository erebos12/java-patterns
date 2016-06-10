/*
 * FILE NAME: TestHelper.java
 *
 * DESCRIPTION:
 *
 *   Source code for class TestHelper.java
 *
 * COPYRIGHT:
 *   (C) Copyright International Business Machines Corporation 1997, 2016.
 *   Licensed Material - Property of IBM - All Rights Reserved.
 *
 * CLASSIFICATION:
 *   IBM Confidential
 *
 * COMPONENT:
 *   IBM Archive and Essence Manager / IBM LTFS Storage Manager
 *
 * CHANGES:
 *
 *  $XX 22.01.2016 initial version (alexsahm).
 */

package test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Random;

import junit.framework.TestCase;
import util.logging.*;
import util.server.*;
/**
 * TestHelper
 */
public class TestHelper extends TestCase {

    public static int MIN_PORT = 1000;
    public static int MAX_PORT = 9999;
    
    /**
     * Helper method to let a test fail including error message
     * 
     * @param failingClassName
     *     return string of class.getSimpleName()
     * @param failingMethodName
     *     plain method name without any appendix (No '()' or ':') 
     * @param errMsg
     *     your custom error message based on your test
     */
    public static void failTestWithErrorMsg (String failingClassName, String failingMethodName, String errMsg)
    {
        String msg = "TEST-FAILED: " + failingClassName + "." + failingMethodName + "() - " + errMsg;
        StaticLogger.logger.log(failingClassName, failingMethodName, 1, errMsg);        
        fail(msg);
    }

    /**
     * Method converts stack trace of exception to string
     * 
     * @param aThrowable
     *     Exception that should be converted to string
     * @return
     *     String of exception stack trace
     */
    public static String exceptionStackTraceToString (Throwable aThrowable)
    {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    /**
     * Helper method to initialize SimpleServer
     * 
     * @param CN
     *    Calling class name
     * @param MN
     *    Calling method name
     * @param port
     *    port on which SimplerServer shall run
     * @param threadPoolSize
     *    number of threads for SimpleServer
     * @param simpleSeverHandler
     *    Handler which proceeds request in SimpleServer
     * @return
     *    usable SimplerServer
     */
    public static SimpleServer initSimpleServer (String CN, 
                                                 String MN, 
                                                 int port,
                                                 int threadPoolSize,
                                                 String simpleSeverHandler)
    {
        SimpleServer simpleServer = null;
        try
        {            
            simpleServer = new SimpleServer(port, threadPoolSize, simpleSeverHandler);
            if (!simpleServer.getIsActive())
            {
                String errMsg = "Could not start SimpleServer on port: " + port;
                TestHelper.failTestWithErrorMsg(CN, MN, errMsg);
            }
        }
        catch (Exception e)
        {
            TestHelper.failTestWithErrorMsg(CN, MN,
                                            TestHelper.exceptionStackTraceToString(e));
        }
        return simpleServer;
    }
    
    /**
     * Generate a randomized INTEGER between min and max
     * 
     * @param min
     *   Minimum of range 
     * @param max
     *   Maximum of range 
     * @return
     *   randomized int between min and max
     */
    public static int randInt (int min, int max)
    {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
