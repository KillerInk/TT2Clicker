package clickerbot.com.troop.clickerbot;

import android.content.Context;
import android.util.Log;

import clickerbot.com.troop.clickerbot.executer.Executer;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.screencapture.MediaProjectionScreenCapture;
import clickerbot.com.troop.clickerbot.screencapture.ScreenCaptureCallBack;

public class ThreadController implements IBot ,ScreenCaptureCallBack
{
    private final static String TAG = ThreadController.class.getSimpleName();
    private volatile boolean doWork = false;

    private long threadstarttime;
    private long tickCounter = 0;
    private long lastTick = 0;
    private volatile boolean isBaseThreadRunning = false;
    private volatile boolean isScreenParserRunning = false;
    protected MediaProjectionScreenCapture mediaProjectionScreenCapture;
    private Context context;


    //Execute the tasks one by one
    private Executer executer;
    //handels the main logic and add tasks to the executer pool
    private Thread baseThread;
    //handel screenParsing
    private Thread screenParserThread;

    @Override
    public void onScreenCapture() {

    }




    public interface TickInterface
    {
        void onTick(long tickCounter);
        void onScreenParserTick();
    }


    private TickInterface tickInterface;




    public ThreadController(Context context, MediaProjectionScreenCapture mediaProjectionScreenCapture, TickInterface tickInterface){
        this.mediaProjectionScreenCapture = mediaProjectionScreenCapture;
        executer = new Executer();
        this.context = context;
        this.tickInterface = tickInterface;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void execute(ExecuterTask runnable) {
        executer.putRunnable(runnable);
    }

    @Override
    public void putFirstAndExecute(ExecuterTask runnable) {
        executer.putFirstAndExecute(runnable);
    }

    public void putAtPos(ExecuterTask task, int pos)
    {
        executer.putAt(task,pos);
    }

    public boolean containsT(ExecuterTask task)
    {
        return executer.containsTaks(task);
    }

    public String getActiveTaskName()
    {
        return executer.getActiveTask();
    }

    @Override
    public void clearExecuterQueue() {
        executer.clear();
    }

    @Override
    public boolean getIsRunning()
    {
        return isBaseThreadRunning;
    }

    @Override
    public MediaProjectionScreenCapture getScreeCapture() {
        return mediaProjectionScreenCapture;
    }

    @Override
    public void start() {
        doWork = true;
        tickCounter =0;
        Log.d(TAG, "start");
        mediaProjectionScreenCapture.start();
        executer.start();
        baseThread = new Thread(()->{
            threadstarttime = System.currentTimeMillis();
            isBaseThreadRunning = true;
            while (doWork && !Thread.currentThread().isInterrupted())
            {
                tickCounter++;
                if (tickInterface != null)
                    tickInterface.onTick(tickCounter);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG,"stopped baseThread");
            isBaseThreadRunning = false;
        }
        );
        baseThread.start();

        screenParserThread = new Thread(() -> {
            isScreenParserRunning = true;
            while (doWork && !Thread.currentThread().isInterrupted())
            {
                if (tickInterface != null)
                    tickInterface.onScreenParserTick();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isScreenParserRunning = false;
            Log.d(TAG,"stopped screenParserThread");
        });
        screenParserThread.start();
    }

    @Override
    public void stop() {
        this.doWork = false;
        executer.stop();
        baseThread.interrupt();
        screenParserThread.interrupt();
        Log.d(TAG,"Stopping");

        while (isBaseThreadRunning || isScreenParserRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        baseThread = null;
        screenParserThread = null;
        Log.d(TAG,"Stopped threads, stopping mediaprojection");
        mediaProjectionScreenCapture.stop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Stopped mediaprojection");

        executer.clear();
        resetTickCounter();
    }

    public void resetTickCounter()
    {
        tickCounter = 0;
    }

}
