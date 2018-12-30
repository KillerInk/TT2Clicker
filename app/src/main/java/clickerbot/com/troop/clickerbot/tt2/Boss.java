package clickerbot.com.troop.clickerbot.tt2;


import android.graphics.Color;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;

public class Boss extends Menu {
    private final String TAG = Boss.class.getSimpleName();
    private IBot bot;
    private int bossFailedCounter = 0;

    public void setIBot(IBot iBot)
    {
        this.bot = iBot;
    }

    public void checkIfLockedOnBoss() throws IOException, InterruptedException {
        int color = bot.getColor(Coordinates.fightBossButton);
        if (Color.red(color) > 230)
        {
            bossFailedCounter++;
            Log.d(TAG,"bossfailed:" +bossFailedCounter);
            clickOnBossFight();
            Thread.sleep(20);
        }
    }

    public int getBossFailedCounter()
    {
        return bossFailedCounter;
    }

    public void resetBossFailedCounter()
    {
        bossFailedCounter = 0;
    }

    private void clickOnBossFight() throws IOException {
        rootShellClick[0].doTap(Coordinates.fightBossButton);
    }
}
