/*
 * FILE NAME: TestClientSocketThread.java
 *
 * DESCRIPTION:
 *
Source code for class:
 *
 *
TestClientSocketThread.java
 *
 * COPYRIGHT:
 *
(C) Copyright International Business Machines Corporation 1997, 2011.
 *
Licensed Material - Property of IBM - All Rights Reserved.
 *
US Government Users Restricted Rights - Use, duplication, or disclosure
 *
restricted by GSA ADP Schedule Contract with IBM Corp.
 *
 * CLASSIFICATION:
 *
IBM Confidential
 *
OCO Source Materials
 *
 * COMPONENT:
 *
IBM ADMIRA
 *
 * CHANGES:
 *
 * 22.01.2014 Initial version.
 *
 */

package util.server;

import java.io.IOException;

import util.logging.Logger;

public class HttpRequestHandler extends ClientSocketThread {

    public String httpMessage = "";
    private String CN = HttpRequestHandler.class.getSimpleName();

    @Override
    public void handleIncomingData ()
    {
        httpMessage = getHTTPMessage();        
    }

    public String getHTTPMessage ()
    {
    	String MN = "getHTTPMessage()";
        String message = "";
        try
        {
            String header = "";
            int contentLength = 0;
            String inputLine;
            while ((inputBuffer.readLine() != null) && !(inputLine = inputBuffer.readLine()).isEmpty())
            {
                if (inputLine.startsWith("Content-Length"))
                {
                    int idx = inputLine.indexOf(":");
                    String clength = inputLine.substring(idx + 2);
                    contentLength = new Integer(clength);
                }
                header += inputLine + "\n";
            }
            message += header;
            String body = "";
            if (contentLength > 0)
            {
                int read;
                while ((read = inputBuffer.read()) != -1)
                {
                    String sign = Character.toString(((char) read));
                    body += sign;
                    if (sign.equalsIgnoreCase(">"))
                    {
                        body += "\n";
                        contentLength++;// new line is an additional sign which
                                        // will be added to the body, so adjust
                                        // content length
                    }
                    if (body.length() == contentLength)
                        break;
                }
            }
            message += body;
        }
        catch (IOException e)
        {
        	String errMsg = "ERROR: Could NOT read message: " + e.toString();
        	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, errMsg);
            
        }
        return message;
    }
}
