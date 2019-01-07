package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Bitmap;
import android.util.Log;

import clickerbot.com.troop.clickerbot.Executer;
import clickerbot.com.troop.clickerbot.ExecuterTask;
import clickerbot.com.troop.clickerbot.IBot;
import clickerbot.com.troop.clickerbot.ScreenCapture;

public abstract class AbstractBot implements IBot ,ScreenCapture.ScreenCaptureCallBack
{
    private final static String TAG = AbstractBot.class.getSimpleName();
    private volatile boolean doWork = false;

    private long threadstarttime;
    private long tickCounter = 0;
    private long lastTick = 0;
    private volatile boolean isRunning = false;
    private ScreenCapture screenCapture;
    protected final BotSettings botSettings;


    private Executer executer;

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


    public AbstractBot(BotSettings botSettings){
        this.botSettings = botSettings;
        screenCapture = new ScreenCapture(this,botSettings);
        executer = new Executer();
    }

    @Override
    public void execute(ExecuterTask runnable) {
        executer.putRunnable(runnable);
    }

    @Override
    public void putFirstAndExecute(ExecuterTask runnable) {
        executer.putFirstAndExecute(runnable);
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
    public ScreenCapture getScreeCapture() {
        return screenCapture;
    }

    @Override
    public void start() {
        doWork = true;
        tickCounter =0;
        Log.d(TAG, "start");
        executer.start();
        new Thread(()->{
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
        ).start();
        screenCapture.start();

    }

    @Override
    public void stop() {
        this.doWork = false;
        executer.stop();
        screenCapture.stop();
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
