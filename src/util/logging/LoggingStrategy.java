package util.logging;

public interface LoggingStrategy {

	static String seperatorString = " - ";
	
	/**
	 * @param loggingClassName
	 * @param loggingMethodName
	 * @param logLevel
	 * @param logMessage
	 */
	public void log(String loggingClassName, 
			        String loggingMethodName, 
			        int logLevel, 
			        String logMessage);

}
