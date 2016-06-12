package net.request;

import java.util.Date;

import util.logging.Logger;

/**
 * RequestManager
 */
public class RequestManager {
    private final String CN = getClass().getName();

    private int maxRetryCount = 0;
    private int retryTime = 0;
    
    private int retries = 0;

    /**
     * Constructor for RequestManager
     * @param maxRetryCount maximum number of retries
     * @param retryTime time between retries in milliseconds
     */
    public RequestManager (int maxRetryCount, int retryTime)
    {
        this.maxRetryCount = maxRetryCount;
        this.retryTime = retryTime;
    }

    public void setMaxRetryCount(int maxRetryCount)
    {
        this.maxRetryCount = maxRetryCount;
    }

    public void setRetryTime(int retryTimeInSec)
    {
        this.retryTime = retryTimeInSec * 1000;
    }

    /**
     * @return true: request retries processed, false: no request retries processed 
     */
    public boolean retriesDone ()
    {
        boolean retriesDone = false;

        if (retries > 0)
        {
            retriesDone = true;
        }

        return retriesDone;
    }
    
    /**
     * @return number of processed request retries
     */
    public int getRetries ()
    {
        return retries;
    }

    /**
     * This method handles the specified request (calls the send method). If the
     * request throws a RetryExeption, it repeats the request handling until the
     * maximum number of retries (constructor parameter). The time between to
     * retries is also specified in the constructor
     * 
     * @param request
     *            request to be handled
     * @return response
     */
    public Response handleRequest (Request request) throws Exception
    {
        final String MN = "handleRequest(): ";

        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "enter");

        Response response = null;
        
        try
        {
            response = request.send();
        }
        catch (RetryException e)
        {            
            while (retries < maxRetryCount)
            {
                try
                {
                    Thread.sleep(retryTime);

                    Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "Retry " + (retries + 1) + " at: " + new Date());
                    
                    response = request.send();
                    break;
                }
                catch (InterruptedException e1)
                {
                    throw new Exception(e1.toString());
                }
                catch (RetryException e1)
                {
                    // Do not remove this catch block !!!
                    // Cause we do nothing here, the retry loop will be executed
                	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, " retry exception thrown while sending request: " + e1.getMessage());
                }
                catch (Exception e1)
                {
                	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR,
                        " general exception thrown while sending request: " + e1.getMessage());
                	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, "Exit retry loop now!");
                    throw e1;
                }
                retries++;
            }
        }
        
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "exit");

        return response;
    }
}
