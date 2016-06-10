package util.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import util.logging.Logger;

public abstract class ClientSocketThread implements Runnable {

	protected InputStream inputStream = null;
	protected OutputStream outputStream = null;	
	protected BufferedReader inputBuffer = null;
	protected PrintWriter outputWriter = null;
	protected Socket socket = null;
	private String className = ClientSocketThread.class.getName();

	public ClientSocketThread() {	    
		logMessage("constructor", Logger.LogLevel.INFO, "ClientSocketThread constructed.");
	}

	private void logMessage(String method, Logger.LogLevel logLevel, String msg)
	{
		Logger.ctx.log(className, method, logLevel, msg);		
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			inputBuffer = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));						
			logMessage("run()", Logger.LogLevel.INFO, "Receive incoming data...");
			outputWriter = new PrintWriter(outputStream);
			handleIncomingData();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (inputBuffer != null) {
				try {
					inputBuffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputWriter != null)
				outputWriter.close();
		}

	}

	public abstract void handleIncomingData();
}
