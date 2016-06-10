package thread;

import java.util.Vector;

import util.logging.StaticLogger;


public class ThreadManager {

    private Vector<LoopingThread> threadPool;
    private long threadSleepInterval = 100;
    private String className = getClass().getName();

    // TODO: Logging and threadSleppInterval to constructor
    public ThreadManager ()
    {
        threadPool = new Vector<LoopingThread>();
        System.out.println("ThreadManager constructed.");
    }
    
    public void constructGenericThreadPool(int threadPoolSize, Thread callingThread)
    {
        threadPool = new Vector<LoopingThread>();
        for (int i = 0; i < threadPoolSize; i++)
        {
            GenericLoopingThread glt = new GenericLoopingThread(callingThread.getName() + "_" + i,
                                                                callingThread);
            glt.start();
            threadPool.add(glt);
        }
    }

    public void addThread (LoopingThread thread)
    {
    	System.out.println("added thread " + thread.getName());
        threadPool.add(thread);
    }

    public void runTask (Runnable runTask)
    {
    	String MN = "runTask()";
        LoopingThread freeThread = null;
        while (getFreeThread() == null)
        {
            try
            {
            	String errMsg = "No free thread available. Waiting for " + threadSleepInterval + " msecs.";
            	StaticLogger.logger.log(className, MN, StaticLogger.LogLevel.ERROR, errMsg);              	
                Thread.sleep(threadSleepInterval);
            }
            catch (InterruptedException ex)
            {
                // use className variable here
				StaticLogger.logger.log(className, MN, StaticLogger.LogLevel.ERROR, ex.toString());            	
            }
        }
        freeThread = getFreeThread();
        if (freeThread != null)
        {
			StaticLogger.logger.log(className, MN, StaticLogger.LogLevel.ERROR,
					"Fetching free thread out of pool " + freeThread.getName());           	
            freeThread.setTask(runTask);           
        }
    }

    private LoopingThread getFreeThread ()
    {
        LoopingThread freeThread = null;
        for (int i = 0; i < threadPool.size(); i++)
        {
            if (threadPool.get(i).isFree())
            {
                freeThread = threadPool.get(i);
                break;
            }
        }
        return freeThread;
    }

    public void shutdownThread (long threadID)
    {
        for (int i = 0; i < threadPool.size(); i++)
        {
            if (threadPool.get(i).getId() == threadID)
            {
                threadPool.get(i).shutdownThread();
                break;
            }
        }
    }

    public void shutdownAllThreads ()
    {
        for (int i = 0; i < threadPool.size(); i++)
        {
            threadPool.get(i).shutdownThread();
        }
    }

    public int getThreadPoolSize ()
    {
        return threadPool.size();
    }
    
    public int getNumOfFreeThreads ()
    {
        int freeThreadCount = 0;
        for (int i = 0; i < threadPool.size(); i++)
        {
            if (threadPool.get(i).isFree())
            {
                freeThreadCount += 1;
            }
        }
        return freeThreadCount;
    }
    
    public int getNumOfInactiveThreads ()
    {
        int numOfInactiveThreads = 0;
        for (LoopingThread thread : threadPool)
        {
            if (!thread.isThreadIsActive())
            {
                numOfInactiveThreads += 1;
            }
        }
        return numOfInactiveThreads;
    }
}
