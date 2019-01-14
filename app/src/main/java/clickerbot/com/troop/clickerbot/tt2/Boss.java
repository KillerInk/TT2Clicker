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


    public Boss(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init() {
        bossState = BossState.NoneFight;
    }

    @Override
    boolean checkIfRdyToExecute() {
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
            if (bossState == BossState.NoneFight) {
                if (bossFailedCounter > 0)
                    bossFailedCounter--;
                setBossState(BossState.BossFightActive);
                waitForNextFail = true;
                Log.i(TAG, "bossFightActiveColor Wait for next fail: true");
            }
        }
        else if(color == bossFightFailedColor)
        {
            if (bossState == BossState.BossFightActive) {
                if (bossFailedCounter < botSettings.bossFailedCount)
                    bossFailedCounter++;
                setBossState(BossState.BossFightFailed);

                if (waitForNextFail) {
                    Log.i(TAG, "bossFightFailedColor Wait for next fail: false");
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

        }
        else if (color != bossFightActiveColor && color != bossFightFailedColor && color != 0 && bossState != BossState.NoneFight){
            setBossState(BossState.NoneFight);
        }
        Log.v(TAG,"isActiveBossFight: "+ bossState.toString() + " " + ColorUtils.getColorString(color));

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
        Thread.sleep(100);
        rootShells[0].doTap(Coordinates.fightBossButton);
        Thread.sleep(100);
        rootShells[0].doTap(Coordinates.fightBossButton);
        Thread.sleep(100);
        rootShells[0].doTap(Coordinates.fightBossButton);
        Thread.sleep(500);
        /*int color = bot.getScreeCapture().getColor(Coordinates.fightBossButton_Color);
        if(color == bossFightFailedColor)
        {
            rootShells[0].doTap(Coordinates.fightBossButton);
            Thread.sleep(100);
            Thread.sleep(300);
        }*/
        waitForNextFail = true;
    }
}
