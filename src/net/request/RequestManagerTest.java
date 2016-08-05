package net.request;

import test.TestHelper;
import junit.framework.TestCase;

public class RequestManagerTest extends TestCase {

    private int counter = 0;
    private String CN = RequestManagerTest.class.getSimpleName();

    public class RequestCallerMockup {

        public int answerAfterRetries = 0;

        public void restJobRequest () throws Exception
        {
            if (counter <= answerAfterRetries)
            {
                counter++;
                throw new Exception();
            }
        }

        public void setAnswerAfterRetries (int answerAfterRetries)
        {
            this.answerAfterRetries = answerAfterRetries;
        }
    }

    public class JobRequest implements Request {

        RequestCallerMockup mock = new RequestCallerMockup();

        JobRequest (int answerAfterRetries)
        {
            mock.setAnswerAfterRetries(answerAfterRetries);
        }

        @Override
        public Response send () throws RetryException
        {
            try
            {
                mock.restJobRequest();
            }
            catch (Exception e)
            {
                throw new RetryException();
            }

            return null;
        }
    }

    public void testSuccessAfter2Retries ()
    {
        String MN = "testSuccessAfter2Retries";
        int retryCount = 6;
        int retryTime = 1000;
        RequestManager rm = new RequestManager(retryCount, retryTime);
        JobRequest jr = new JobRequest(2);
        Exception ex = null;
        try
        {
            rm.handleRequest(jr);
            int expectedRetries = 2; // answer after 2 retries
            if (rm.getRetries() != expectedRetries)
            {
                TestHelper.failTestWithErrorMsg(CN, MN, "Expected retries: "
                    + expectedRetries + " but received: " + rm.getRetries());
            }
        }
        catch (Exception e)
        {
           ex = e;
        }
        if (ex != null)
        {
            TestHelper.failTestWithErrorMsg(CN, MN,
                                            "Excpetion was not expected but received: "
                                                + ex.toString());
        }
    }

    public void testNoAnswer5Retries ()
    {
        String MN = "testNoAnswer5Retries";
        int retryCount = 5;
        int retryTime = 500;
        RequestManager rm = new RequestManager(retryCount, retryTime);
        JobRequest jr = new JobRequest(7);
        Exception ex = null;
        try
        {
            rm.handleRequest(jr);
            int expectedRetries = 5; // no answer even after 5 retries
            if (rm.getRetries() != expectedRetries)
            {
                TestHelper.failTestWithErrorMsg(CN, MN, "Expected retries: "
                    + expectedRetries + " but received: " + rm.getRetries());
            }
        }
        catch (Exception e)
        {
            ex = e;
        }
        if (ex != null)
        {
            TestHelper.failTestWithErrorMsg(CN, MN,
                                            "Excpetion was not expected but received: "
                                                + ex.toString());
        }
    }

    public void testNoRetries ()
    {
        String MN = "testNoRetries";
        int retryCount = 0;
        int retryTime = 0;
        RequestManager rm = new RequestManager(retryCount, retryTime);
        JobRequest jr = new JobRequest(4);        
        try
        {
            rm.handleRequest(jr);
            int expectedRetries = 0; // no retries
            if (rm.getRetries() != expectedRetries)
            {
                TestHelper.failTestWithErrorMsg(CN, MN, "Expected retries: "
                    + expectedRetries + " but received: " + rm.getRetries());
            }
        }
        catch (Exception e)
        {
        	TestHelper.failTestWithErrorMsg(CN, MN,
                    "Excpetion was not expected but received: "
                        + e.toString());
        }       
    }
}
