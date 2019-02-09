package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.MenuCloseTask;

import static clickerbot.com.troop.clickerbot.tt2.Heros.lvlFIrsHeroButton_click;
import static clickerbot.com.troop.clickerbot.tt2.Heros.lvlThirdHeroButton_click;

public abstract class Menu extends Item
{
    private final String TAG = Menu.class.getSimpleName();
    public static AtomicBoolean MenuOpen = new AtomicBoolean(false);

    private final int menuOpenCloseDelay = 1000;
    ArrayList<Integer> maxButtonColors;

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
    private int colorMenuOpen = Color.argb(255,84,76,76);
    private Point menuOpenCheckPoint = new Point(471,449);
    private Point menuMaximizedCheckPoint = new Point(471,12);
    private boolean menuTaskRunning = false;

    public Menu(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        maxButtonColors = getMaxButtonColors();
    }



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



    private boolean isMenuOpen()
    {
        int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
        return color == colorMenuOpen;
    }

    private boolean isMenuMaximized()
    {
        int color = bot.getScreeCapture().getColor(menuMaximizedCheckPoint);
        return color == colorMenuOpen;
    }

    private void openMenu(Point point, ExecuterTask task)
    {
        WaitLock.checkForErrorAndWait();
        setMenuState(MenuState.opening);
        WaitLock.checkForErrorAndWait();
        Log.d(TAG, "openSwordMasterMenu");

        while (!isMenuOpen() && !Thread.currentThread().isInterrupted() && !task.cancelTask) {
            try {
                doSingelTap(point);
                Thread.sleep(menuOpenCloseDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MenuOpen.set(true);
        setMenuState(MenuState.open);
    }

    private void closeMenu(Point point,ExecuterTask task)
    {
        WaitLock.checkForErrorAndWait();
        setMenuState(MenuState.closing);
        Log.d(TAG, "closeMenu");
        try {
            while (isMenuOpen()&& !Thread.currentThread().isInterrupted() && !task.cancelTask) {
                WaitLock.checkForErrorAndWait();
                doSingelTap(point);
                Thread.sleep(menuOpenCloseDelay);
                WaitLock.checkForErrorAndWait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MenuOpen.set(false);
        setMenuState(MenuState.closed);
    }

    public void openSwordMasterMenu(ExecuterTask task)
    {
        openMenu(Coordinates.Menu_SwordMaster,task);
    }

    public void closeSwordMasterMenu(ExecuterTask task)
    {
        closeMenu(Coordinates.Menu_SwordMaster,task);
    }

    public void openHeroMenu(ExecuterTask task)
    {
        openMenu(Coordinates.Menu_Heros,task);
    }

    public void closeHeroMenu(ExecuterTask task)
    {
        closeMenu(Coordinates.Menu_Heros,task);
    }

    public void openArtifactMenu(ExecuterTask task)
    {
        openMenu(Coordinates.Menu_Artifacts,task);
    }

    public void closeArtifactMenu(ExecuterTask task)
    {
        closeMenu(Coordinates.Menu_Artifacts,task);
    }




    public void maximiseMenu()
    {
        while (!isMenuMaximized()&& !Thread.currentThread().isInterrupted()) {
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
    }

    public void minimiseMenu()
    {
        while (isMenuMaximized()&& !Thread.currentThread().isInterrupted()) {
            WaitLock.checkForErrorAndWait();
            Log.d(TAG, "minimiseMenu");
            try {

                doSingelTap(Coordinates.Menu_Minimise);
                Thread.sleep(menuOpenCloseDelay);
                WaitLock.checkForErrorAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        menuState = MenuState.open;
    }

    public void gotToTop(ExecuterTask task) throws IOException, InterruptedException {
        while (!isMenuTopReached() && !task.cancelTask && Menu.MenuOpen.get()) {
            WaitLock.checkForErrorAndWait();
            touchInput.swipeVertical(lvlFIrsHeroButton_click, lvlThirdHeroButton_click);
            WaitLock.checkForErrorAndWait();
            Thread.sleep(300);
            WaitLock.checkForErrorAndWait();
        }
        WaitLock.checkForErrorAndWait();
        touchInput.swipeVertical(lvlFIrsHeroButton_click, lvlThirdHeroButton_click);
        WaitLock.checkForErrorAndWait();
        Thread.sleep(300);
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


    public void menuTest(ExecuterTask task) throws InterruptedException, IOException {
       /* openHeroMenu();
        closeMenu();
        openSwordMasterMenu();
        closeMenu();*/

        openHeroMenu(task);

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
