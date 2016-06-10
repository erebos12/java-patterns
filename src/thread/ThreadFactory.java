
package thread;

import util.logging.StaticLogger;

/**
 * This class manages creation of threads in the current JVM process of the 
 * application.
 */
public class ThreadFactory {
    
    private static final String CN  = ThreadFactory.class.getName();
    
    
    /**
     * Creates a new thread with the specified ID and starts the execution of 
     * that thread. The thread will execute the <code>run()</code> method of 
     * the <code>Runnable</code> object and append its logging output to the
     * output of a logging head thread. 
     * @param threadName Name for the new thread.
     * @param runnable Object executing the actual business logic of the new thread.
     * @param loggingHeadThread Optional head thread for logging of the new 
     *                          thread. If null, the current thread will be set
     *                          as the head thread.
     * @return Thread object (notice that this thread is already started).
     * @throws DMAeException with error code <code>DMAeError.FAILED_TO_START_THREAD</code>
     *                       in case the new thread could not be created or started.
     */
    public static Thread startThread(String threadName, Runnable runnable, 
                                     Thread loggingHeadThread) throws Exception
    {
        String MN = "startThread(): ";
        
        try
        {
            if (loggingHeadThread == null)
            {
                loggingHeadThread = Thread.currentThread();
            }
            
            StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO,
                       "creating new thread with name " + threadName + "...");
            Thread t = new PlainThread(threadName, runnable, loggingHeadThread);
            
            StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO,
                       threadName + "...");
            t.start();
            
            StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.INFO, "exit");
            return t;
        }
        catch (Exception e)
        {
        	StaticLogger.logger.log(CN, MN, StaticLogger.LogLevel.ERROR, e.toString());
            throw e;
        }
    }
    
    // TODO consider function for running an arbitrary method of an object
    // (by using Reflection and constructing an internal Runnable)

}
