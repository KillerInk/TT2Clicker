package clickerbot.com.troop.clickerbot.tt2;

import java.util.concurrent.atomic.AtomicBoolean;

public class WaitLock
{
    public static AtomicBoolean errorDetected = new AtomicBoolean();
    public static AtomicBoolean fairyWindowDetected = new AtomicBoolean();

    public static void lockError(boolean lock)
    {
        if (lock)
        {
            synchronized (errorDetected)
            {
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

    public static void checkForErrorAndWait()
    {
        synchronized (WaitLock.errorDetected)
        {
            if (WaitLock.errorDetected.get()) {
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
                try {
                    WaitLock.fairyWindowDetected.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
