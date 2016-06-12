package daemon;

import util.logging.Logger;

public class MyDaemon extends BaseDaemon {
	
	private WorkerClass worker = null;

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

	public MyDaemon(String logFileName, String runFileName) 
	{
		super(MyDaemon.class.getName(), logFileName, runFileName);
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

