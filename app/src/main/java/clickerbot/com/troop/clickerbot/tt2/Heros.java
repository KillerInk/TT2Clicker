package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.ScreenCapture;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelHerosTask;

public class Heros extends Menu {

    public static final String TAG = Heros.class.getSimpleName();
    private Boss boss;
    private final int runHeroActivator = 60000;//ms
    private long lastHerossActivated = 0;
    private LevelHerosTask levelHerosTask;

    private final int hero_button_red_min = 160;

    public Heros(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell,Boss boss) {
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
                Log.v(TAG, "level Heros");
                bot.execute(levelHerosTask);
                lastHerossActivated = System.currentTimeMillis();
                return true;

        }
       /* else if (debug) {
            Log.d(TAG, "not rdy to execute " + boss.getBossState() + " timeover: "+timeOver());}*/
        return false;
    }


    private boolean timeOver()
    {
        return System.currentTimeMillis() - lastHerossActivated > runHeroActivator;
    }



    public void levelHeros() throws InterruptedException, IOException {
        openHeroMenu();
        //make sure we are on top
        swipeUp(300);
        Thread.sleep(200);
        swipeUp(300);
        Thread.sleep(200);
        swipeUp(300);
        Thread.sleep(200);
        lvlUpHero(Coordinates.lvlFIrsHeroButton_click, Coordinates.lvlFIrsHeroButton_color);
        lvlUpHero(Coordinates.lvlSecondHeroButton_click, Coordinates.lvlSecondHeroButton_color);
        lvlUpHero(Coordinates.lvlThirdHeroButton_click, Coordinates.lvlThirdHeroButton_color);

        while (!(boss.isActivebossFight() && botSettings.doAutoTap) && !levelHerosTask.cancelTask && canlevelNextHero()){
            lvlUpHero(Coordinates.lvlThirdHeroButton_click, Coordinates.lvlThirdHeroButton_color);
        }
        //lvlUpHero(Coordinates.lvlThirdHeroButton_click, Coordinates.lvlThirdHeroButton_color);

        Thread.sleep(200);
        closeMenu();
        Thread.sleep(200);


    }


    private void lvlUpHero(Point point, Point p_color) throws IOException, InterruptedException {

        if ((boss.isActivebossFight() && botSettings.doAutoTap) || levelHerosTask.cancelTask) {
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

        int color = bot.getScreeCapture().getColorFromNextFrame(Coordinates.lvlThirdHeroButton_click);
        Log.v(TAG,"canlevelNextHero(): " + (Color.red(color) > hero_button_red_min) +" " +ColorUtils.getColorString(color));
        if (Color.red(color) > hero_button_red_min)
            return true;
        else
            return false;
    }
}
