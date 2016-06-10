
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
