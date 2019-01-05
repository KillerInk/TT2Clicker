package clickerbot.com.troop.clickerbot.tt2;


import android.graphics.Color;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.ScreenCapture;

// boss icon boss fight active -12826035 60 74 77
//boss icons boss fight failed -1085168 239 113 16

public class Boss extends Menu {
    private final boolean debug = false;
    private final String TAG = Boss.class.getSimpleName();
    private int bossFailedCounter = 0;

    private BossState bossState = BossState.NoneFight;

    public Boss(IBot ibot, BotSettings botSettings, RootShell[] rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init() {

    }

    @Override
    boolean rdToExecute() {

        return false;
    }

    enum BossState {
        BossFightActive,
        BossFightFailed,
        NoneFight
    }

    public synchronized void setBossState(BossState state)
    {
        this.bossState = state;
    }

    public synchronized BossState getBossState()
    {
        return bossState;
    }


    private int bossFightActiveColor = Color.argb(255,69,85,89);
    private int bossFightFailedColor = Color.argb(255,239,113,16);


    public void checkIfActiveBossFight()
    {
        int color = bot.getScreeCapture().getColor(Coordinates.fightBossButton_Color);
        if (color ==  bossFightActiveColor)
        {
            if (bossState == BossState.NoneFight)
                if (bossFailedCounter > 0)
                    bossFailedCounter--;
            setBossState(BossState.BossFightActive);

        }
        else if(color == bossFightFailedColor)
        {
            if (bossState == BossState.BossFightActive)
                if (bossFailedCounter < botSettings.bossFailedCount)
                    bossFailedCounter++;
            setBossState(BossState.BossFightFailed);

        }
        else if (color != bossFightActiveColor && color != bossFightFailedColor && color != 0){
            setBossState(BossState.NoneFight);
        }
        if (debug)
            Log.d(TAG,"isActiveBossFight: "+ bossState.toString() + " " + ScreenCapture.getColorString(color));

    }

    public boolean isActivebossFight()
    {
        return getBossState() == BossState.BossFightActive;
    }



    public void checkIfLockedOnBoss() throws IOException, InterruptedException {
        int color = bot.getScreeCapture().getColor(Coordinates.fightBossButton_Color);
        if (Color.red(color) > 230)
        {
            bossFailedCounter++;
            if (debug)
                Log.d(TAG,"bossfailed:" +bossFailedCounter);
            clickOnBossFight();
            Thread.sleep(20);
        }
        else if (debug)
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
        rootShells[0].doTap(Coordinates.fightBossButton);
    }
}
