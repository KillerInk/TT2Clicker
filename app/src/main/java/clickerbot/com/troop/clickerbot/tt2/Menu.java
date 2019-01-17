package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.MenuCloseTask;

public abstract class Menu extends Item
{
    private final String TAG = Menu.class.getSimpleName();
    public static volatile boolean MenuOpen = false;

    private final int menuOpenCloseDelay = 1000;
    ArrayList<Integer> maxButtonColors;

    public final Point MenuMaxButtonColorPosition = new Point(335,518);

    public Menu(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        maxButtonColors = getMaxButtonColors();
    }

    private int colorMenuOpen = Color.argb(255,84,76,76);
    private Point menuOpenCheckPoint = new Point(471,449);
    private boolean menuTaskRunning = false;

    public boolean isMenuTopReached()
    {
        int color = bot.getScreeCapture().getColor(MenuMaxButtonColorPosition);
        boolean yep = false;

        if (maxButtonColors.contains(color))
            yep = true;
        Log.d(TAG, "isMenuTopReached: "+ yep + " " + ColorUtils.getColorString(color));
        return yep;
    }

    private ArrayList<Integer> getMaxButtonColors()
    {
        ArrayList<Integer> arr = new ArrayList();
        arr.add(-11178377);
        arr.add(-10914692);
        arr.add(-10914948);
        arr.add(-10849155);
        arr.add(-10914691);
        arr.add(-11112327);
        return arr;
    }

    public void checkIfMenuOpen()
    {
        if (!MenuOpen && !menuTaskRunning) {
            int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
            if (color == colorMenuOpen) {
                menuTaskRunning = true;
                bot.putFirstAndExecuteTask(MenuCloseTask.class);
            }
        }
    }

    public void closeMenu()
    {
        Log.d(TAG, "closeMenu");
        try {
            touchInput.tap(Coordinates.Menu_Close);
            Thread.sleep(100);
            touchInput.tap(Coordinates.Menu_Close);
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MenuOpen = false;
        menuTaskRunning = false;
        Log.d(TAG, "closedMenu");
    }

    public void openSwordMasterMenu()
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "openSwordMasterMenu");
        MenuOpen = true;
        try {
            touchInput.tap(Coordinates.Menu_SwordMaster);
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeSwordMasterMenu()
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "openSwordMasterMenu");
        MenuOpen = false;
        try {
            touchInput.tap(Coordinates.Menu_SwordMaster);
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openHeroMenu()
    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "openHeroMenu");
        MenuOpen =true;
        try {
            touchInput.tap(Coordinates.Menu_Heros);
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void closeHeroMenu()
    {
        Log.d(TAG, "openHeroMenu");
        MenuOpen =false;
        try {
            touchInput.tap(Coordinates.Menu_Heros);
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void maximiseMenu()
    {
        Log.d(TAG, "maximiseMenu");
        try {
            touchInput.tap(Coordinates.Menu_Maximise);
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void minimiseMenu()
    {
        Log.d(TAG, "minimiseMenu");
        try {
            touchInput.tap(Coordinates.Menu_Minimise);
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void swipeUp()
    {
        try {
            swipeUp(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void swipeUp(int pixel) throws InterruptedException {
        try {
            touchInput.swipeVertical(new Point(280 ,600),new Point(280,600+pixel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public void swipe(int pixel,int duration)
    {
        try {
            rootShells[0].doSwipe(new Point(280 ,600),new Point(280,600+pixel),duration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
/*
    public void swipe(Point from,Point to,int duration)
    {
        try {
            rootShells[0].doSwipe(from,to,duration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public void menuTest() throws InterruptedException, IOException {
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

    private final int swipeTopPos = 585;
    private final int swipeBottomPos = 734;

    public void swipeUp3xHeros() throws InterruptedException, IOException {
        touchInput.swipeVertical(new Point(200,swipeTopPos),new Point(200,swipeBottomPos));
    }

    public void swipeDown3xHeros() throws InterruptedException, IOException {
        touchInput.swipeVertical(new Point(200,swipeBottomPos),new Point(200,swipeTopPos));
    }
}
