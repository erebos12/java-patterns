package util.logging;

public class Logger {
		
	public enum LogLevel {
		INFO, WARNING, ERROR, FATAL;
	}

	public static LoggingContext ctx = new LoggingContext(new LoggingStdOut());

}
