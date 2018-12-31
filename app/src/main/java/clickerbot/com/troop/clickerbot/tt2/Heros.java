package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;

public class Heros extends Menu {

    private static final String TAG = Heros.class.getSimpleName();

    public Heros(IBot bot, BotSettings botSettings) {
        super(bot, botSettings);
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

        while (canlevelNextHero())
            lvlUpHero(Coordinates.lvlThirdHeroButton_click, Coordinates.lvlThirdHeroButton_color);

        Thread.sleep(200);
        closeMenu();

    }


    private void lvlUpHero(Point point, Point p_color) throws IOException, InterruptedException {
        //bot.dumpScreen();
        synchronized (bot)
        {
            bot.wait();
        }
        int color = bot.getColor(p_color);
        Log.d(TAG, "lvl up Hero color:"+ color +" r:" + Color.red(color) + " g:"+Color.green(color) + " b:"+ Color.blue(color));
        if (Color.red(color) > 170) {
            Log.d(TAG,"Click on Hero button");
            rootShellClick[0].doTap(point);
            Thread.sleep(1);
            rootShellClick[0].doTap(point);
            Thread.sleep(50);
        }
    }

    private boolean canlevelNextHero() throws IOException, InterruptedException {
        swipeUp(-37);
        Thread.sleep(500);
        //bot.dumpScreen();
        synchronized (bot)
        {
            bot.wait();
        }
        int color = bot.getColor(Coordinates.lvlThirdHeroButton_click);
        if (Color.red(color) > 170)
            return true;
        else
            return false;
    }
}
