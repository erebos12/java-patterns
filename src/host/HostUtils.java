package host;

import java.net.InetAddress;
import java.net.UnknownHostException;

import string.StringUtils;
import util.logging.Logger;

public class HostUtils {

	private static String CN = HostUtils.class.getName();

	public static String getHostname() {
		String MN = "getHostname(): ";
		String hostname = "localhost";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			String errMsg = "Could not acquire hostname for localhost: " + e.toString();
			Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, errMsg);
		}
		return hostname;
	}

	public static String getHomeSysVar() {
		String MN = "getHomeSysVar(): ";
		String path = System.getProperty("user.home");
		if (StringUtils.isStringNullOrEmpty(path)) {
			String msg = "Error while acquiring system variable user.home. Mandatory for path location of runfile.";
			Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, msg);
			return null;
		}
		return path;
	}
}
