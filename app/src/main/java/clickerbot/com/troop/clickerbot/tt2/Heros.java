package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.Executer;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTop6HerosTask;

public class Heros extends Menu {

    public static final String TAG = Heros.class.getSimpleName();
    private Boss boss;
    private long lastTop6HerosLeveld = 0;
    private long lastAllHerosLeveld = 0;
    private ArrayList<Integer> goodHeroLevelUpColors;



    public Heros(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
        goodHeroLevelUpColors = getColorsToLvlHero();

    }

    @Override
    void init() {
        lastTop6HerosLeveld = 0;
        lastAllHerosLeveld = 0;
    }

    @Override
    boolean checkIfRdyToExecute() {
        if (botSettings.autoLvlHeros && System.currentTimeMillis() - lastAllHerosLeveld > botSettings.levelAllHeroTime && boss.getBossState() != Boss.BossState.BossFightActive)
        {
            Log.v(TAG, "level ALL Heros");
            bot.executeTask(LevelAllHerosTask.class);
            lastAllHerosLeveld = System.currentTimeMillis();
            lastTop6HerosLeveld = System.currentTimeMillis();
        }
        if (boss.getBossState() != Boss.BossState.BossFightActive
                && botSettings.autoLvlHeros && timeOver()) {
                Log.v(TAG, "level Top6 Heros");
                bot.executeTask(LevelTop6HerosTask.class);
                lastTop6HerosLeveld = System.currentTimeMillis();
                return true;
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
        //make sure we are on top
        while (!isMenuTopReached() && !task.cancelTask && Menu.MenuOpen) {
            touchInput.swipeVertical(Coordinates.lvlFIrsHeroButton_click, Coordinates.lvlThirdHeroButton_click);
            Thread.sleep(300);
        }
        Thread.sleep(500);
        level3heros(task);
        touchInput.swipeVertical(new Point(240, 707), new Point(240, 556));
        touchInput.swipeVertical(new Point(240, 707), new Point(240, 556));
        level3heros(task);

        Thread.sleep(200);
        closeHeroMenu();
        Thread.sleep(200);

        lastTop6HerosLeveld = System.currentTimeMillis();
    }

    public void lvlAllHeros(ExecuterTask task) throws InterruptedException, IOException {
        openHeroMenu();
        for (int i=0; i< 25; i++) {
            if (task.cancelTask)
                return;
            touchInput.swipeVertical(new Point(240, 707), new Point(240, 556));
        }
        if (!task.cancelTask)
            Thread.sleep(1500);
        if (!task.cancelTask)
            Thread.sleep(100);
        int loopbreaker = 0;
        while (!isMenuTopReached() && breakCondition(loopbreaker,25,task)) {
            loopbreaker++;
            Thread.sleep(200);
            level3heros(task);
            touchInput.swipeVertical(new Point(240,556),new Point(240,707));
            Thread.sleep(400);
        }
        closeHeroMenu();

        lastAllHerosLeveld = System.currentTimeMillis();
        lastTop6HerosLeveld = System.currentTimeMillis();
    }


    private void level3heros(ExecuterTask task) throws IOException, InterruptedException {
        levelhero(Coordinates.lvlFIrsHeroButton_color,Coordinates.lvlFIrsHeroButton_click,task);
        levelhero(Coordinates.lvlSecondHeroButton_color,Coordinates.lvlSecondHeroButton_click,task);
        levelhero(Coordinates.lvlThirdHeroButton_color,Coordinates.lvlThirdHeroButton_click,task);
    }

    private void levelhero(Point color, Point click, ExecuterTask task) throws IOException, InterruptedException {
        int loopbreaker = 0;
        while (level3HeroBreakConditions(color,loopbreaker,12,task)) {
            loopbreaker++;
            tapOnHero(1, click);

            if (!task.cancelTask)
                Thread.sleep(500); //TODO this time may can get lowered, or removed with a wait for a new frame.
        }
    }

    private boolean level3HeroBreakConditions(Point color, int loopbreaker, int loopbreakerMax, ExecuterTask task)
    {
        boolean canlevel = breakCondition(loopbreaker,loopbreakerMax,task);
        if(canlevel) {
            canlevel = canLevelHero(bot.getScreeCapture().getColor(color), 1);
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
            canlevel = Menu.MenuOpen == true;
            Log.d(TAG, "MenuOPen:" + Menu.MenuOpen);
        }
        return canlevel;
    }

    private void tapOnHero(int times,Point point) throws IOException, InterruptedException {
        for (int i = 0; i< times;i++) {
            doSingelTap(point);
            Thread.sleep(200);
        }
    }

    private boolean canLevelHero(int color, int heroid)
    {
        boolean canlvl = false;
        if (goodHeroLevelUpColors.contains(color))
            canlvl = true;
        Log.v(TAG,"canLevelHero "+heroid+": " + canlvl + " " +ColorUtils.getColorString(color));
        return canlvl;
    }

    /**
     * colors from the Hero Button to lvl and unlock hero skills
     * colors got dumpend from R.drawable.hero_button_skill_unlock and R.drawable.hero_button_level_hero at X position 333
     * @return a list with good colors to lvl up
     */
    private ArrayList<Integer> getColorsToLvlHero()
    {
        ArrayList<Integer> arr = new ArrayList();
        arr.add(-867322);
        arr.add(-13052);
        arr.add(-933114);
        arr.add(-932859);
        arr.add(-78588);
        arr.add(-998650);
        arr.add(-1797100);
        arr.add(-1008877);
        arr.add(-943084);
        arr.add(-943341);
        arr.add(-877805);
        arr.add(-943340);
        arr.add(-1665772);
        arr.add(-272567);
        arr.add(-9399);
        arr.add(-272311);
        return arr;
    }



}
