package clickerbot.com.troop.clickerbot;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import java.io.IOException;

public interface IBot {
    boolean getIsRunning();
    void start();
    void stop();

    OCR getOcr();

    ScreenCapture getScreeCapture();
    public void resetTickCounter();

    void execute(Runnable runnable);
    void clearExecuterQueue();
}
