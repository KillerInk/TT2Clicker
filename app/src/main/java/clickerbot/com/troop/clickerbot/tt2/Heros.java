package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTop6HerosTask;

public class Heros extends Menu {

    public static final String TAG = Heros.class.getSimpleName();
    private Boss boss;
    private long lastTop6HerosLeveld = 0;
    private long lastAllHerosLeveld = 0;

    private final int hero_button_red_min = 160;

    public Heros(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell,Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
    }

    @Override
    void init() {
        lastTop6HerosLeveld = 0;
        lastAllHerosLeveld = 0;
    }

    @Override
    boolean rdToExecute() {
        if (botSettings.autoLvlHeros && System.currentTimeMillis() - lastAllHerosLeveld > botSettings.levelAllHeroTime)
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



    public void levelTop6Heros() throws InterruptedException, IOException {
        openHeroMenu();
        //make sure we are on top
        swipeUp(300);
        Thread.sleep(200);
        swipeUp(300);
        Thread.sleep(200);
        swipeUp(300);
        Thread.sleep(200);
        if (canLevelHero(bot.getScreeCapture().getColorFromNextFrame(Coordinates.lvlFIrsHeroButton_color)))
            tapOnHero(tapOnHerosXtimes, Coordinates.lvlFIrsHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlSecondHeroButton_color)))
            tapOnHero(tapOnHerosXtimes, Coordinates.lvlSecondHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlThirdHeroButton_color)))
            tapOnHero(tapOnHerosXtimes, Coordinates.lvlThirdHeroButton_click);
        swipeDown3xHeros();
        if (canLevelHero(bot.getScreeCapture().getColorFromNextFrame(Coordinates.lvlFIrsHeroButton_color)))
            tapOnHero(tapOnHerosXtimes, Coordinates.lvlFIrsHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlSecondHeroButton_color)))
            tapOnHero(tapOnHerosXtimes, Coordinates.lvlSecondHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlThirdHeroButton_color)))
            tapOnHero(tapOnHerosXtimes, Coordinates.lvlThirdHeroButton_click);

        Thread.sleep(200);
        closeHeroMenu();
        Thread.sleep(200);


    }


    private void lvlUpHero(Point point, Point p_color) throws IOException, InterruptedException {

        if ((boss.isActivebossFight() && botSettings.doAutoTap)) {
            Log.d(TAG,"BossFight abort lvlup");
            return;
        }
            Log.v(TAG,"Click on Hero button");
        rootShells[0].doTap(point);
        Thread.sleep(1);
        rootShells[0].doTap(point);
        Thread.sleep(50);
    }

    private boolean canlevelNextHero() throws IOException, InterruptedException {
        swipe(-37,200);
        Thread.sleep(210);

        int color = bot.getScreeCapture().getColorFromNextFrame(Coordinates.lvlThirdHeroButton_color);
        Log.v(TAG,"canlevelNextHero(): " + (Color.red(color) > hero_button_red_min) +" " +ColorUtils.getColorString(color));
        if (Color.red(color) > hero_button_red_min)
            return true;
        else
            return false;
    }


    final int tapOnHerosXtimes = 5;

    public void lvlAllHeros() throws InterruptedException, IOException {
        openHeroMenu();
        swipeUp(-400);
        Thread.sleep(200);
        swipeUp(-400);
        Thread.sleep(200);
        swipeUp(-400);
        Thread.sleep(200);
        swipeUp(-400);
        Thread.sleep(400);
        while (bot.getScreeCapture().getColorFromNextFrame(MenuMaxButtonColorPosition) != MenuMaxButtonBackgroundColor) {
            if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlFIrsHeroButton_color)))
                tapOnHero(tapOnHerosXtimes, Coordinates.lvlFIrsHeroButton_click);
            if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlSecondHeroButton_color)))
                tapOnHero(tapOnHerosXtimes, Coordinates.lvlSecondHeroButton_click);
            if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlThirdHeroButton_color)))
                tapOnHero(tapOnHerosXtimes, Coordinates.lvlThirdHeroButton_click);
            swipeUp3xHeros();
        }
        closeHeroMenu();


    }

    private void tapOnHero(int times,Point point) throws IOException, InterruptedException {
        for (int i = 0; i< times;i++) {
            rootShells[0].doTap(point);
            Thread.sleep(200);
        }
    }

    private boolean canLevelHero(int color)
    {
        if (Color.red(color) > hero_button_red_min)
            return true;
        else
            return false;
    }

}
