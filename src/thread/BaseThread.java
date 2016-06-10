
package thread;

/**
 * Base thread that defines the logging thread that is calling this thread and
 * its name.
 */
public abstract class BaseThread extends Thread {

    /**
     * Constructor for AdmiraThread.
     * 
     * @param name
     *            Name of this thread.
     * @param loggingHeadThread
     *            Logging receiver.
     */

    protected Thread loggingHeadThread = null;

    public BaseThread (String name, Thread loggingHeadThread)
    {
        super(name);
        this.loggingHeadThread = loggingHeadThread;        
    }

    public void unregisterLogging ()
    {
        
    }
    
    @Override
    public void finalize ()
    {
        unregisterLogging();
    }
}
