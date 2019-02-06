package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import clickerbot.com.troop.clickerbot.IBot;
import clickerbot.com.troop.clickerbot.MediaProjectionScreenCapture;
import clickerbot.com.troop.clickerbot.ScreenCaptureCallBack;
import clickerbot.com.troop.clickerbot.executer.Executer;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;

public abstract class AbstractBot implements IBot ,ScreenCaptureCallBack
{
    private final static String TAG = AbstractBot.class.getSimpleName();
    private volatile boolean doWork = false;

    private long threadstarttime;
    private long tickCounter = 0;
    private long lastTick = 0;
    private volatile boolean isRunning = false;
    /*private ScreenCapture screenCapture;*/
    protected final BotSettings botSettings;
    protected MediaProjectionScreenCapture mediaProjectionScreenCapture;
    private Context context;


    private Executer executer;
    private Thread baseThread;

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

    @Override
    public void clearExecuterQueue() {
        executer.clear();
    }

    @Override
    public boolean getIsRunning()
    {
        return isRunning;
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
            isRunning = true;
            while (doWork)
            {
                tick();
                try {
                    Thread.sleep(botSettings.mainLooperSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;
        }
        );
        baseThread.start();
    }

    @Override
    public void stop() {
        this.doWork = false;
        executer.stop();
        baseThread.interrupt();
        //screenCapture.stop();
        mediaProjectionScreenCapture.stop();
        try {
            Thread.sleep(botSettings.mainLooperSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executer.clear();
    }

    public void resetTickCounter()
    {
        tickCounter = 0;
    }

    private void tick()
    {
        lastTick = System.currentTimeMillis();
        tickCounter++;

        onTick(tickCounter);
        long sleep = 100 - (System.currentTimeMillis() - lastTick);
        if (sleep > 0) {
            //Log.d(TAG, "do tick sleep " + sleep);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    abstract void onTick(long tickCounter);


    /*public Bitmap getAreaFromScreen(Rect rect)
    {
        int width = rect.right -rect.left;
        int height = rect.bottom -rect.top;
        Log.d(TAG,"getAreaFromScreen " + width +" " +height);
        Bitmap areaCut = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < width;x++)
        {
            for (int y= 0; y < height;y++)
            {
                int color = screenDump.getPixel(rect.left +x, rect.top+y);
                if (Color.red(color) < 50 && Color.green(color) < 50 && Color.blue(color) < 50)
                    areaCut.setPixel(x,y,Color.BLACK);
                else
                    areaCut.setPixel(x,y,Color.WHITE);
            }
        }

        Rect desRect = new Rect(0, 0, rect.width(), rect.height());

        return areaCut;
    }*/


}
