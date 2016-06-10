package util.logging;

/**
 * @author erebos
 * Concrete Logging strategy for System.out.println
 * to print logs in std out (console)
 *
 */
public class LoggingStdOut implements LoggingStrategy {

	@Override
	public void log(String loggingClassName, String loggingMethodName, Logger.LogLevel logLevel, String logMessage) {

		System.out.println(  loggingClassName + seperatorString 
				           + loggingMethodName + seperatorString 
				           + logLevel + seperatorString 
				           + logMessage);
	}
}
