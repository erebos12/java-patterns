/*
 * FILE NAME: DaemonSuperClass.java
 *
 * DESCRIPTION:
 *
 *
 *
 * COPYRIGHT:
 *   (C) Copyright International Business Machines Corporation 1997, 2014.
 *   Licensed Material - Property of IBM - All Rights Reserved.
 *
 * CLASSIFICATION:
 *   IBM Confidential
 *
 * COMPONENT:
 *   IBM ADMIRA
 *
 * CHANGES:
 *
 *  $XX Mar 31, 2014 initial version (michael).
 */

package daemon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

import string.StringUtils;
import util.logging.Logger;


/**
 * Base class which provides functionalities of every common AREMA daemon.
 * init log file;  creates run file and writes timestamp to it;
 * implements loop which is based on runFile if exists; start the JAVA process as a single thread to run in background;
 * implements the base algorithm for AREMA daemon (start, runLoop, stop)
 * 
 * The child class just implements abstract methods: initialize(), start() and stop(). 
 *
 */
public abstract class BaseDaemon implements Runnable {

    private String CN = null;
    private int runFileIntervalInMsec = 500;
    private Thread myThread = null;
    private File runFile = null;
    
	public BaseDaemon(String className, String logFileNamePrefix, String runFileName)
    {
        String MN = "BaseDaemon(): ";
        this.CN = className;

        String msg = "Successfully configured logging facilities...";
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, msg);             
        
        runFile = createRunFile(runFileName);
        if (runFile == null)
        {
        	msg = "Error while creating runFile!";
        	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, msg);              
            System.exit(1);
        }
        myThread = new Thread(this);
    }
    
    public void setRunFileIntervalInMsec(int runFileIntervalInMsec)
    {
        this.runFileIntervalInMsec = runFileIntervalInMsec;
    }
    
    public void startMeAsDaemon()
    {
        myThread.start();
    }

    public abstract void initialize ();

    public abstract void start ();

    public abstract void stop ();

    public final void run ()
    {
        String MN = "run()";
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "initialize...");
        initialize();

        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "start...");
        start();
       
        runDaemonLoop();
        
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "stop...");
        stop();
    }

    private void runDaemonLoop ()
    {
        String MN = "runDaemonLoop(): ";
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, "enter...");       
        while (runFile.exists())
        {          
            writeTimestamp(new Date());         
            try
            {
                Thread.sleep(runFileIntervalInMsec);
            }
            catch (InterruptedException e)
            {
            	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, e.toString());
            }
        }
    }

    private File createRunFile (String runFileName)
    {     
        String MN = "createRunFile(): ";
        String msg = "creating runfile with name: " + runFileName;
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, msg);        
        File runFile = new File(runFileName);
        try
        {
            runFile.createNewFile();
        }
        catch (IOException e)
        {
            msg = "Could not create runfile: " + e.toString();
            Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, msg);
            return null;
        }
        msg = "Created runfile " + runFile.getName() + " successfully.";
        Logger.ctx.log(CN, MN, Logger.LogLevel.INFO, msg);
        return runFile;
    }

    private void writeTimestamp (Date timestamp)
    {
        String MN = "writeTimestamp(): ";
        String sTimestamp = DateFormat.getDateTimeInstance().format(timestamp);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(runFile)))        
        {            
            bufferedWriter.write(sTimestamp);
        }
        catch (IOException e)
        {
        	String msg = "Error while writing timestamp to runFile: " + e.toString();
        	Logger.ctx.log(CN, MN, Logger.LogLevel.ERROR, msg);                   
        }        
    }
}
