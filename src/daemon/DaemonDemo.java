package daemon;

import junit.framework.TestCase;

/**
 * HOW TO WRITE AN AREMA DAEMON:
 *  The following code shows a sample for an AREMA Daemon. 
 *  1. Write YourAremaDaemonClass which extends the class COM.ibm.dma.admira.util.daemon.AremaDaemon 
 *  2. Implement the main-Method (static): 
 *    a) Read in in args of main
 *    b) Construct your concrete AremaDaemon - YourAremaDaemonClass
 *  3. Implement your WorkerClass which provides the real functionality (+ JUnit-Test) 
 *  4. Implement the initialize(), start() and stop() methods which calls the WorkClass interfaces
 *  THAT'S IT!!!
 *
 */
public class DaemonDemo extends TestCase {    

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

        // ATTENTION: DON'T FORGET THE MAIN METHOD. A REAL DAEMON MUST HAVE A MAIN-METHOD!
        // SHOULD BE STATIC !!!! Here for testing just a non-static method.
        public void main (String[] args)
        {
            // Read args of main method here. Here default values will defined by local variables 
            String LOG_FILE_NAME_PREFIX = "daemonXY";
            String runFileName = "daemonXY_localhost_8080.run";
            
            DaemonXY daemonXY = new DaemonXY(LOG_FILE_NAME_PREFIX, runFileName);      
            //Optional: Setting another runtimeIntervalInMsec e.g. 1000 or 3000
            //  Default is 500 ms in case you don't call the setter
            daemonXY.setRunFileIntervalInMsec(1000);
            // NOW START THE DAEMON
            daemonXY.startMeAsDaemon();
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
}

