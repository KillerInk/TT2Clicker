package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.ClickOnBossFightTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTop6HerosTask;

public class Heros extends Menu {

    public static final String TAG = Heros.class.getSimpleName();
    private Boss boss;
    private long lastTop6HerosLeveld = 0;
    private long lastAllHerosLeveld = 0;

    private final int hero_button_red_min = 160;

    public Heros(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
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

    public void levelTop6Heros() throws InterruptedException, IOException {
        openHeroMenu();
        //make sure we are on top
        swipeUp(300);
        Thread.sleep(200);
        swipeUp(300);
        Thread.sleep(200);
        swipeUp(300);
        Thread.sleep(200);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlFIrsHeroButton_color)))
            tapOnHero(1, Coordinates.lvlFIrsHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlSecondHeroButton_color)))
            tapOnHero(1, Coordinates.lvlSecondHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlThirdHeroButton_color)))
            tapOnHero(1, Coordinates.lvlThirdHeroButton_click);
        swipeDown3xHeros();
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlFIrsHeroButton_color)))
            tapOnHero(1, Coordinates.lvlFIrsHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlSecondHeroButton_color)))
            tapOnHero(1, Coordinates.lvlSecondHeroButton_click);
        if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlThirdHeroButton_color)))
            tapOnHero(1, Coordinates.lvlThirdHeroButton_click);

        Thread.sleep(200);
        closeHeroMenu();
        Thread.sleep(200);

        lastTop6HerosLeveld = System.currentTimeMillis();

       /* if (boss.getBossState() == Boss.BossState.BossFightFailed)
            bot.executeTask(ClickOnBossFightTask.class);*/

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
        while (bot.getScreeCapture().getColor(MenuMaxButtonColorPosition) != MenuMaxButtonBackgroundColor) {
            Thread.sleep(200);
            if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlFIrsHeroButton_color)))
                tapOnHero(tapOnHerosXtimes, Coordinates.lvlFIrsHeroButton_click);
            if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlSecondHeroButton_color)))
                tapOnHero(tapOnHerosXtimes, Coordinates.lvlSecondHeroButton_click);
            if (canLevelHero(bot.getScreeCapture().getColor(Coordinates.lvlThirdHeroButton_color)))
                tapOnHero(tapOnHerosXtimes, Coordinates.lvlThirdHeroButton_click);
            swipeUp3xHeros();
        }
        closeHeroMenu();

        lastAllHerosLeveld = System.currentTimeMillis();
        lastTop6HerosLeveld = System.currentTimeMillis();

        /*if (boss.getBossState() == Boss.BossState.BossFightFailed)
            bot.executeTask(ClickOnBossFightTask.class);*/
    }

    private void tapOnHero(int times,Point point) throws IOException, InterruptedException {
        for (int i = 0; i< times;i++) {
            doSingelTap(point);
            Thread.sleep(200);
        }
    }

    private boolean canLevelHero(int color)
    {
        boolean canlvl = false;
        if (Color.red(color) > hero_button_red_min)
            canlvl = true;
        Log.v(TAG,"canLevelHero: " + canlvl + " " +ColorUtils.getColorString(color));
        return canlvl;
    }

}
