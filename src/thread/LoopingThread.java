
package thread;


public abstract class LoopingThread extends BaseThread {
   
    private Runnable task = null;
    private boolean threadIsActive = true;
    private static final String className = LoopingThread.class.getName();
       
    public LoopingThread (String name, Thread loggingHeadThread)
    {
        super(name, loggingHeadThread);
    }

    public boolean isFree ()
    {
        return (task == null);
    }
    public Runnable getTask ()
    {
        return task;
    }

    public void setTask (Runnable task)
    {
        this.task = task;
    }
    
    public void resetTask ()
    {
        this.task = null;
    }

    public boolean isThreadIsActive ()
    {
        return threadIsActive;
    }

    public void setThreadIsActive (boolean threadIsActive)
    {
        this.threadIsActive = threadIsActive;
    }

    public abstract void run ();
    
    public abstract void releaseResources ();
    
    public void shutdownThread ()
    {
    	System.out.println("Shutting down thread " + getName());
        releaseResources();        
        unregisterLogging();
        threadIsActive = false;
    }

}
