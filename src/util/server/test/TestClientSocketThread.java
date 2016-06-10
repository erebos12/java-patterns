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

package util.server.test;

import java.io.IOException;

import util.server.ClientSocketThread;

public class TestClientSocketThread extends ClientSocketThread {

    private String message = null;

    @Override
    public void handleIncomingData ()
    {
        try
        {
            message = inputBuffer.readLine();
            if (message.startsWith("REQUEST"))
            {
                // System.out.println("SUCCESS: received message: " + message);
                outputWriter.write("RESPONSE\n");
                outputWriter.flush();
            }
            else
            {
                System.out.println("ERROR: received invalid message: " + message);
            }
        }
        catch (IOException e)
        {
            System.out.println("ERROR: Could NOT read message: " + e.toString());
        }
    }
}
