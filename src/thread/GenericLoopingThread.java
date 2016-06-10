
package thread;


public class GenericLoopingThread extends LoopingThread {

    private static final String className = GenericLoopingThread.class.getName();

    public GenericLoopingThread (String name, Thread loggingHeadThread)
    {
        super(name, loggingHeadThread);
    }

    @Override
    public void run ()
    {
    	System.out.println("Thread is waiting for incoming task...");
        while (isThreadIsActive())
        {
            if (getTask() != null)
            {
            	System.out.println("Start running task...");
                Runnable task = getTask();
                task.run();
                System.out.println("Task done. Thread goes to sleep for next icoming task...");
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
