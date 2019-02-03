package clickerbot.com.troop.clickerbot.tt2.tasks.test;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.Coordinates;

public class SwipeTest extends ExecuterTask {

    private final TouchInterface touchInterface;

    public SwipeTest(TouchInterface touchInterface)
    {
        this.touchInterface = touchInterface;
    }

    int hero_menu_item_height = 76;
    @Override
    public void doTask() {
        try {
            touchInterface.tap(Coordinates.Menu_Heros,30);
            Thread.sleep(1000);
            touchInterface.swipeVertical(new Point(240,707),new Point(240,556));
            Thread.sleep(100);
            touchInterface.swipeVertical(new Point(240,707),new Point(240,556));
            /*Thread.sleep(100);
            touchInterface.swipeVertical(new Point(240,707),new Point(240,556));*/

            Thread.sleep(2000);
            touchInterface.swipeVertical(new Point(240,556),new Point(240,707));
            Thread.sleep(100);
            touchInterface.swipeVertical(new Point(240,556),new Point(240,707));
            Thread.sleep(100);
            touchInterface.swipeVertical(new Point(240,556),new Point(240,707));
           /* Thread.sleep(2000);
            touchInterface.tap(Coordinates.Menu_Close);*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
