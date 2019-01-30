package clickerbot.com.troop.clickerbot.tt2;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class is used to have the ability to lock and wait in different threads.
 */
public class WaitLock
{
    private static String TAG = WaitLock.class.getSimpleName();
    public static AtomicBoolean errorDetected = new AtomicBoolean();
    public static AtomicBoolean fairyWindowDetected = new AtomicBoolean();
    public static AtomicBoolean sceneTransition = new AtomicBoolean();
    private static AtomicBoolean flashzip = new AtomicBoolean();

    /**
     * manly used to detect failed taps that opened a unwanted window.
     * tells all listner to wait till its false
     * @param lock
     */
    public static void lockError(boolean lock)
    {
        synchronized (errorDetected)
        {
            Log.v(TAG, "errorDetected");
            errorDetected.set(lock);
            if (!lock)
                errorDetected.notifyAll();
        }
    }

    /**
     * applys a lock to all listners to wait till it gets set back to false
     * caused due a fairywindow got open
     * @param lock
     */
    public static void lockFairyWindow(boolean lock)
    {
        synchronized (fairyWindowDetected)
        {
            Log.v(TAG, "fairywindow detected");
            fairyWindowDetected.set(lock);
            if (!lock)
                fairyWindowDetected.notifyAll();
        }
    }

    /**
     * tell all listner to wait while a scene transition happen
     * @param lock
     */
    public static void lockSceneTransition(boolean lock)
    {
        synchronized (sceneTransition)
        {
            Log.v(TAG, "sceneTransition detected");
            sceneTransition.set(lock);
            if (!lock)
                sceneTransition.notifyAll();
        }
    }


    /**
     * tells crazy/random taps to wait to give flash zip a chance to get clicked
     * @param lock
     */
    public static void lockFlashZip(boolean lock)
    {
        synchronized (flashzip)
        {
            Log.v(TAG, "sceneTransition detected");
            flashzip.set(lock);
            if (!lock)
                flashzip.notifyAll();
        }
    }

    public static void waitForFlashZip()
    {
        synchronized (flashzip) {
            if (flashzip.get()) {
                try {
                    flashzip.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * wait for scenetransition, fairywindow or submenu errors
     */
    public static void checkForErrorAndWait()
    {
        synchronized (WaitLock.sceneTransition)
        {
            if (WaitLock.sceneTransition.get()) {
                Log.v(TAG, "wait for sceneTransition");
                try {
                    WaitLock.sceneTransition.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (WaitLock.errorDetected)
        {
            if (WaitLock.errorDetected.get()) {
                Log.v(TAG, "wait for error");
                try {
                    WaitLock.errorDetected.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (WaitLock.fairyWindowDetected)
        {
            if (WaitLock.fairyWindowDetected.get()) {
                Log.v(TAG, "wait for fairywindow");
                try {
                    WaitLock.fairyWindowDetected.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
