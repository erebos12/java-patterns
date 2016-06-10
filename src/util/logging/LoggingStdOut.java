package util.logging;

public class LoggingStdOut implements LoggingStrategy {

	@Override
	public void log(String loggingClassName, String loggingMethodName, StaticLogger.LogLevel logLevel, String logMessage) {

		System.out.println(  loggingClassName + seperatorString 
				           + loggingMethodName + seperatorString 
				           + logLevel + seperatorString 
				           + logMessage);
	}
}
