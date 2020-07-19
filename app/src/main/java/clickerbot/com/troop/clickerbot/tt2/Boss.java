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
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelTopHerosTask;

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
    private int bossFightFailedColor = Color.argb(255,239,114,16);

    private int bossStateNoneFightDetected = 0;


    public void checkIfActiveBossFight()
    {
        if (WaitLock.fairyWindowDetected.get()
                || WaitLock.sceneTransition.get()
                || WaitLock.errorDetected.get()
                || WaitLock.clanquest.get()
                || Menu.getMenuState() != MenuState.closed)
            return;
        int color = bot.getScreeCapture().getColor(Coordinates.fightBossButton_Color);
        if (ColorUtils.colorIsInRange(color,bossFightActiveColor,5) )
        {
            if (bossState == BossState.NoneFight && bossFailedCounter.get() > 0) {
                bossFailedCounter.getAndDecrement();
                Log.d(TAG,"Reduce boss failed counter" + bossFailedCounter.get() );
            }
            setBossState(BossState.BossFightActive);

            //Log.i(TAG, "bossFightActiveColor Wait for next fail: true");

        }
        else if(ColorUtils.colorIsInRange(color,bossFightFailedColor ,5) )
        {
            if (bossState == BossState.BossFightActive && bossFailedCounter.get() < botSettings.bossFailedCount)
                    bossFailedCounter.getAndIncrement();
            setBossState(BossState.BossFightFailed);
            Log.d(TAG,"Increase boss failed counter ,level up heros and swordmaster " + bossFailedCounter.get() );
            if (!bot.containsTask(ClickOnBossFightTask.class)) {
                //Log.i(TAG, "bossFightFailedColor Wait for next fail: false");
                if (botSettings.autoLvlSwordMaster) {
                    bot.executeTask(LevelSwordMasterTask.class);
                }
                if (botSettings.autoLvlHeros) {
                    bot.executeTask(LevelTopHerosTask.class);
                }
                bot.executeTask(ClickOnBossFightTask.class);
            }

        }
        else if (!ColorUtils.colorIsInRange(color,bossFightActiveColor,5 )
                && !ColorUtils.colorIsInRange(color,bossFightFailedColor,5)
                && color != 0
                && bossState != BossState.NoneFight
                && !WaitLock.fairyWindowDetected.get()
                && !WaitLock.sceneTransition.get()
                && !WaitLock.errorDetected.get()
                && Menu.getMenuState() == MenuState.closed
        )
        {


            bossStateNoneFightDetected++;
            if (bossStateNoneFightDetected > 8) {
                Log.d(TAG,"Boss state none fight");
                setBossState(BossState.NoneFight);
                bossStateNoneFightDetected = 0;
            }
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
        doLongerSingelTap(Coordinates.fightBossButton,"on bossfight");
        Thread.sleep(500);
    }
}
