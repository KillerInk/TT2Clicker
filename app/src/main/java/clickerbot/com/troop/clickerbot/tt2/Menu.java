package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.RootShell;

public class Menu
{
    protected RootShell rootShellClick[];

    public void setRootShellClick(RootShell rootShellClick[])
    {
        this.rootShellClick = rootShellClick;
    }

    public void closeMenu()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_Close);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSwordMasterMenu()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_SwordMaster);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void maximiseMenu()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_Maximise);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void minimiseMenu()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_Minimise);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swipeUp()
    {
        try {
            rootShellClick[0].doSwipe(new Point(280 ,600),new Point(280,700));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
