package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTopHerosTask;

public class Heros extends Menu {

    public static final String TAG = Heros.class.getSimpleName();
    private Boss boss;
    private long lastTop6HerosLeveld = 0;
    private long lastAllHerosLeveld = 0;
    private int lvlAllHerosRunCount = 0;

    private final int hero_click_X = 424;
    public static final int hero_color_X = 470;
    private Integer[] hero_Y_coordinates = new Integer[]{
            134,
            214,
            289,
            366,
            446,
            523,
            599,
            675,
            749,
    };


    public Heros(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;

    }

    @Override
    void init(ExecuterTask task) {
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
            if (boss.getBossState() != Boss.BossState.BossFightActive && timeOver() && !botSettings.lvlHerosWhileInBossFight) {
                Log.v(TAG, "level Top Heros");
                bot.executeTask(LevelTopHerosTask.class);
                lastTop6HerosLeveld = System.currentTimeMillis();
                return true;
            }
            else if (timeOver() && botSettings.lvlHerosWhileInBossFight)
            {
                Log.v(TAG, "level Top Heros");
                bot.executeTask(LevelTopHerosTask.class);
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
        return System.currentTimeMillis() - lastTop6HerosLeveld > botSettings.levelTop9HeroTime;
    }

    public void levelTopHerosMaximised(ExecuterTask task) throws InterruptedException, IOException {
        Thread.sleep(500);
        openHeroMenu(task);
        WaitLock.checkForErrorAndWait();
        if (!isMenuMaximized())
            maximiseMenu(task);
        WaitLock.checkForErrorAndWait();
        gotToTopMaximised(task);
        WaitLock.checkForErrorAndWait();
        Thread.sleep(300);
        hero_Y_coordinates = getHero_Y_coordinates();
        for (int i = 0; i< hero_Y_coordinates.length; i++)
        {
            WaitLock.checkForErrorAndWait();
            levelhero(new Point(hero_color_X, hero_Y_coordinates[i]),new Point(hero_click_X, hero_Y_coordinates[i]),task,1);
            WaitLock.checkForErrorAndWait();
        }
        Thread.sleep(300);
        WaitLock.checkForErrorAndWait();
        closeHeroMenu(task);
        WaitLock.checkForErrorAndWait();
        Thread.sleep(200);
        WaitLock.checkForErrorAndWait();

        lastTop6HerosLeveld = System.currentTimeMillis();

    }

    public void levelAllHerosMaximised(ExecuterTask task) throws InterruptedException, IOException {
        Thread.sleep(500);
        openHeroMenu(task);
        WaitLock.checkForErrorAndWait();
        if(!isMenuMaximized())
            maximiseMenu(task);
        WaitLock.checkForErrorAndWait();
        gotToBottomMaximised(task);

        WaitLock.checkForErrorAndWait();
        Thread.sleep(300);
        int loopbreaker = 0;
        while (/*!isMenuTopMaximisedReached() && */breakCondition(loopbreaker,5,task)) {
            hero_Y_coordinates = getHero_Y_coordinates();
            for (int i = 0; i < hero_Y_coordinates.length; i++) {
                WaitLock.checkForErrorAndWait();
                levelhero(new Point(hero_color_X, hero_Y_coordinates[i]), new Point(hero_click_X, hero_Y_coordinates[i]), task, 1);
                WaitLock.checkForErrorAndWait();
            }
            WaitLock.checkForErrorAndWait();
            Thread.sleep(300);
            WaitLock.checkForErrorAndWait();
            swipeUpMaximised();
            loopbreaker++;
        }
        Thread.sleep(300);
        WaitLock.checkForErrorAndWait();
        closeHeroMenu(task);
        WaitLock.checkForErrorAndWait();
        Thread.sleep(300);
        WaitLock.checkForErrorAndWait();

        lastAllHerosLeveld = System.currentTimeMillis();
        lastTop6HerosLeveld = System.currentTimeMillis();

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



    private void tapOnHero(int times,Point point) throws IOException, InterruptedException {
        for (int i = 0; i< times;i++) {
            WaitLock.checkForErrorAndWait();
            doSingelTap(point,"on Hero " + i);
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

    private Integer[] getHero_Y_coordinates()
    {
        List<Integer> list =new ArrayList<>();
        int[] colors = bot.getScreeCapture().getColorFromOneVerticalLine(hero_color_X,50,760);
        int start = 0;
        int end =50;
        boolean waitforstart = true;
        boolean lastColorWasGray = true;
        int t = 0;
        while(t < colors.length)
        {
            if (t == 0 && !ColorUtils.isGray(colors[t])) {
                waitforstart = false;
                lastColorWasGray = false;
                start = 0;
            }
            else if (waitforstart && !ColorUtils.isGray(colors[t]) && lastColorWasGray)
            {
                waitforstart = false;
                lastColorWasGray = false;
                start = t;
            }
            else if (!waitforstart && !lastColorWasGray && ColorUtils.isGray(colors[t]))
            {
                waitforstart = true;
                lastColorWasGray = true;
                int dif = (start +(t - start) /2)+50;
                list.add(dif);
            }
            t++;
        }

        return list.toArray(new Integer[list.size()]);
    }
}
