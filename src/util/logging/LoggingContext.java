package util.logging;

public class LoggingContext {

	private LoggingStrategy strategy;

	public LoggingContext(LoggingStrategy strategy) {
		this.strategy = strategy;
	}

	public void log(String CN, String MN, StaticLogger.LogLevel logLevel, String logMessage) 
	{
		strategy.log(CN, MN, logLevel, logMessage);
	}
}
