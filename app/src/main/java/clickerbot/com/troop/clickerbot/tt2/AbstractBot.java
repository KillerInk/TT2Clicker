package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import clickerbot.com.troop.clickerbot.IBot;
import clickerbot.com.troop.clickerbot.executer.Executer;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.screencapture.MediaProjectionScreenCapture;
import clickerbot.com.troop.clickerbot.screencapture.ScreenCaptureCallBack;

public abstract class AbstractBot implements IBot ,ScreenCaptureCallBack
{
    private final static String TAG = AbstractBot.class.getSimpleName();
    private volatile boolean doWork = false;

    private long threadstarttime;
    private long tickCounter = 0;
    private long lastTick = 0;
    private volatile boolean isBaseThreadRunning = false;
    private volatile boolean isScreenParserRunning = false;
    protected final BotSettings botSettings;
    protected MediaProjectionScreenCapture mediaProjectionScreenCapture;
    private Context context;


    private Executer executer;
    //handels the main logic and add tasks to the executer pool
    private Thread baseThread;
    //handel screenParsing
    private Thread screenParserThread;

    public interface UpdateUi
    {
        void updatePrestigeTime(String time);
        void updateImage(Bitmap bitmap);
    }

    private UpdateUi updateUi;

    public void setUpdateUiCallBack(UpdateUi updateUiCallBack)
    {
        this.updateUi = updateUiCallBack;
    }

    public void UpdatePrestigeTime(String out)
    {
        if (updateUi != null)
        {
            updateUi.updatePrestigeTime(out);
        }
    }

    public void UpdateImage(Bitmap bitmap)
    {
        if (updateUi != null)
        {
            updateUi.updateImage(bitmap);
        }
    }


    public AbstractBot(Context context,BotSettings botSettings, MediaProjectionScreenCapture mediaProjectionScreenCapture){
        this.botSettings = botSettings;
        this.mediaProjectionScreenCapture = mediaProjectionScreenCapture;
        executer = new Executer();
        this.context = context;
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
                onTick(tickCounter);
                try {
                    Thread.sleep(botSettings.mainLooperSleepTime);
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
                onScreenParserTick();
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
        if (updateUi != null)
            updateUi.updatePrestigeTime("Stopping");
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
            Thread.sleep(botSettings.mainLooperSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Stopped mediaprojection");
        if (updateUi != null)
            updateUi.updatePrestigeTime("Stopped");
        executer.clear();
        resetTickCounter();
    }

    public void resetTickCounter()
    {
        tickCounter = 0;
    }

    abstract void onTick(long tickCounter);
    abstract void onScreenParserTick();
}
