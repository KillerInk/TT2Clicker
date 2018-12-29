package clickerbot.com.troop.clickerbot;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import java.io.IOException;

public interface IBot {
    boolean getIsRunning();
    void start();
    void stop();
    void dumpScreen() throws InterruptedException, IOException;
    int getColor(Point point);
    OCR getOcr();
    Bitmap getAreaFromScreen(Rect rect);
}
