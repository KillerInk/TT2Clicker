package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;

public class Heros extends Menu {

    private static final String TAG = Heros.class.getSimpleName();
    private IBot bot;

    public void setIBot(IBot iBot)
    {
        this.bot = iBot;
    }

    public void levelHeros() throws InterruptedException, IOException {
        openHeroMenu();
        //make sure we are on top
        swipeUp(200);
        Thread.sleep(200);
        swipeUp(200);
        Thread.sleep(200);
        swipeUp(200);
        Thread.sleep(1000);
        //swip down to first hero from top
        swipeUp(-28);
        Thread.sleep(1000);
        lvlUpHero();
        for (int i =0; i< 3; i++)
        {
            swipeUp(-37);
            Thread.sleep(1000);
            lvlUpHero();
        }
        bot.dumpScreen();
        int color = bot.getColor(Coordinates.lvlNextHeroButton);
        if (Color.red(color) > 100) {
            for (int i =0; i< 3; i++)
            {
                swipeUp(-37);
                Thread.sleep(1000);
                lvlUpHero();
            }
        }
        bot.dumpScreen();
        color = bot.getColor(Coordinates.lvlNextHeroButton);
        if (Color.red(color) > 100) {
            for (int i =0; i< 3; i++)
            {
                swipeUp(-37);
                Thread.sleep(1000);
                lvlUpHero();
            }
        }
        Thread.sleep(200);
        closeMenu();

    }

    private void lvlUpHero() throws IOException, InterruptedException {
        bot.dumpScreen();
        int color = bot.getColor(Coordinates.lvlHeroButton);
        Log.d(TAG, "lvl up Hero color:"+ color +" r:" + Color.red(color) + " g:"+Color.green(color) + " b:"+ Color.blue(color));
        if (Color.red(color) > 100) {
            Log.d(TAG,"Click on Hero button");
            rootShellClick[0].doTap(Coordinates.lvlHeroButton);
            Thread.sleep(50);
        }
    }
}
