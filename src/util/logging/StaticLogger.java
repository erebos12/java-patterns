package util.logging;

public class StaticLogger {
		
	public enum LogLevel {
		INFO, WARNING, ERROR, FATAL;
	}

	public static LoggingContext logger = new LoggingContext(new LoggingStdOut());

}
