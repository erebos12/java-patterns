
package thread;

import util.logging.StaticLogger;

public class PlainThread extends BaseThread {

    private static final String CN = BaseThread.class.getName();
    private Runnable worker = null;    

    public PlainThread (String name, Runnable worker,
                        Thread loggingHeadThread)
    {
        super(name, loggingHeadThread);
        this.worker = worker;

    }

    /**
     * Thread execution method.
     */
    public void run ()
    {
        String MN = "run(): ";
        StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO, "enter...");

        try
        {
            if (worker != null)
            {
            	StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO, "calling actual worker...");
                worker.run();
            }
        }
        catch (Throwable t)
        {
        	StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.ERROR, t.toString());
        }
        finally
        {
            
        }
        StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO, "exit");
    }
}
