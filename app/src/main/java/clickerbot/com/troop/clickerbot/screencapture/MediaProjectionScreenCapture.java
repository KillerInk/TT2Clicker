package clickerbot.com.troop.clickerbot.screencapture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.TextureView;

import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;

public class MediaProjectionScreenCapture implements ImageReader.OnImageAvailableListener {

    private final static int MAX_IMAGES = 8;

    private final String TAG = MediaProjectionScreenCapture.class.getSimpleName();
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionManager mMediaProjectionManager;
    private TextureView surfaceView;
    private static Intent mediaProjectionResult;
    private static int mResultCode;
    private int mScreenDensity;
    private Bitmap inputbmp;
    private long frames;
    private ImageReader imageReader;
    private HandlerThread mBackgroundThread;
    protected Handler mBackgroundHandler;
    private LinkedBlockingQueue<Image> blockingQueue;

    public MediaProjectionScreenCapture(Context context,Intent mediaProjectionResult, int mResultCode, TextureView surfaceView, int mScreenDensity)
    {
        mMediaProjectionManager = (MediaProjectionManager)
                context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        this.mediaProjectionResult = mediaProjectionResult;
        this.mResultCode = mResultCode;
        this.surfaceView = surfaceView;
        inputbmp = Bitmap.createBitmap(480,800,Bitmap.Config.ARGB_8888);
        imageReader = ImageReader.newInstance(480,800,PixelFormat.RGBA_8888,MAX_IMAGES);
        imageReader.setOnImageAvailableListener(this,mBackgroundHandler);
        blockingQueue = new LinkedBlockingQueue<>(MAX_IMAGES);
        this.mScreenDensity =mScreenDensity;
        create();

    }

    public void create()
    {
        Log.d(TAG,"startBackgroundThread" );
        mBackgroundThread = new HandlerThread("");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    public void destroy()
    {
        Log.d(TAG,"stopBackgroundThread");
        if(mBackgroundThread == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBackgroundThread.quitSafely();
        }
        else
            mBackgroundThread.quit();

        mBackgroundThread = null;
        mBackgroundHandler = null;
    }

    public void start()
    {
        startScreenCapture();
    }

    public void stop()
    {
        stopScreenCapture();
    }

    public void close()
    {
        tearDownMediaProjection();
        imageReader.close();
        Image img;
        while ((img = blockingQueue.poll()) != null)
            img.close();
        blockingQueue.clear();
        destroy();
    }

    private void setUpMediaProjection() {
        mMediaProjection = mMediaProjectionManager.getMediaProjection(mResultCode, mediaProjectionResult);
    }

    private void tearDownMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    private void startScreenCapture() {

        if (mMediaProjection != null) {
            setUpVirtualDisplay();
        } else if (mResultCode != 0 && mediaProjectionResult != null) {
            setUpMediaProjection();
            setUpVirtualDisplay();
        }
    }

    private void setUpVirtualDisplay() {
        Log.i(TAG, "Setting up a VirtualDisplay: " +
                480 + "x" + 800 +
                " (" + mScreenDensity + ")");
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("ScreenCapture",
                imageReader.getWidth(), imageReader.getHeight(), mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                imageReader.getSurface(), null, null);
    }

    private void stopScreenCapture() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        mVirtualDisplay = null;
    }

    public Bitmap getBitmap()
    {
        getBitmapFromQueue();
        Log.v(TAG,"getBitmap w:" + inputbmp.getWidth() +" h:" + inputbmp.getHeight());
        return inputbmp;
    }

    public int getColor(Point p)
    {
        int color =0;
        getBitmapFromQueue();
        if (inputbmp != null && inputbmp.getWidth() > 0 && inputbmp.getHeight() > 0)
            color = inputbmp.getPixel(p.x, p.y);
        //if this case happen something went wrong while copy the buffer to the bitmap
        //or it got never filled
        if (color == 0)
        {
            Log.d(TAG ,"Requested COLOR = 0 wait()");
            color = inputbmp.getPixel(p.x, p.y);
        }

        return color;
    }


    public int[] getColorFromOneVerticalLine(int x, int start_y, int end_y)
    {
        int[] arr = null;
        getBitmapFromQueue();
        if (inputbmp != null && inputbmp.getWidth() > 0 && inputbmp.getHeight() > 0){
            arr = new int[(end_y - start_y)];
            int i = 0;
            for (int y = start_y; y < end_y; y++)
            {
                arr[i++] = inputbmp.getPixel(x, y);
            }
        }
        return arr;
    }

    public int[] getColorFromOneHorizontalLine(Bitmap map,int y, int start_x, int end_x)
    {
        int[] arr = null;
        if (map != null && map.getWidth() > 0 && map.getHeight() > 0){
            arr = new int[(end_x - start_x)];
            int i = 0;
            for (int x = start_x; x < end_x; x++)
            {
                arr[i++] = map.getPixel(x, y);
            }
        }
        return arr;
    }

    public int[] getColorFromOneHorizontalLine(int y, int start_x, int end_x)
    {
        return getColorFromOneHorizontalLine(inputbmp,y,start_x,end_x);
    }

    @Override
    public void onImageAvailable(ImageReader reader) {
        Image img;
        if (blockingQueue.remainingCapacity() == 1) {
            img = blockingQueue.poll();
            if (img!=null)
                img.close();
        }
        frames++;
        img = reader.acquireLatestImage();
        if (img != null)
            try {
                blockingQueue.put(img);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private Bitmap getBitmapFromQueue()
    {
        Image img = blockingQueue.poll();
        if (img == null)
            return null;
        ByteBuffer byteBuffer = img.getPlanes()[0].getBuffer();
        inputbmp.copyPixelsFromBuffer(byteBuffer);
        img.close();
        return inputbmp;
    }

    public Bitmap getBitmapFromPos(int x, int y, int width, int height) throws InterruptedException {
        Bitmap retBit = null;
        getBitmapFromQueue();
        if (inputbmp != null && inputbmp.getWidth() > 0 && inputbmp.getHeight() > 0) {
            retBit = Bitmap.createBitmap(inputbmp,x,y,width,height);
        }
        return retBit;
    }
}
