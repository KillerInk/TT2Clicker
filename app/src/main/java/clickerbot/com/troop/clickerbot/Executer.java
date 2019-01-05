package clickerbot.com.troop.clickerbot;

import android.util.Log;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Executer {

    private String TAG = Executer.class.getSimpleName();
    private final int KEEP_ALIVE_TIME = 500;
    private boolean doWork;
    LinkedList<Runnable> runnableLinkedList;
    private Thread exeThread;


    public Executer() {
        this.runnableLinkedList = new LinkedList<>();
    }

    public void start()
    {
        doWork = true;

        exeThread = new Thread(()->{
            Runnable run;
            while (doWork)
            {
                     run = runnableLinkedList.pollFirst();
                     if (run != null)
                        run.run();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        exeThread.start();
    }

    public void stop()
    {
        Log.d(TAG, "stop");
        doWork =false;
        exeThread.interrupt();
        try {
            exeThread.stop();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        runnableLinkedList.clear();
        Log.d(TAG, "Queue size:" + runnableLinkedList.size());
    }

    public void putRunnable(Runnable runnable)
    {
        if (!runnableLinkedList.contains(runnable)) {
            runnableLinkedList.add(runnable);
            Log.d(TAG,"putRunnable"+runnable.getClass().getSimpleName());
        }
        else Log.d(TAG, "Runnable already added");
    }

    public void clear()
    {
        runnableLinkedList.clear();
    }
}
