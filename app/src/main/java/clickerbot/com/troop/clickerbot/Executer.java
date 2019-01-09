package clickerbot.com.troop.clickerbot;

import android.util.Log;

import java.util.LinkedList;


public class Executer {

    private String TAG = Executer.class.getSimpleName();
    private final int KEEP_ALIVE_TIME = 500;
    private boolean doWork;
    LinkedList<ExecuterTask> runnableLinkedList;
    private Thread exeThread;


    public Executer() {
        this.runnableLinkedList = new LinkedList<>();
    }

    private ExecuterTask run;

    public void start()
    {
        doWork = true;

        exeThread = new Thread(()->{
            while (doWork)
            {
                     run = runnableLinkedList.pollFirst();
                     if (run != null) {
                         Log.i(TAG, "run:  " + run.getClass().getSimpleName());
                         run.run();
                     }
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

        runnableLinkedList.clear();
        Log.d(TAG, "Queue size:" + runnableLinkedList.size());
    }

    public void putRunnable(ExecuterTask runnable)
    {
        if (!runnableLinkedList.contains(runnable) && run != runnable) {
            runnableLinkedList.add(runnable);
            Log.v(TAG,"putRunnable "+runnable.getClass().getSimpleName());
        }
        else Log.v(TAG, "Runnable already added " +runnable.getClass().getSimpleName());
    }

    public void putFirstAndExecute(ExecuterTask executerTask)
    {

        try {
            if (run != null)
                run.cancelTask = true;
            runnableLinkedList.addFirst(executerTask);
        }
        catch (NullPointerException ex)
        {
        }

    }

    public void clear()
    {
        runnableLinkedList.clear();
    }
}
