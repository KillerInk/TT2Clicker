package clickerbot.com.troop.clickerbot.executer;

import android.util.Log;

import java.util.LinkedList;


public class Executer {

    private String TAG = Executer.class.getSimpleName();
    private boolean doWork;
    LinkedList<ExecuterTask> runnableLinkedList;
    private Thread exeThread;

    private boolean running;

    public Executer() {
        this.runnableLinkedList = new LinkedList<>();
    }

    private ExecuterTask run;

    public String getActiveTask()
    {
        if (run == null)
            return null;
        return run.getClass().getSimpleName();
    }

    public void start()
    {
        doWork = true;

        exeThread = new Thread(()->{
            running = true;
            while (doWork && !Thread.currentThread().isInterrupted())
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
            running = false;
        });
        exeThread.start();
    }

    public void stop()
    {
        Log.d(TAG, "stop");
        doWork =false;
        exeThread.interrupt();
        if (run != null)
            run.cancelTask = true;
        while (running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        runnableLinkedList.clear();
        Log.d(TAG, "Stopped Queue size:" + runnableLinkedList.size());
    }

    public boolean containsTaks(ExecuterTask runnable)
    {
        return runnableLinkedList.contains(runnable)  || run == runnable;
    }

    public void putRunnable(ExecuterTask runnable)
    {
        if (!runnableLinkedList.contains(runnable) && run != runnable) {
            runnableLinkedList.add(runnable);
            Log.i(TAG,"putRunnable "+runnable.getClass().getSimpleName());
        }
        //else Log.v(TAG, "Runnable already added " +runnable.getClass().getSimpleName());
    }

    public void putFirstAndExecute(ExecuterTask executerTask)
    {
        try {
            if (run == executerTask)
                return;
            if (run != null)
                run.cancelTask = true;
            runnableLinkedList.addFirst(executerTask);
        }
        catch (NullPointerException ex)
        {
        }

    }

    public void putAt(ExecuterTask executerTask, int pos)
    {
        if (runnableLinkedList.contains(executerTask))
            runnableLinkedList.remove(executerTask);
        runnableLinkedList.add(pos, executerTask);
    }

    public void clear()
    {
        runnableLinkedList.clear();
    }
}
