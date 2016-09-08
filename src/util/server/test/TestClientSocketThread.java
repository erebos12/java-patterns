
package util.server.test;

import java.io.IOException;

import test.TestHelper;
import util.server.ClientSocketThread;

public class TestClientSocketThread extends ClientSocketThread {

    private String message = null;
    private String CN = TestClientSocketThread.class.getName();

    @Override
    public void handleIncomingData ()
    {
    	String MN = "handleIncomingData()";
        try
        {
            message = inputBuffer.readLine();
            if (message != null && message.startsWith("REQUEST"))
            {                
                outputWriter.write("RESPONSE\n");
                outputWriter.flush();
            }
            else
            {
            	String errMsg = "ERROR: received invalid message: " + message;
            	TestHelper.failTestWithErrorMsg(CN, MN, errMsg); 
            }
        }
        catch (IOException e)
        {        	
        	String errMsg = "ERROR: Could NOT read message: " + e.toString();
        	TestHelper.failTestWithErrorMsg(CN, MN, errMsg);            
        }
    }
}
