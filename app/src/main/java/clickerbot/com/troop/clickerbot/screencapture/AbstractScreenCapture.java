package clickerbot.com.troop.clickerbot.screencapture;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

public abstract class AbstractScreenCapture implements ScreenCaptureInterface {
    private final String TAG = AbstractScreenCapture.class.getSimpleName();

    private HandlerThread mBackgroundThread;
    protected Handler mBackgroundHandler;

    @Override
    public void create()
    {
        Log.d(TAG,"startBackgroundThread" );
        mBackgroundThread = new HandlerThread("");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    @Override
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
}
