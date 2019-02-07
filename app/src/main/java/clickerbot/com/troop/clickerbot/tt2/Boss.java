package clickerbot.com.troop.clickerbot.tt2;


import android.graphics.Color;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.ClickOnBossFightTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTop6HerosTask;

// boss icon boss fight active -12826035 60 74 77
//boss icons boss fight failed -1085168 239 113 16

public class Boss extends Menu {
    private final String TAG = Boss.class.getSimpleName();
    private AtomicInteger bossFailedCounter = new AtomicInteger();

    private BossState bossState = BossState.NoneFight;


    public Boss(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init(ExecuterTask task) {
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


    public void checkIfActiveBossFight()
    {
        if (WaitLock.fairyWindowDetected.get() || WaitLock.sceneTransition.get() || WaitLock.errorDetected.get())
            return;
        int color = bot.getScreeCapture().getColor(Coordinates.fightBossButton_Color);
        if (color ==  bossFightActiveColor)
        {
            if (bossState == BossState.NoneFight && bossFailedCounter.get() > 0)
                    bossFailedCounter.getAndDecrement();
            setBossState(BossState.BossFightActive);
            //Log.i(TAG, "bossFightActiveColor Wait for next fail: true");

        }
        else if(color == bossFightFailedColor)
        {
            if (bossState == BossState.BossFightActive && bossFailedCounter.get() < botSettings.bossFailedCount)
                    bossFailedCounter.getAndIncrement();
            setBossState(BossState.BossFightFailed);

            if (!bot.containsTask(ClickOnBossFightTask.class)) {
                //Log.i(TAG, "bossFightFailedColor Wait for next fail: false");
                if (botSettings.autoLvlSwordMaster) {
                    bot.executeTask(LevelSwordMasterTask.class);
                }
                if (botSettings.autoLvlHeros) {
                    bot.executeTask(LevelTop6HerosTask.class);
                }
                bot.executeTask(ClickOnBossFightTask.class);
            }

        }
        else if (color != bossFightActiveColor
                && color != bossFightFailedColor
                && color != 0
                && bossState != BossState.NoneFight
                && !WaitLock.fairyWindowDetected.get()
                && !WaitLock.sceneTransition.get()
                && !WaitLock.errorDetected.get())
        {
            setBossState(BossState.NoneFight);
        }
        //Log.v(TAG,"isActiveBossFight: "+ bossState.toString() + " " + ColorUtils.getColorString(color));

    }

    public int getBossFailedCounter()
    {
        return bossFailedCounter.get();
    }

    public void resetBossFailedCounter()
    {
        bossFailedCounter.set(0);
    }

    public void clickOnBossFight() throws IOException, InterruptedException {
        if (bossState == BossState.BossFightActive)
            return;
        doLongerSingelTap(Coordinates.fightBossButton);
        Thread.sleep(500);
        /*int color = bot.getScreeCapture().getColor(Coordinates.fightBossButton_Color);
        if(color == bossFightFailedColor)
        {
            rootShells[0].doTap(Coordinates.fightBossButton);
            Thread.sleep(100);
            Thread.sleep(300);
        }*/
    }
}
