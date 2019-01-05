package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelHerosTask;

public class Heros extends Menu {

    public static final String TAG = Heros.class.getSimpleName();
    private Boss boss;
    private final int runHeroActivator = 60000;//ms
    private long lastHerossActivated = 0;
    private LevelHerosTask levelHerosTask;

    public Heros(IBot ibot, BotSettings botSettings, RootShell[] rootShell,Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
        levelHerosTask = new LevelHerosTask(this);
    }

    @Override
    void init() {
        lastHerossActivated = 0;
    }

    @Override
    boolean rdToExecute() {
        if (boss.getBossState() != Boss.BossState.BossFightActive
                && botSettings.autoLvlHeros && timeOver()) {

                Log.d(TAG, "level Heros");
                bot.execute(levelHerosTask);
                lastHerossActivated = System.currentTimeMillis();
                return true;

        }
        else {Log.d(TAG, "not rdy to execute " + boss.getBossState() + "timeover:"+timeOver());}

        return false;
    }


    private boolean timeOver()
    {
        return System.currentTimeMillis() - lastHerossActivated > runHeroActivator;
    }



    public void levelHeros() throws InterruptedException, IOException {
        openHeroMenu();
        //make sure we are on top
        swipeUp(200);
        Thread.sleep(200);
        swipeUp(200);
        Thread.sleep(200);
        swipeUp(200);
        Thread.sleep(500);

        lvlUpHero(Coordinates.lvlFIrsHeroButton_click, Coordinates.lvlFIrsHeroButton_color);
        lvlUpHero(Coordinates.lvlSecondHeroButton_click, Coordinates.lvlSecondHeroButton_color);
        lvlUpHero(Coordinates.lvlThirdHeroButton_click, Coordinates.lvlThirdHeroButton_color);

        while (canlevelNextHero() && !boss.isActivebossFight())
            lvlUpHero(Coordinates.lvlThirdHeroButton_click, Coordinates.lvlThirdHeroButton_color);

        Thread.sleep(200);
        closeMenu();
        Thread.sleep(200);


    }


    private void lvlUpHero(Point point, Point p_color) throws IOException, InterruptedException {

        if (boss.isActivebossFight())
            return;
        int color = bot.getScreeCapture().getColorFromNextFrame(p_color);
        Log.d(TAG, "lvl up Hero color:"+ color +" r:" + Color.red(color) + " g:"+Color.green(color) + " b:"+ Color.blue(color));
        if (Color.red(color) > 170) {
            Log.d(TAG,"Click on Hero button");
            rootShells[0].doTap(point);
            Thread.sleep(1);
            rootShells[0].doTap(point);
            Thread.sleep(50);
        }
    }

    private boolean canlevelNextHero() throws IOException, InterruptedException {
        swipeUp(-37);
        Thread.sleep(200);
        //bot.dumpScreen();
        int color = bot.getScreeCapture().getColorFromNextFrame(Coordinates.lvlThirdHeroButton_click);
        if (Color.red(color) > 170)
            return true;
        else
            return false;
    }
}
