package daemon;

import java.io.File;

import util.logging.Logger;

public class DaemonMain {

	public static String runFileName = null;
	public class WorkerClass {
		
		private String CN = WorkerClass.class.getName();
		/*
		 * This class is the real worker class. Here the actual business logic
		 * should be implemented. Separate class to be more testable as JUnit
		 * test.
		 */
		public void startWork() {
			Logger.ctx.log(CN, "startWork", Logger.LogLevel.INFO, "doing something here...");
		}

		public void stopWork() {
			Logger.ctx.log(CN, "stopWork", Logger.LogLevel.INFO, "stopping my work here...");
		}
	}

	public class DaemonXY extends BaseDaemon {
		private WorkerClass worker = null;

		public DaemonXY(String logFileName, String runFileName) {
			super(DaemonXY.class.getName(), logFileName, runFileName);
		}

		@Override
		public void initialize() {
			worker = new WorkerClass();
		}

		@Override
		public void start() {
			worker.startWork();
		}

		@Override
		public void stop() {
			worker.stopWork();
		}
	}

	public DaemonMain() {
		
		// 0. Mandatory: Read args of main method here. Here default values will
		// defined by local variables
		// You could read them from console or config file as config parameters
		String LOG_FILE_NAME_PREFIX = "daemonXY";
		runFileName = "daemonXY_localhost_8080.run";
		// 1. Mandatory: Construct the daemon
		DaemonXY daemonXY = new DaemonXY(LOG_FILE_NAME_PREFIX, runFileName);
		// 2. Optional: Setting another runtimeIntervalInMsec e.g. 1000 or 3000
		// Default is 500 ms in case you don't call this setter
		daemonXY.setRunFileIntervalInMsec(1000);
		// 3. Mandatory: NOW START THE DAEMON
		daemonXY.startMeAsDaemon();
	}

	public static void main(String[] args) {
		String CN = DaemonMain.class.getName();
		String MN = "main()";
		DaemonMain daemonMain = new DaemonMain();
		try {
			// Wait 5 secs and then delete the run file
			// what causes stopping the daemon
			Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "Waiting got 5 secs...");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File runFile = new File(runFileName);
		if (runFile.exists())
		{
			Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "Deleting runfile now...");
			runFile.delete();
		}
	}

}
