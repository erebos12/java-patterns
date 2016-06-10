
package thread;

import util.logging.Logger;

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
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "enter...");

        try
        {
            if (worker != null)
            {
            	Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "calling actual worker...");
                worker.run();
            }
        }
        catch (Throwable t)
        {
        	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, t.toString());
        }
        finally
        {
            
        }
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "exit");
    }
}
