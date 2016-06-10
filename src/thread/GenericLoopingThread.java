
package thread;

import util.logging.StaticLogger;

public class GenericLoopingThread extends LoopingThread {

    private static final String CN = GenericLoopingThread.class.getName();

    public GenericLoopingThread (String name, Thread loggingHeadThread)
    {
        super(name, loggingHeadThread);
    }

    @Override
    public void run ()
    {
    	String MN = "run()";
		StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO, "Thread is waiting for incoming task...");
        while (isThreadIsActive())
        {
            if (getTask() != null)
            {            	
            	StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO, "Start running task...");
                Runnable task = getTask();
                task.run();
                String msg = "Task done. Thread goes to sleep for next icoming task...";
                StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO, msg);                
                resetTask();
            }
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            	System.out.println("Thread interrupted.");
            }
        }

    }

    @Override
    public void releaseResources ()
    {
        // nothing to do
    }

}
