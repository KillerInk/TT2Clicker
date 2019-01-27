package clickerbot.com.troop.clickerbot.tt2;

import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class WaitLock
{
    private static String TAG = WaitLock.class.getSimpleName();
    private static AtomicBoolean errorDetected = new AtomicBoolean();
    private static AtomicBoolean fairyWindowDetected = new AtomicBoolean();
    private static AtomicBoolean sceneTransition = new AtomicBoolean();

    public static void lockError(boolean lock)
    {
        if (lock)
        {
            synchronized (errorDetected)
            {
                Log.d(TAG, "errorDetected");
                errorDetected.set(true);
            }
        }
        else
        {
            synchronized (errorDetected)
            {
                errorDetected.set(false);
                errorDetected.notifyAll();
            }
        }
    }

    public static void lockFairyWindow(boolean lock)
    {
        if (lock)
        {
            synchronized (fairyWindowDetected)
            {
                Log.d(TAG, "fairywindow detected");
                fairyWindowDetected.set(true);
            }
        }
        else
        {
            synchronized (fairyWindowDetected)
            {
                fairyWindowDetected.set(false);
                fairyWindowDetected.notifyAll();
            }
        }
    }

    public static void lockSceneTransition(boolean lock)
    {
        if (lock)
        {
            synchronized (sceneTransition)
            {
                Log.d(TAG, "sceneTransition detected");
                sceneTransition.set(true);
            }
        }
        else
        {
            synchronized (sceneTransition)
            {
                sceneTransition.set(false);
                sceneTransition.notifyAll();
            }
        }
    }

    public static void checkForErrorAndWait()
    {
        synchronized (WaitLock.sceneTransition)
        {
            if (WaitLock.sceneTransition.get()) {
                Log.d(TAG, "wait for sceneTransition");
                try {
                    WaitLock.sceneTransition.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (WaitLock.errorDetected)
        {
            if (WaitLock.errorDetected.get()) {
                Log.d(TAG, "wait for error");
                try {
                    WaitLock.errorDetected.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (WaitLock.fairyWindowDetected)
        {
            if (WaitLock.fairyWindowDetected.get()) {
                Log.d(TAG, "wait for fairywindow");
                try {
                    WaitLock.fairyWindowDetected.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
