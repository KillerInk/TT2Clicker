package clickerbot.com.troop.clickerbot.screencapture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

import clickerbot.com.troop.clickerbot.RootShell;

public class ShellScreenCapture extends AbstractScreenCapture {

    private RootShell rootShell;
    private boolean doWork;
    private final String TAG = ShellScreenCapture.class.getSimpleName();

    private LinkedBlockingQueue<Bitmap> blockingQueue;

    public ShellScreenCapture()
    {
        rootShell = new RootShell(99);
        blockingQueue = new LinkedBlockingQueue<>(5);
    }

    @Override
    public Bitmap getBitmapFromQueue(Bitmap map) {
            Bitmap buffer = blockingQueue.poll();
            if (buffer != null)
                map = buffer;
            return map;
    }

    private void captureScreen()
    {
        try {
            if (blockingQueue.remainingCapacity() ==1)
                blockingQueue.poll();
            rootShell.captureScreen();
            blockingQueue.put(BitmapFactory.decodeStream(rootShell.getInputStream()));
            Log.d(TAG, "Add buffer");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {
        rootShell.Close();
    }

    public void start()
    {
        if (doWork)
            return;
        doWork =true;
        rootShell.startProcess();
        new Thread(()->{
            while (doWork)
            {
                try {
                    captureScreen();
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop()
    {
        doWork =false;
        rootShell.stopProcess();
    }


}
