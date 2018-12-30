package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.RootShell;

public class Menu
{
    protected RootShell rootShellClick[];

    private final int menuOpenCloseDelay = 1000;

    public void setRootShellClick(RootShell rootShellClick[])
    {
        this.rootShellClick = rootShellClick;
    }

    public void closeMenu()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_Close);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openSwordMasterMenu()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_SwordMaster);
            Thread.sleep(menuOpenCloseDelay);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openHeroMenu()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_Heros);
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
            rootShellClick[0].doTap(Coordinates.Menu_Maximise);
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
            rootShellClick[0].doTap(Coordinates.Menu_Minimise);
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
            rootShellClick[0].doSwipe(new Point(280 ,600),new Point(280,600+pixel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
