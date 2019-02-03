package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.MenuCloseTask;

public abstract class Menu extends Item
{
    private final String TAG = Menu.class.getSimpleName();
    public static AtomicBoolean MenuOpen = new AtomicBoolean(false);

    private final int menuOpenCloseDelay = 1500;
    ArrayList<Integer> maxButtonColors;
    private final int screenTransitionColor = Color.argb(255,25,25,25);

    private static MenuState menuState;

    public static synchronized  void setMenuState(MenuState state)
    {
        menuState = state;
    }

    public static synchronized MenuState getMenuState()
    {
        return menuState;
    }

    public enum MenuState
    {
        opening,
        open,
        closing,
        closed,
        maximise
    }


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
        int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
        if (!WaitLock.sceneTransition.get()) {
            MenuOpen.set(color == colorMenuOpen);
            if (MenuOpen.get() && !menuTaskRunning && menuState == MenuState.closed) {
                menuTaskRunning = true;
                bot.putFirstAndExecuteTask(MenuCloseTask.class);
            }
        }
    }

    public void closeMenu()
    {
        WaitLock.checkForErrorAndWait();
        Log.d(TAG, "closeMenu");
        menuState = MenuState.closing;
        try {
            WaitLock.checkForErrorAndWait();
            doSingelTap(Coordinates.Menu_Close);
            WaitLock.checkForErrorAndWait();
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WaitLock.checkForErrorAndWait();
        int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
        if (color != colorMenuOpen)
            MenuOpen.set(false);
        menuState = MenuState.closed;
        menuTaskRunning = false;
        Log.d(TAG, "closedMenu");
    }

    public void openSwordMasterMenu()
    {
        WaitLock.checkForErrorAndWait();
        setMenuState(MenuState.opening);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WaitLock.checkForErrorAndWait();
        Log.d(TAG, "openSwordMasterMenu");

        try {
            doSingelTap(Coordinates.Menu_SwordMaster);
            Thread.sleep(menuOpenCloseDelay);
            WaitLock.checkForErrorAndWait();
            int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
            if (color == colorMenuOpen)
                MenuOpen.set(true);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMenuState(MenuState.open);
    }

    public void closeSwordMasterMenu()
    {
        WaitLock.checkForErrorAndWait();
        setMenuState(MenuState.closing);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "openSwordMasterMenu");
        try {
            WaitLock.checkForErrorAndWait();
            doSingelTap(Coordinates.Menu_SwordMaster);
            Thread.sleep(menuOpenCloseDelay);
            WaitLock.checkForErrorAndWait();
            int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
            if (color != colorMenuOpen)
                MenuOpen.set(false);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMenuState(MenuState.closed);
    }

    public void openHeroMenu()
    {
        WaitLock.checkForErrorAndWait();
        setMenuState(MenuState.opening);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WaitLock.checkForErrorAndWait();
        Log.d(TAG, "openHeroMenu");
        try {
            doSingelTap(Coordinates.Menu_Heros);
            Thread.sleep(menuOpenCloseDelay);
            WaitLock.checkForErrorAndWait();
            int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
            if (color == colorMenuOpen)
                MenuOpen.set(true);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMenuState(MenuState.open);
    }

    public void closeHeroMenu()
    {
        WaitLock.checkForErrorAndWait();
        Log.d(TAG, "openHeroMenu");
        setMenuState(MenuState.closing);
        try {
            doSingelTap(Coordinates.Menu_Heros);
            Thread.sleep(menuOpenCloseDelay);
            WaitLock.checkForErrorAndWait();
            int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
            if (color != colorMenuOpen)
                MenuOpen.set(false);
            WaitLock.checkForErrorAndWait();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMenuState(MenuState.closed);
    }

    public void maximiseMenu()
    {
        WaitLock.checkForErrorAndWait();
        menuState = MenuState.maximise;
        Log.d(TAG, "maximiseMenu");
        try {
            doSingelTap(Coordinates.Menu_Maximise);
            WaitLock.checkForErrorAndWait();
            Thread.sleep(menuOpenCloseDelay);
            WaitLock.checkForErrorAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void minimiseMenu()
    {
        WaitLock.checkForErrorAndWait();
        Log.d(TAG, "minimiseMenu");
        try {

            doSingelTap(Coordinates.Menu_Minimise);
            Thread.sleep(menuOpenCloseDelay);
            WaitLock.checkForErrorAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menuState = MenuState.open;
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
        WaitLock.checkForErrorAndWait();
        touchInput.swipeVertical(new Point(200,swipeBottomPos),new Point(200,swipeTopPos));
        WaitLock.checkForErrorAndWait();
    }

}
