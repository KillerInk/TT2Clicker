package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;
import clickerbot.com.troop.clickerbot.OCR;
import clickerbot.com.troop.clickerbot.RootShell;

public abstract class AbstractBot implements IBot
{
    private final static String TAG = AbstractBot.class.getSimpleName();
    private volatile boolean doWork = false;

    private long threadstarttime;
    private long tickCounter = 0;
    private long lastTick = 0;
    private volatile boolean isRunning = false;
    //sleep times
    private final long screenDumpSleepTime = 500;
    private final int tickSleepTime = 100;
    private RootShell dumpScreenShell;
    private Bitmap screenDump;

    @Override
    public boolean getIsRunning()
    {
        return isRunning;
    }

    @Override
    public void start() {
        doWork = true;
        tickCounter =0;
        dumpScreenShell = new RootShell(4,80);
        new Thread(()->{
            threadstarttime = System.currentTimeMillis();
            isRunning = true;
            while (doWork)
            {
                tick();
                try {
                    Thread.sleep(tickSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;
        }
        ).start();

      /* new Thread(()->{
            while (doWork)
            {
                try {
                    dumpScreen();

                    Thread.sleep(screenDumpSleepTime);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    @Override
    public void stop() {
        this.doWork = false;
        try {
            Thread.sleep(tickSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dumpScreenShell.Close();
    }

    private void tick()
    {
        lastTick = System.currentTimeMillis();
        tickCounter++;

        onTick(tickCounter);
        long sleep = 100 - (System.currentTimeMillis() - lastTick);
        if (sleep > 0) {
            Log.d(TAG, "do tick sleep " + sleep);
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    abstract void onTick(long tickCounter);

    @Override
    public synchronized void dumpScreen() throws InterruptedException, IOException {
        dumpScreenShell.captureScreen();
        Thread.sleep(screenDumpSleepTime);
        screenDump = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/screen.png");
        Log.d(TAG,"ScreenDumped");
    }

    @Override
    public synchronized int getColor(Point point)
    {
        Log.d(TAG,"getColor");
        if (screenDump != null)
            return screenDump.getPixel(point.x,point.y);
        else
            return 0;
    }

    public Bitmap getAreaFromScreen(Rect rect)
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
    }


}
