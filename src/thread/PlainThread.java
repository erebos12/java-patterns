
package thread;


public class PlainThread extends BaseThread {

    private static final String className = BaseThread.class.getName();
    private Runnable worker = null;
    private Thread loggingHeadThread = null;

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
        String mn = "run(): ";
        System.out.println(mn + "enter");

        try
        {
            if (worker != null)
            {
            	System.out.println("calling actual worker...");
                worker.run();
            }
        }
        catch (Throwable t)
        {
        	System.out.println(mn + t);
        }
        finally
        {
            
        }
        System.out.println(mn + "exit");
    }
}
