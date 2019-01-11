package clickerbot.com.troop.clickerbot.tt2;


import android.graphics.Color;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.tt2.tasks.ClickOnBossFightTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTop6HerosTask;

// boss icon boss fight active -12826035 60 74 77
//boss icons boss fight failed -1085168 239 113 16

public class Boss extends Menu {
    private final String TAG = Boss.class.getSimpleName();
    private int bossFailedCounter = 0;

    private BossState bossState = BossState.NoneFight;

    private final int bossFightClickCheck = 10000;
    private int bossFightClickCheckLast=0;

    public Boss(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init() {
        bossFightClickCheckLast = 0;
    }

    @Override
    boolean checkIfRdyToExecute() {
        /*if (System.currentTimeMillis() - bossFightClickCheckLast > bossFightClickCheck)
        {
            if (bossState == BossState.BossFightFailed)
            {
                if (botSettings.autoLvlSwordMaster)
                    bot.execute(sw);
            }

        }*/
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

    private boolean waitForNextFail = true;

    public void checkIfActiveBossFight()
    {
        int color = bot.getScreeCapture().getColor(Coordinates.fightBossButton_Color);
        if (color ==  bossFightActiveColor)
        {
            if (bossState == BossState.NoneFight)
                if (bossFailedCounter > 0)
                    bossFailedCounter--;
            setBossState(BossState.BossFightActive);
            waitForNextFail = true;

        }
        else if(color == bossFightFailedColor)
        {
            if (bossState == BossState.BossFightActive)
                if (bossFailedCounter < botSettings.bossFailedCount)
                    bossFailedCounter++;
            setBossState(BossState.BossFightFailed);

            if (waitForNextFail) {
                waitForNextFail = false;
                int pos = 0;
                if (botSettings.autoLvlSwordMaster) {
                    bot.putTaskAtPos(LevelSwordMasterTask.class, pos++);
                }
                if (botSettings.autoLvlHeros) {
                    bot.putTaskAtPos(LevelTop6HerosTask.class, pos++);
                }
                bot.putTaskAtPos(ClickOnBossFightTask.class, pos++);
            }

        }
        else if (color != bossFightActiveColor && color != bossFightFailedColor && color != 0){
            setBossState(BossState.NoneFight);
        }
        Log.v(TAG,"isActiveBossFight: "+ bossState.toString() + " " + ColorUtils.getColorString(color));

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

    public void clickOnBossFight() throws IOException, InterruptedException {
        rootShells[0].doTap(Coordinates.fightBossButton);
        Thread.sleep(100);
        Thread.sleep(300);
        int color = bot.getScreeCapture().getColorFromNextFrame(Coordinates.fightBossButton_Color);
        if(color == bossFightFailedColor)
        {
            rootShells[0].doTap(Coordinates.fightBossButton);
            Thread.sleep(100);
            Thread.sleep(300);
        }

    }
}
