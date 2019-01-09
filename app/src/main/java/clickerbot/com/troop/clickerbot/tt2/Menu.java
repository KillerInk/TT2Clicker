package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.RootShell;

public abstract class Menu extends Item
{
    private final String TAG = Menu.class.getSimpleName();
    public static boolean MenuOpen = false;

    private final int menuOpenCloseDelay = 1000;

    public final int MenuMaxButtonBackgroundColor = Color.argb(255,65,82,82);
    public final Point MenuMaxButtonColorPosition = new Point(465,500);

    public Menu(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell) {
        super(ibot, botSettings, rootShell);
    }


    public void closeMenu()
    {
        Log.d(TAG, "closeMenu");
        try {
            rootShells[0].doTap(Coordinates.Menu_Close);
            Thread.sleep(100);
            rootShells[0].doTap(Coordinates.Menu_Close);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MenuOpen = false;
        Log.d(TAG, "closedMenu");
    }

    public void openSwordMasterMenu()
    {
        Log.d(TAG, "openSwordMasterMenu");
        MenuOpen = true;
        try {
            rootShells[0].doTap(Coordinates.Menu_SwordMaster);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeSwordMasterMenu()
    {
        Log.d(TAG, "openSwordMasterMenu");
        MenuOpen = false;
        try {
            rootShells[0].doTap(Coordinates.Menu_SwordMaster);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openHeroMenu()
    {
        Log.d(TAG, "openHeroMenu");
        MenuOpen =true;
        try {
            rootShells[0].doTap(Coordinates.Menu_Heros);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void closeHeroMenu()
    {
        Log.d(TAG, "openHeroMenu");
        MenuOpen =false;
        try {
            rootShells[0].doTap(Coordinates.Menu_Heros);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void maximiseMenu()
    {
        Log.d(TAG, "maximiseMenu");
        try {
            rootShells[0].doTap(Coordinates.Menu_Maximise);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void minimiseMenu()
    {
        Log.d(TAG, "minimiseMenu");
        try {
            rootShells[0].doTap(Coordinates.Menu_Minimise);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void swipeUp()
    {
        swipeUp(100);
    }

    public void swipeUp(int pixel)
    {
        try {
            rootShells[0].doSwipe(new Point(280 ,600),new Point(280,600+pixel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swipe(int pixel,int duration)
    {
        try {
            rootShells[0].doSwipe(new Point(280 ,600),new Point(280,600+pixel),duration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swipe(Point from,Point to,int duration)
    {
        try {
            rootShells[0].doSwipe(from,to,duration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void menuTest() throws InterruptedException {
       /* openHeroMenu();
        closeMenu();
        openSwordMasterMenu();
        closeMenu();*/

        openHeroMenu();
        swipeDown3xHeros();
        Thread.sleep(1000);
        swipeDown3xHeros();
        Thread.sleep(1000);

    }

    public void swipeUp3xHeros() throws InterruptedException {
        swipe(new Point(261,590),new Point(261,766),1000);
        Thread.sleep(1000);
    }

    public void swipeDown3xHeros() throws InterruptedException {
        //swipe(new Point(261,766),new Point(261,590),1000);
        swipe(new Point(261,766),new Point(261,590),1000);
        Thread.sleep(1000);
    }
}
