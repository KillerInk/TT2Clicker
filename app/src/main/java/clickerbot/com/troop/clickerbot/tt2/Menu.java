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
    public final Point MenuMaximisedMaxButtonColorPosition = new Point(335,60);
    private int colorMenuOpen = Color.argb(255,84,76,76);
    private Point menuOpenCheckPoint = new Point(471,449);
    private Point menuMaximizedCheckPoint = new Point(471,12);
    private boolean menuTaskRunning = false;
    private final Point swipeTopPoint =  new Point(200, 517);
    private final Point swipeBottomPoint = new Point(200, 744);

    private final Point swipeTopPointMaximised =  new Point(200, 129);
    private final Point swipeBottomPointMaximised = new Point(200, 729);

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

    public boolean isMenuTopMaximisedReached()
    {
        int color = bot.getScreeCapture().getColor(MenuMaximisedMaxButtonColorPosition);
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
        arr.add(Color.argb(255,89,116,125));
        return arr;
    }

    public void checkIfMenuOpen()
    {
        int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
        if (!WaitLock.sceneTransition.get() && !WaitLock.fairyWindowDetected.get() && !WaitLock.clanquest.get()) {
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
        menuState = MenuState.closing;
        try {
            WaitLock.checkForErrorAndWait();
            doSingelTap(Coordinates.Menu_Close,"closeMenu");
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



    public boolean isMenuOpen()
    {
        int color = bot.getScreeCapture().getColor(menuOpenCheckPoint);
        return color == colorMenuOpen;
    }

    public boolean isMenuMaximized()
    {
        int color = bot.getScreeCapture().getColor(menuMaximizedCheckPoint);
        return color == colorMenuOpen;
    }

    private void openMenu(Point point, ExecuterTask task)
    {
        WaitLock.checkForErrorAndWait();
        setMenuState(MenuState.opening);
        WaitLock.checkForErrorAndWait();

        while ((!isMenuOpen() && !isMenuMaximized()) && !Thread.currentThread().isInterrupted() && !task.cancelTask) {
            try {
                doSingelTap(point,"openSwordMasterMenu");
                Thread.sleep(menuOpenCloseDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MenuOpen.set(true);
        setMenuState(MenuState.open);
        if (isMenuMaximized())
            setMenuState(MenuState.maximise);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void closeMenu(Point point,ExecuterTask task)
    {
        WaitLock.checkForErrorAndWait();
        setMenuState(MenuState.closing);
        try {
            while ((isMenuOpen() || isMenuMaximized())&& !Thread.currentThread().isInterrupted() && !task.cancelTask) {
                WaitLock.checkForErrorAndWait();
                doSingelTap(point,"closeMenu");
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




    public void maximiseMenu(ExecuterTask task)
    {
        while (!isMenuMaximized()&& !Thread.currentThread().isInterrupted() && !task.cancelTask) {
            WaitLock.checkForErrorAndWait();
            menuState = MenuState.maximise;
            try {
                doSingelTap(Coordinates.Menu_Maximise,"maximiseMenu");
                WaitLock.checkForErrorAndWait();
                Thread.sleep(menuOpenCloseDelay);
                WaitLock.checkForErrorAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void minimiseMenu(ExecuterTask task)
    {
        while (isMenuMaximized()&& !Thread.currentThread().isInterrupted() && !task.cancelTask) {
            WaitLock.checkForErrorAndWait();
            try {

                doSingelTap(Coordinates.Menu_Minimise,"minimiseMenu");
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
            swipeUp();
            WaitLock.checkForErrorAndWait();
            Thread.sleep(300);
            WaitLock.checkForErrorAndWait();
        }
       /* WaitLock.checkForErrorAndWait();
        swipeUp();
        WaitLock.checkForErrorAndWait();*/
        Thread.sleep(100);
    }

    public void gotToTopMaximised(ExecuterTask task) throws IOException, InterruptedException {
        while (!isMenuTopMaximisedReached() && !task.cancelTask && Menu.getMenuState() == MenuState.maximise) {
            WaitLock.checkForErrorAndWait();
            swipeUpMaximised();
            WaitLock.checkForErrorAndWait();
            Thread.sleep(300);
            WaitLock.checkForErrorAndWait();
        }
       /* WaitLock.checkForErrorAndWait();
        swipeUp();
        WaitLock.checkForErrorAndWait();*/
        Thread.sleep(100);
    }

    public void swipeDown() throws IOException, InterruptedException {
        touchInput.swipeVertical(swipeBottomPoint,swipeTopPoint);
    }

    public void swipeUp() throws InterruptedException, IOException {
        touchInput.swipeVertical(swipeTopPoint,swipeBottomPoint);

    }

    public void swipeDownMaximised() throws IOException, InterruptedException {
        touchInput.swipeVertical(swipeBottomPointMaximised,swipeTopPointMaximised);
    }

    public void swipeUpMaximised() throws InterruptedException, IOException {
        touchInput.swipeVertical(swipeTopPointMaximised,swipeBottomPointMaximised);

    }



    public void menuTest(ExecuterTask task) throws InterruptedException, IOException {
       /* openHeroMenu();
        closeMenu();
        openSwordMasterMenu();
        closeMenu();*/

        openHeroMenu(task);

            swipeDown();
            Thread.sleep(1000);
            swipeDown();
            Thread.sleep(1000);



    }


}
