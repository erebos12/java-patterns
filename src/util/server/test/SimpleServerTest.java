
package util.server.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.TestCase;
import test.TestHelper;
import util.server.SimpleServer;

public class SimpleServerTest extends TestCase {

    private String CN = SimpleServerTest.class.getSimpleName();
    	
    public void testSimpleServerRun () throws Exception
    {
        String MN = "testSimpleServerRun";
        SimpleServer server = null;       
        int port = TestHelper.randInt(TestHelper.MIN_PORT, TestHelper.MAX_PORT);
        int threadPoolSize = 20;
        try
        {
            server = TestHelper.initSimpleServer(CN, MN, port, threadPoolSize, TestClientSocketThread.class.getName());
            Socket clientSocket = null;
            String messageToSend = null;
            for (int i = 0; i < 3; i++)
            {
                clientSocket = new Socket("localhost", port);
                clientSocket.setReuseAddress(true);
                assertTrue(clientSocket.isConnected());
                OutputStream outputStream = clientSocket.getOutputStream();
                messageToSend = "REQUEST_" + i + "\n";
                outputStream.write(messageToSend.getBytes());
                outputStream.flush();// sending message to SimpleServer
                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader inputBuffer = new BufferedReader(
                                                                new InputStreamReader(
                                                                                      inputStream));
                String response = inputBuffer.readLine();// reading answer
                                                         // from
                                                         // SimpleServer
                String expectedResponse = "RESPONSE";
                if (response == null)
                {
                    TestHelper.failTestWithErrorMsg(CN, MN,
                                                    "Didn't receive any response from SimpleServer!");
                }
                if (!response.equalsIgnoreCase(expectedResponse))
                {
                    String errMsg = "Expected response: " + expectedResponse
                        + " but received " + response;
                    TestHelper.failTestWithErrorMsg(CN, MN, errMsg);
                }
                clientSocket = null;
            }

        }
        catch (UnknownHostException e)
        {
            TestHelper.failTestWithErrorMsg(CN, MN,
                                            TestHelper.exceptionStackTraceToString(e));
        }
        catch (IOException e)
        {
            TestHelper.failTestWithErrorMsg(CN, MN,
                                            TestHelper.exceptionStackTraceToString(e));
        }
        finally
        {
        	if (server != null)
			{
				server.stop();
			}
        }
        if (server != null && !server.isStopped())
        {
            TestHelper.failTestWithErrorMsg(CN, MN,
                                            "SimpleServer must be inactive/stopped here!");
        }
    }

    @SuppressWarnings("static-access")
    public static void sleep (long milsec)
    {
        try
        {
            Thread.currentThread().sleep(milsec);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testInvalidHandlerClass ()
    {
        int port = 4444;
        int threadPoolSize = 20;
        IllegalArgumentException ex = null;
        try
        {
            SimpleServer server = new SimpleServer(port, threadPoolSize, "");
            server.start();
        }
        catch (IllegalArgumentException e)
        {
            ex = e;
        }
        catch (Exception e)
        {
            fail("Exception in init SimpleServer: " + e.toString());
        }
        assertTrue(ex != null);

        try
        {
            SimpleServer server = new SimpleServer(port, threadPoolSize, null);
            server.start();
        }
        catch (IllegalArgumentException e)
        {
            ex = e;
        }
        catch (Exception e)
        {
            fail("Exception in init SimpleServer: " + e.toString());
        }
        assertTrue(ex != null);
    }

    public void testInvalidPort ()
    {
        int invalidPort = 0;        
        IllegalArgumentException ex = null;
        try
        {
            SimpleServer server = new SimpleServer(
                                                   invalidPort,
                                                   0,
                                                   TestClientSocketThread.class.getName());
            server.start();
        }
        catch (IllegalArgumentException e)
        {
            ex = e;
        }
        catch (Exception e)
        {
            fail("Exception in init SimpleServer: " + e.toString());
        }
        assertTrue(ex != null);
    }

    public void testInvalidThreadPoolSize ()
    {
        int port = 7777;
        int invalidThreadPoolSize = 0;
        IllegalArgumentException ex = null;
        try
        {
            SimpleServer server = new SimpleServer(
                                                   port,
                                                   invalidThreadPoolSize,
                                                   TestClientSocketThread.class.getName());
            server.start();
        }
        catch (IllegalArgumentException e)
        {
            ex = e;
        }
        catch (Exception e1)
        {
            fail("Exception in init SimpleServer: " + e1.toString());
        }
        assertTrue(ex != null);
    }

}
