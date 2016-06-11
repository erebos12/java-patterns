package daemon;

import junit.framework.TestCase;

/**
 * HOW TO WRITE AN AREMA DAEMON:
 *  The following code shows a sample for an AREMA Daemon. 
 *  1. Write YourAremaDaemonClass which extends the class COM.ibm.dma.admira.util.daemon.BaseDaemon 
 *  2. Implement the main-Method (static): 
 *    a) Read in in args of main
 *    b) Construct your concrete Daemon - YourDaemonClass
 *  3. Implement your WorkerClass which provides the real functionality (+ JUnit-Test) 
 *  4. Implement the initialize(), start() and stop() methods which calls the WorkClass interfaces
 *  THAT'S IT!!!
 *
 */
public class DaemonTest extends TestCase {    

    public class WorkerClass {
        /* 
         * This class is the real worker class.
         * Here the actual business logic should be implemented.
         * Separate class to be more testable as JUnit test.
         */        
        public void startWork(){}
        public void stopWork(){}                    
    }

    public class DaemonXY extends BaseDaemon
    {
        private WorkerClass worker = null;
        
		public DaemonXY(String logFileName, String runFileName)
        {
            super(DaemonXY.class.getName(), logFileName, runFileName);
        }

        @Override
        public void initialize ()
        {
            worker = new WorkerClass();
        }

        @Override
        public void start ()
        {
            worker.startWork();           
        }

        @Override
        public void stop ()
        {
            worker.stopWork();
        } 
    }   
    // ATTENTION: DON'T FORGET THE MAIN METHOD. A REAL DAEMON MUST HAVE A STATIC MAIN-METHOD!
    // This method testDaemon() represents the static main method
    public void testDaemon()
    {
    	//0. Mandatory: Read args of main method here. Here default values will defined by local variables 
    	//   You could read them from console or config file as config parameters
        String LOG_FILE_NAME_PREFIX = "daemonXY";
        String runFileName = "daemonXY_localhost_8080.run";
        //1. Mandatory: Construct the daemon
        DaemonXY daemonXY = new DaemonXY(LOG_FILE_NAME_PREFIX, runFileName);      
        //2. Optional: Setting another runtimeIntervalInMsec e.g. 1000 or 3000
        //   Default is 500 ms in case you don't call this setter
        daemonXY.setRunFileIntervalInMsec(1000);
        //3. Mandatory: NOW START THE DAEMON
        daemonXY.startMeAsDaemon();    	 	
    }
}

