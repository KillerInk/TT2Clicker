package clickerbot.com.troop.clickerbot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.tt2.BotSettings;

public class ScreenCapture {

    public interface ScreenCaptureCallBack{

        void onScreenCapture();
    }

    private String TAG =ScreenCapture.class.getSimpleName();

    private RootShell rootShell;
    private volatile boolean doWork = false;
    private Bitmap screenDumpBmp;

    private Object bitmapLock =new Object();

    private long frameCount;

    private ScreenCaptureCallBack screenCaptureCallBack;
    private BotSettings botSettings;

    public ScreenCapture(ScreenCaptureCallBack screenCaptureCallBack, BotSettings botSettings)
    {
        rootShell = new RootShell(99);
        this.screenCaptureCallBack = screenCaptureCallBack;
        this.botSettings = botSettings;
    }

    public void destroy()
    {
        rootShell.Close();
    }

    public void start()
    {
        if (doWork)
            return;
        doWork =true;
        frameCount= 0;
        rootShell.startProcess();
        new Thread(()->{
            while (doWork)
            {
                try {
                    dumpScreen();
                    Thread.sleep(1);
                    frameCount++;

                    if (screenCaptureCallBack != null)
                        screenCaptureCallBack.onScreenCapture();
                    Thread.sleep(botSettings.captureFrameSleepTime);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stop()
    {
        doWork =false;
        rootShell.stopProcess();
    }


    private void dumpScreen() throws InterruptedException, IOException {

            rootShell.captureScreen();

        synchronized (bitmapLock) {
            screenDumpBmp = BitmapFactory.decodeStream(rootShell.getInputStream());
            bitmapLock.notifyAll();
        }
        Log.v(TAG,"ScreenDumped");
    }

    public Bitmap getScreenDumpBmp()
    {
        return screenDumpBmp;
    }

    public int getColor(Point point)
    {
        int color =0;
        synchronized (bitmapLock) {

            if (screenDumpBmp != null)
                color = screenDumpBmp.getPixel(point.x, point.y);

            Log.v(TAG, "getColor:" + ColorUtils.getColorString(color));
        }
        return color;
    }

    private long lastframe = 0;
    public int getColorFromNextFrame(Point point)
    {
        int color = 0;
        synchronized (bitmapLock) {
            lastframe = frameCount;
            Log.v(TAG, "WaitForFrame");
            try {
                bitmapLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (lastframe +2 > frameCount)
            {
                if (screenDumpBmp != null)
                    color = screenDumpBmp.getPixel(point.x, point.y);
                Log.v(TAG,"getColorFromCurrentFrame() " + lastframe+2 +"/"+ frameCount +" " + ColorUtils.getColorString(color));
                Log.v(TAG, "wait for next frame");

                try {
                    bitmapLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (screenDumpBmp != null)
                color = screenDumpBmp.getPixel(point.x, point.y);
            Log.v(TAG,"getColorFromNextFrame() "+ lastframe+2 +"/"+ + frameCount +" " + ColorUtils.getColorString(color));
        }
        return color;
    }


}
