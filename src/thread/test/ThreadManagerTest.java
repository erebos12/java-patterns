

package thread.test;


import java.util.Calendar;

import org.junit.Test;

import thread.LoopingThread;
import thread.ThreadManager;

import static org.junit.Assert.*;

public class ThreadManagerTest {

    public class NewLoopingThread extends LoopingThread {

        private long taskWaitTime = 0;
        public String state = null;

        public NewLoopingThread (long taskWaitTime)
        {
            super("anyName", Thread.currentThread());
            this.taskWaitTime = taskWaitTime;
            this.start();
        }

        public void run ()
        {
            while (isThreadIsActive())
            {
                state = "WAITING";
                try
                {
                    if (getTask() != null)
                    {
                        state = "RUNNING";
                        Thread.sleep(taskWaitTime);
                        resetTask();
                    }
                }
                catch (InterruptedException e)
                {
                    fail("");
                }
            }
            state = "DONE";
        }

        @Override
        public void releaseResources ()
        {
            // TODO Auto-generated method stub
        }
    }

    public class RunnableTask implements Runnable {

        private long taskWaitTime = 0;
        public String state = null;
        
        public RunnableTask (long taskWaitTime, String taskName)
        {
            this.taskWaitTime = taskWaitTime;
            state = "WAITING";
        }

        public void run ()
        {
            // this could be an asynchronous task e.g. DB-Request
            // or whatever
            // System.out.println(taskName + ": State RUNNING: " +
            // getCurrentTime());
            state = "RUNNING";
            sleep(taskWaitTime);
            state = "DONE";
            // System.out.println(taskName + ": State DONE: " +
            // getCurrentTime());
        }
    }

    @SuppressWarnings("static-access")
    private void sleep (long milsec)
    {
        try
        {
            Thread.currentThread().sleep(milsec);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private boolean pollForChangeState (RunnableTask task,
                                        String nextSateToChange,
                                        int maxWaitTime)
    {
        int milsec = 0;         
        while (!task.state.equalsIgnoreCase(nextSateToChange))
        {
            sleep(10);
            milsec += 10;
            if (milsec == maxWaitTime)
            {
                return false;
            }
        }
        /*System.out.println("Changed state to " + nextSateToChange + " in "
            + milsec + " msec");*/
        return true;
    }
    private boolean pollForChangeState (NewLoopingThread thread,
                                        String nextSateToChange,
                                        int maxWaitTime)
    {
        int milsec = 0;        
        while (!thread.state.equalsIgnoreCase(nextSateToChange))
        {
            sleep(10);
            milsec += 10;
            if (milsec == maxWaitTime)
            {
                return false;
            }
        }
        /*System.out.println("Changed state to " + nextSateToChange + " in "
            + milsec + " msec");*/
        return true;
    }

    public static String getCurrentTime ()
    {
        int hour = Calendar.getInstance().get(Calendar.HOUR);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        int milsec = Calendar.getInstance().get(Calendar.MILLISECOND);
        String timeString = hour + ":" + minute + ":" + second + "." + milsec;
        return timeString;
    }
    
    @Test
    public void testGenericThreadPool ()
    {
        ThreadManager threadManager = new ThreadManager();
        int threadPoolSize = 2;
        threadManager.constructGenericThreadPool(threadPoolSize,
                                                 Thread.currentThread());
        int tps = threadManager.getThreadPoolSize();
        assertEquals(threadPoolSize, tps);
        // free threads are active/waiting but not running a task currently
        int numberOfFreeThreads = threadManager.getNumOfFreeThreads();
        assertEquals(threadPoolSize, numberOfFreeThreads);
        RunnableTask task1 = new RunnableTask(100, "task1");
        assertEquals("WAITING", task1.state);
        RunnableTask task2 = new RunnableTask(100, "task2");
        assertEquals("WAITING", task2.state);

        // task will be executed asynchronously in a separate thread
        threadManager.runTask(task1);
        boolean changedToRunning1 = pollForChangeState(task1, "RUNNING", 200);
        assertEquals(true, changedToRunning1);
        numberOfFreeThreads = threadManager.getNumOfFreeThreads();
        assertEquals(1, numberOfFreeThreads); // one thread is busy
        // task will be executed asynchronously in a separate thread
        threadManager.runTask(task2);
        numberOfFreeThreads = threadManager.getNumOfFreeThreads();
        assertEquals(0, numberOfFreeThreads); // both threads are busy        
        boolean changedToRunning2 = pollForChangeState(task2, "RUNNING", 200);
        assertEquals(true, changedToRunning2);        
        // waiting for all tasks done      
        boolean changedToDone1 = pollForChangeState(task1, "DONE", 200);
        assertEquals(true, changedToDone1);
        boolean changedToDone2 = pollForChangeState(task2, "DONE", 200);
        assertEquals(true, changedToDone2);          
        threadManager.shutdownAllThreads();
        int numOfInactiveThreads = threadManager.getNumOfInactiveThreads();
        sleep(100);
        assertEquals(2, numOfInactiveThreads);
    }

    @Test
    public void testLoopingThreadPool ()
    {
        ThreadManager threadManager = new ThreadManager();
        NewLoopingThread newThread1 = new NewLoopingThread(100);
        threadManager.addThread(newThread1);
        sleep(100);
        assertEquals("WAITING", newThread1.state);
        NewLoopingThread newThread2 = new NewLoopingThread(100);
        threadManager.addThread(newThread2);
        sleep(100);
        assertEquals("WAITING", newThread2.state);
        int numberOfFreeThreads = threadManager.getNumOfFreeThreads();
        assertEquals(2, numberOfFreeThreads); // both threads are free
        threadManager.runTask(newThread1);
        boolean changedToRunning1 = pollForChangeState(newThread1, "RUNNING", 400);
        assertEquals(true, changedToRunning1);                                
        threadManager.shutdownThread(newThread1.getId());
        numberOfFreeThreads = threadManager.getNumOfFreeThreads();        
        boolean changedToDone = pollForChangeState(newThread1, "DONE", 400);
        assertEquals(true, changedToDone);        
        assertEquals(1, numberOfFreeThreads);
        threadManager.shutdownAllThreads();        
        boolean changedToDone2 = pollForChangeState(newThread2, "DONE", 400);
        assertEquals(true, changedToDone2);        
    }

    @Test
    public void testGenericThreadPoolOutOfThreads ()
    {
        ThreadManager threadManager = new ThreadManager();
        int threadPoolSize = 1;
        threadManager.constructGenericThreadPool(threadPoolSize,
                                                 Thread.currentThread());
        int tps = threadManager.getThreadPoolSize();
        assertEquals(threadPoolSize, tps);
        int numberOfFreeThreads = threadManager.getNumOfFreeThreads();
        assertEquals(threadPoolSize, numberOfFreeThreads);
        RunnableTask task1 = new RunnableTask(200, "task1");
        RunnableTask task2 = new RunnableTask(100, "task2");
        threadManager.runTask(task1);        
        boolean changedToRunning1 = pollForChangeState(task1, "RUNNING", 200);
        assertEquals(true, changedToRunning1);
        threadManager.runTask(task2);
        // threadManager is out of threads, so
        // task2 have to wait for task1 ending
        assertEquals("WAITING", task2.state);        
        numberOfFreeThreads = threadManager.getNumOfFreeThreads();
        assertEquals(0, numberOfFreeThreads);
        boolean changedToRunning2 = pollForChangeState(task2, "RUNNING", 400);
        assertEquals(true, changedToRunning2);        
        boolean changedToDone2 = pollForChangeState(task2, "DONE", 200);
        assertEquals(true, changedToDone2);        
    }
}
