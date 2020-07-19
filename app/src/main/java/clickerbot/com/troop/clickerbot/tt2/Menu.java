package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.MenuCloseTask;

public abstract class Menu extends Item
{
    private final String TAG = Menu.class.getSimpleName();


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
    private int colorMenuOpen = Color.argb(255,85,80,81);
    private Point menuOpenCheckPoint = new Point(471,449);
    private Point menuMaximizedCheckPoint = new Point(471,12);
    private boolean menuTaskRunning = false;
    private final Point swipeTopPoint =  new Point(200, 517);
    private final Point swipeBottomPoint = new Point(200, 744);

    private final Point swipeTopPointMaximised =  new Point(200, 129);
    private final Point swipeBottomPointMaximised = new Point(200, 729);

    //background color from menu when swipe moved down top items
    private final int colorMenuTopReached = Color.argb(255,32,32,41);
    private final  Point checkPoint_colorMenuTopReached = new Point(77,189);
    private final  Point checkPoint_colorMenuTopReached2 = new Point(77,199);
    private final  Point checkPoint_colorMenuBottomReached = new Point(40,757);
    private final  Point checkPoint_colorMenuBottomReached2 = new Point(40,747);

    public Menu(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        maxButtonColors = getMaxButtonColors();
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
            if ((isMenuOpen() || isMenuMaximized()) && !menuTaskRunning && menuState == MenuState.closed) {
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
            if (isMenuOpen())
                doSingelTap(Coordinates.Menu_Close,"closeMenu");
            if (isMenuMaximized())
                doSingelTap(Coordinates.Menu_Close_Maximised,"closeMenu");
            WaitLock.checkForErrorAndWait();
            Thread.sleep(menuOpenCloseDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WaitLock.checkForErrorAndWait();
        if (!isMenuOpen() && !isMenuMaximized()) {
            menuState = MenuState.closed;
        }
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
        if (isMenuOpen()) {
            setMenuState(MenuState.open);
        }
        if (isMenuMaximized()) {
            setMenuState(MenuState.maximise);
        }
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
        if (!isMenuOpen() && !isMenuMaximized()) {
            setMenuState(MenuState.closed);
        }
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
            try {
                doSingelTap(Coordinates.Menu_Maximise,"maximiseMenu");
                WaitLock.checkForErrorAndWait();
                Thread.sleep(menuOpenCloseDelay);
                WaitLock.checkForErrorAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isMenuMaximized())
                menuState = MenuState.maximise;
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
        if (!isMenuMaximized() && isMenuOpen())
            menuState = MenuState.open;
    }

    public void gotToTopMaximised(ExecuterTask task) throws IOException, InterruptedException {
        int loopbreaker = 0;
        boolean istop = false;
        while (!istop && breakCondition(loopbreaker,15,task) && !task.cancelTask && Menu.getMenuState() == MenuState.maximise) {
            WaitLock.checkForErrorAndWait();
            //swipeUpMaximised();
            touchInput.swipeVertical(swipeTopPointMaximised,swipeBottomPointMaximised, false);
            int color = bot.getScreeCapture().getColor(checkPoint_colorMenuTopReached);
            int color2 = bot.getScreeCapture().getColor(checkPoint_colorMenuTopReached2);
            if (ColorUtils.colorIsInRange(color,colorMenuTopReached,5) && ColorUtils.colorIsInRange(color2,colorMenuTopReached,5))
                istop = true;
            touchInput.releaseTouch();
            WaitLock.checkForErrorAndWait();
            Thread.sleep(300);
            WaitLock.checkForErrorAndWait();
            loopbreaker++;
        }
        Thread.sleep(100);
    }

    public void gotToBottomMaximised(ExecuterTask task) throws IOException, InterruptedException {
        int loopbreaker = 0;
        boolean istop = false;
        while (!istop && breakCondition(loopbreaker,12,task) && !task.cancelTask && Menu.getMenuState() == MenuState.maximise) {
            WaitLock.checkForErrorAndWait();
            //swipeUpMaximised();
            touchInput.swipeVertical(swipeBottomPointMaximised ,swipeTopPointMaximised, false);
            int color = bot.getScreeCapture().getColor(checkPoint_colorMenuBottomReached);
            int color2 = bot.getScreeCapture().getColor(checkPoint_colorMenuBottomReached);
            if (ColorUtils.colorIsInRange(color,colorMenuTopReached,5) && ColorUtils.colorIsInRange(color2,colorMenuTopReached,5))
                istop = true;
            touchInput.releaseTouch();
            WaitLock.checkForErrorAndWait();
            Thread.sleep(300);
            WaitLock.checkForErrorAndWait();
            loopbreaker++;
        }
        Thread.sleep(100);
    }

    public void swipeDown() throws IOException, InterruptedException {
        touchInput.swipeVertical(swipeBottomPoint,swipeTopPoint, true);
    }

    public void swipeUp() throws InterruptedException, IOException {
        touchInput.swipeVertical(swipeTopPoint,swipeBottomPoint, true);

    }

    public void swipeDownMaximised() throws IOException, InterruptedException {
        touchInput.swipeVertical(swipeBottomPointMaximised,swipeTopPointMaximised, true);
    }

    public void swipeUpMaximised() throws InterruptedException, IOException {
        touchInput.swipeVertical(swipeTopPointMaximised,swipeBottomPointMaximised, true);

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

    protected boolean breakCondition(int loopbreaker, int loopbreakerMax, ExecuterTask task)
    {
        boolean canlevel = true;
        if (canlevel) {
            canlevel = loopbreaker < loopbreakerMax;
            Log.d(TAG, "loopbreak not triggered:" + canlevel);
        }
        if (canlevel) {
            canlevel = task.cancelTask != true;
            Log.d(TAG, "task canceld:" + task.cancelTask);
        }
        if (canlevel) {
            canlevel =  Menu.getMenuState() != MenuState.closed;
            Log.d(TAG, "MenuOPen:" + Menu.getMenuState());
        }
        return canlevel;
    }

}
