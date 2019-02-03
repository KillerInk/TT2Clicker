package clickerbot.com.troop.clickerbot.touch;

import android.graphics.Point;

import java.io.IOException;

public interface TouchInterface {
    void tap(Point pos,int duration) throws InterruptedException;
    void swipeVertical(Point from, Point to) throws InterruptedException, IOException;
    void close();
    void start();
    void stop();
}
