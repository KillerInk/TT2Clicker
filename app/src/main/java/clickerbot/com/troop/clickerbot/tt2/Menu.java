package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.RootShell;

public abstract class Menu extends Item
{
    public static boolean MenuOpen = false;

    private final int menuOpenCloseDelay = 1000;

    public Menu(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell) {
        super(ibot, botSettings, rootShell);
    }


    public void closeMenu()
    {
        try {
            rootShells[0].doTap(Coordinates.Menu_Close);
            Thread.sleep(10);
            rootShells[0].doTap(Coordinates.Menu_Close);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MenuOpen = false;
    }

    public void openSwordMasterMenu()
    {
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

    public void openHeroMenu()
    {
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

    public void maximiseMenu()
    {
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

}
