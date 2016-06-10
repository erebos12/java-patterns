package thread;

import java.util.Vector;

import util.logging.Logger;


public class ThreadManager {

    private Vector<LoopingThread> threadPool;
    private long threadSleepInterval = 100;
    private String CN = getClass().getName();

    // TODO: Logging and threadSleppInterval to constructor
    public ThreadManager ()
    {
        threadPool = new Vector<LoopingThread>();
        Logger.ctx.log(CN, "constructor", Logger.LogLevel.INFO, "ThreadManager constructed.");
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
    	String MN = "addThread()";
    	Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "added thread " + thread.getName());
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
            	Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, errMsg);              	
                Thread.sleep(threadSleepInterval);
            }
            catch (InterruptedException ex)
            {
                // use className variable here
				Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, ex.toString());            	
            }
        }
        freeThread = getFreeThread();
        if (freeThread != null)
        {
			Logger.ctx.log(CN, MN, Logger.LogLevel.INFO,
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
