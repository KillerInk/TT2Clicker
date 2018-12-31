package clickerbot.com.troop.clickerbot.tt2;


import android.graphics.Color;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;

// boss icon boss fight active -12826035 60 74 77
//boss icons boss fight failed -1085168 239 113 16

public class Boss extends Menu {
    private final String TAG = Boss.class.getSimpleName();
    private int bossFailedCounter = 0;
    private boolean isActiveBossFight = false;


    public Boss(IBot bot, BotSettings botSettings) {
        super(bot, botSettings);
    }
    private int bossFightActiveColor = Color.argb(255,69,85,89);

    public boolean isActiveBossFight() {
        return isActiveBossFight;
    }

    public void checkIfActiveBossFight()
    {
        int color = bot.getColor(Coordinates.fightBossButton_Color);
        if (color ==  bossFightActiveColor)
            isActiveBossFight = true;
        else //if (Color.red(color) > 230)
        {
            isActiveBossFight = false;
        }
        Log.d(TAG,"isActiveBossFight:"+ isActiveBossFight + " " + color + " " +Color.red(color) + " "+Color.green(color) + " " +Color.blue(color));

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
        else
            Log.d(TAG, "not stucked on boss");
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
