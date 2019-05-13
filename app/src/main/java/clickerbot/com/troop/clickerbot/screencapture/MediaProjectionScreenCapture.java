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

public class MediaProjectionScreenCapture implements ImageReader.OnImageAvailableListener {
    private final String TAG = MediaProjectionScreenCapture.class.getSimpleName();
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionManager mMediaProjectionManager;
    private TextureView surfaceView;
    private static Intent mediaProjectionResult;
    private static int mResultCode;
    private int mScreenDensity;
    private Bitmap inputbmp;
    private ScreenCaptureCallBack screenCaptureCallBack;
    private long frames;
    private ImageReader imageReader;
    private HandlerThread mBackgroundThread;
    protected Handler mBackgroundHandler;

    public MediaProjectionScreenCapture(Context context,Intent mediaProjectionResult, int mResultCode, TextureView surfaceView, int mScreenDensity)
    {
        mMediaProjectionManager = (MediaProjectionManager)
                context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        this.mediaProjectionResult = mediaProjectionResult;
        this.mResultCode = mResultCode;
        this.surfaceView = surfaceView;
        inputbmp = Bitmap.createBitmap(480,800,Bitmap.Config.ARGB_8888);
        imageReader = ImageReader.newInstance(480,800,PixelFormat.RGBA_8888,5);
        imageReader.setOnImageAvailableListener(this,mBackgroundHandler);

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


    public void setScreenCaptureCallBack(ScreenCaptureCallBack captureCallBack)
    {
        this.screenCaptureCallBack = captureCallBack;
    }


    public void start()
    {
        //surfaceView.getSurfaceTexture().setDefaultBufferSize(480,800);



        startScreenCapture();
        //surfaceView.setVisibility(View.GONE);
    }

    public void stop()
    {
        stopScreenCapture();
    }

    public void close()
    {
        tearDownMediaProjection();
        imageReader.close();
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

    private Object bitmapLOCK =new Object();
    public Bitmap getBitmap()
    {
        /*synchronized (bitmapLOCK) {
            inputbmp = surfaceView.getBitmap();
        }*/
        Log.v(TAG,"getBitmap w:" + inputbmp.getWidth() +" h:" + inputbmp.getHeight());
        return inputbmp;
    }

    public void waitForNextFrame(int framesToWait) throws InterruptedException {
        captureframe = lastFrame;
        synchronized (bitmapLOCK) {
            if (captureframe+framesToWait >= lastFrame)
                bitmapLOCK.wait();
        }
    }

    long lastFrame;
    long captureframe;
    public int getColor(Point p)
    {
        int color =0;
        //captureframe = lastFrame;
        synchronized (bitmapLOCK) {
           /* if (captureframe+1 > lastFrame) {
                try {
                    bitmapLOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            /*Log.v(TAG,"getColor w:" + inputbmp.getWidth() +" h:" + inputbmp.getHeight());*/
            if (inputbmp != null && inputbmp.getWidth() > 0 && inputbmp.getHeight() > 0)
                color = inputbmp.getPixel(p.x, p.y);
            //if this case happen something went wrong while copy the buffer to the bitmap
            //or it got never filled
            if (color == 0)
            {
                Log.d(TAG ,"Requested COLOR = 0 wait()");
                try {
                    bitmapLOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                color = inputbmp.getPixel(p.x, p.y);
            }

        }
        return color;
    }

    @Override
    public void onImageAvailable(ImageReader reader) {
        frames++;
        Image img = reader.acquireLatestImage();
        if (true) {
            if (reader != null) {

                if (img != null) {
                    ByteBuffer byteBuffer = img.getPlanes()[0].getBuffer();
                    synchronized (bitmapLOCK) {
                        inputbmp.copyPixelsFromBuffer(byteBuffer);
                        lastFrame++;
                        bitmapLOCK.notify();
                    }

                    /*if (screenCaptureCallBack != null)
                        screenCaptureCallBack.onScreenCapture();*/
                }
            }
            frames = 0;
        }
        if (img != null)
            img.close();
    }
}
