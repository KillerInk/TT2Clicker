package clickerbot.com.troop.clickerbot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.IOException;

public class ScreenCapture {

    public static boolean debug = false;

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

    public ScreenCapture(ScreenCaptureCallBack screenCaptureCallBack)
    {
        rootShell = new RootShell(99);
        this.screenCaptureCallBack = screenCaptureCallBack;
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
        new Thread(()->{
            while (doWork)
            {
                try {
                    dumpScreen();
                    Thread.sleep(1);
                    frameCount++;

                    if (screenCaptureCallBack != null)
                        screenCaptureCallBack.onScreenCapture();
                    Thread.sleep(10);

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
    }


    private void dumpScreen() throws InterruptedException, IOException {
        synchronized (bitmapLock) {
            rootShell.captureScreen();
            Thread.sleep(600);
            screenDumpBmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screen.png");
            bitmapLock.notifyAll();
        }
        if (debug)
            Log.d(TAG,"ScreenDumped");
    }

    public int getColor(Point point)
    {
        int color =0;
        synchronized (bitmapLock) {
            if (debug)
                Log.d(TAG, "getColor");
            if (screenDumpBmp != null)
                color = screenDumpBmp.getPixel(point.x, point.y);
        }
        return color;
    }

    private long lastframe = 0;
    public int getColorFromNextFrame(Point point)
    {
        int color = 0;
        synchronized (bitmapLock) {
            lastframe = frameCount;
            if (debug)
                Log.d(TAG, "WaitForFrame");
            try {
                bitmapLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (lastframe +2 != frameCount)
            {
                if (debug)
                    Log.d(TAG,"wait for next frame");
                try {
                    bitmapLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (screenDumpBmp != null)
                color = screenDumpBmp.getPixel(point.x, point.y);
            if (debug)
                Log.d(TAG,"getColorFromNextFrame() " + getColorString(color));
        }
        return color;
    }

    public static String getColorString(int color)
    {
        return "color " + color + " r:" +Color.red(color) + " g:"+Color.green(color) + " b:" +Color.blue(color);
    }
}
