package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTop6HerosTask;

public class Heros extends Menu {

    public static final String TAG = Heros.class.getSimpleName();
    private Boss boss;
    private long lastTop6HerosLeveld = 0;
    private long lastAllHerosLeveld = 0;

    private final Point lvlFIrsHeroButton_color = new Point(470,579);
    public static final Point lvlFIrsHeroButton_click = new Point(424,579);

    private final Point lvlSecondHeroButton_color = new Point(470,652);
    private final Point lvlSecondHeroButton_click = new Point(424,652);

    private final Point lvlThirdHeroButton_color = new Point(470,726);
    public static final Point lvlThirdHeroButton_click = new Point(424,726);
    private int lvlAllHerosRunCount = 0;

    public Heros(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;

    }

    @Override
    void init() {
        lastTop6HerosLeveld = 0;
        lastAllHerosLeveld = 0;
        lvlAllHerosRunCount = 0;
    }

    @Override
    boolean checkIfRdyToExecute() {
        if (botSettings.autoLvlHeros) {
            if (botSettings.levelAllHeroTime > 0
                    && botSettings.autoLvlHeros && System.currentTimeMillis() - lastAllHerosLeveld > botSettings.levelAllHeroTime
                    && boss.getBossState() != Boss.BossState.BossFightActive
                    && (lvlAllHerosRunCount < botSettings.levelAllHeroCount || botSettings.levelAllHeroCount == 0)) {
                lvlAllHerosRunCount++;
                Log.v(TAG, "level ALL Heros");
                bot.executeTask(LevelAllHerosTask.class);
                lastAllHerosLeveld = System.currentTimeMillis();
                lastTop6HerosLeveld = System.currentTimeMillis();
            }
            if (boss.getBossState() != Boss.BossState.BossFightActive && timeOver()) {
                Log.v(TAG, "level Top6 Heros");
                bot.executeTask(LevelTop6HerosTask.class);
                lastTop6HerosLeveld = System.currentTimeMillis();
                return true;
            }
        }
       /* else if (debug) {
            Log.d(TAG, "not rdy to execute " + boss.getBossState() + " timeover: "+timeOver());}*/
        return false;
    }

    private boolean timeOver()
    {
        return System.currentTimeMillis() - lastTop6HerosLeveld > botSettings.levelTop6HeroTime;
    }

    public void levelTop6Heros(ExecuterTask task) throws InterruptedException, IOException {
        openHeroMenu();
        WaitLock.checkForErrorAndWait();
        //make sure we are on top
        while (!isMenuTopReached() && !task.cancelTask && Menu.MenuOpen.get()) {
            WaitLock.checkForErrorAndWait();
            touchInput.swipeVertical(lvlFIrsHeroButton_click, lvlThirdHeroButton_click);
            WaitLock.checkForErrorAndWait();
            Thread.sleep(300);
            WaitLock.checkForErrorAndWait();
        }
        Thread.sleep(500);
        WaitLock.checkForErrorAndWait();
        level3heros(task);
        WaitLock.checkForErrorAndWait();
        touchInput.swipeVertical(new Point(240, 707), new Point(240, 556));
        WaitLock.checkForErrorAndWait();
        touchInput.swipeVertical(new Point(240, 707), new Point(240, 556));
        WaitLock.checkForErrorAndWait();
        level3heros(task);
        WaitLock.checkForErrorAndWait();
        Thread.sleep(200);
        WaitLock.checkForErrorAndWait();
        closeHeroMenu();
        WaitLock.checkForErrorAndWait();
        Thread.sleep(200);
        WaitLock.checkForErrorAndWait();

        lastTop6HerosLeveld = System.currentTimeMillis();
    }

    public void lvlAllHeros(ExecuterTask task) throws InterruptedException, IOException {
        openHeroMenu();
        WaitLock.checkForErrorAndWait();
        for (int i=0; i< 25; i++) {
            if (task.cancelTask)
                return;
            WaitLock.checkForErrorAndWait();
            touchInput.swipeVertical(new Point(240, 707), new Point(240, 556));
            WaitLock.checkForErrorAndWait();
        }
        if (!task.cancelTask)
            Thread.sleep(1500);
        WaitLock.checkForErrorAndWait();
        if (!task.cancelTask)
            Thread.sleep(100);
        WaitLock.checkForErrorAndWait();
        int loopbreaker = 0;
        while (!isMenuTopReached() && breakCondition(loopbreaker,25,task)) {
            loopbreaker++;
            WaitLock.checkForErrorAndWait();
            Thread.sleep(200);
            WaitLock.checkForErrorAndWait();
            level3heros(task);
            WaitLock.checkForErrorAndWait();
            touchInput.swipeVertical(new Point(240,556),new Point(240,707));
            WaitLock.checkForErrorAndWait();
            Thread.sleep(400);
            WaitLock.checkForErrorAndWait();
        }
        closeHeroMenu();

        lastAllHerosLeveld = System.currentTimeMillis();
        lastTop6HerosLeveld = System.currentTimeMillis();
    }


    private void level3heros(ExecuterTask task) throws IOException, InterruptedException {
        levelhero(lvlFIrsHeroButton_color,lvlFIrsHeroButton_click,task,1);
        levelhero(lvlSecondHeroButton_color,lvlSecondHeroButton_click,task,2);
        levelhero(lvlThirdHeroButton_color,lvlThirdHeroButton_click,task,3);
    }

    private void levelhero(Point color, Point click, ExecuterTask task, int id) throws IOException, InterruptedException {
        int loopbreaker = 0;
        while (level3HeroBreakConditions(color,loopbreaker,12,task,id)) {
            loopbreaker++;
            tapOnHero(1, click);
            WaitLock.checkForErrorAndWait();
            if (!task.cancelTask)
                Thread.sleep(100); //TODO this time may can get lowered, or removed with a wait for a new frame.
            WaitLock.checkForErrorAndWait();
        }
    }

    private boolean level3HeroBreakConditions(Point color, int loopbreaker, int loopbreakerMax, ExecuterTask task, int id)
    {
        boolean canlevel = breakCondition(loopbreaker,loopbreakerMax,task);
        if(canlevel) {
            canlevel = canLevelHero(bot.getScreeCapture().getColor(color), id);
            Log.d(TAG, "canLevelHero:" + canlevel);
        }
        return canlevel;
    }

    private boolean breakCondition(int loopbreaker, int loopbreakerMax, ExecuterTask task)
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
            canlevel = Menu.MenuOpen.get() == true;
            Log.d(TAG, "MenuOPen:" + Menu.MenuOpen);
        }
        return canlevel;
    }

    private void tapOnHero(int times,Point point) throws IOException, InterruptedException {
        for (int i = 0; i< times;i++) {
            WaitLock.checkForErrorAndWait();
            doSingelTap(point);
            Thread.sleep(100);
            WaitLock.checkForErrorAndWait();
        }
    }

    private boolean canLevelHero(int color, int heroid)
    {
        boolean canlvl = false;
        if (!ColorUtils.isGray(color))
            canlvl = true;
        Log.v(TAG,"canLevelHero "+heroid+": " + canlvl + " " +ColorUtils.getColorString(color));
        return canlvl;
    }
}
