package daemon;

import java.io.File;

import util.logging.Logger;

public class DaemonMain {

	public static String runFileName = null;

	public DaemonMain() {
		
		// 0. Mandatory: Read args of main method here. Here default values will
		// defined by local variables
		// You could read them from console or config file as config parameters
		String LOG_FILE_NAME_PREFIX = "daemonXY";
		runFileName = "daemonXY_localhost_8080.run";
		String daemonToConstruct = "MyDaemon";
		try {
			// 1. Mandatory: Construct the daemon
			BaseDaemon daemon = DaemonFactory.createDaemon(daemonToConstruct, LOG_FILE_NAME_PREFIX, runFileName);
			// 2. Optional: Setting another runtimeIntervalInMsec e.g. 1000 or 3000
			// Default is 500 ms in case you don't call this setter
			daemon.setRunFileIntervalInMsec(1000);
			// 3. Mandatory: NOW START THE DAEMON
			daemon.startMeAsDaemon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
