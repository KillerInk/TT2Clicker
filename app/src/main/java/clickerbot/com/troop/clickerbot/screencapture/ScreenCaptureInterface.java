package clickerbot.com.troop.clickerbot.screencapture;

import android.graphics.Bitmap;
import android.graphics.Point;

public interface ScreenCaptureInterface {
    void create();
    void destroy();
    Bitmap getBitmapFromQueue(Bitmap map);
    void start();
    void stop();
    void close();

}
