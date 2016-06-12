package daemon;

import string.StringUtils;

public class DaemonFactory {

	public static BaseDaemon createDaemon(String daemonType, String logFileName, String runFileName) throws Exception
	 {
	      if(StringUtils.isStringNullOrEmpty(daemonType))
	      {	    	  
	         throw new Exception("Unknown or missing daemon: '" + daemonType + "'");
	      }		
	      if(daemonType.equalsIgnoreCase("MyDaemon"))
	      {
	         return new MyDaemon(logFileName, runFileName);	         
	      }	      
	      return null;
	   }

}
