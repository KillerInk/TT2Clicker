package clickerbot.com.troop.clickerbot.tt2;

import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.PrestigeTask;

import static clickerbot.com.troop.clickerbot.tt2.TT2Bot.convertMinToMs;

public class Prestige extends Menu {

    private final String TAG = Prestige.class.getSimpleName();

    private final long runPrestigeCheckActivator = convertMinToMs(1);
    private long lastPrestigeCheck = 0;
    //private int prestigeTime = 60;
    private long timeSinceLastPrestige = 0;
    private Boss boss;

    public Prestige(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
    }

    @Override
    void init() {
        timeSinceLastPrestige = System.currentTimeMillis();
    }

    public long getTimeSinceLastPrestige(){
        return timeSinceLastPrestige + botSettings.timeToPrestige;
    }

    @Override
    boolean checkIfRdyToExecute() {
        if(System.currentTimeMillis() - lastPrestigeCheck > runPrestigeCheckActivator) {
            Log.d(TAG, "check if boss failed or time to prestige");

            if (System.currentTimeMillis() - timeSinceLastPrestige > botSettings.timeToPrestige + timeSinceLastPrestige
                    || boss.getBossFailedCounter() >= botSettings.bossFailedCount) {
                Log.d(TAG, "reason to prestige: bossfailed:" + boss.getBossFailedCounter() + " time toprestige:" + (System.currentTimeMillis() - timeSinceLastPrestige > botSettings.timeToPrestige));
                bot.clearExecuterQueue();
                bot.executeTask(PrestigeTask.class);

            }
            lastPrestigeCheck = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void doPrestige() throws InterruptedException, IOException {
        if (botSettings.autoPrestige) {
            bot.clearExecuterQueue();
            openSwordMasterMenu();
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(1000);
            rootShells[0].doTap(Coordinates.prestigeMenuButton);
            Thread.sleep(1000);
            rootShells[0].doTap(Coordinates.prestigeButton);
            Thread.sleep(3000);
            rootShells[0].doTap(Coordinates.prestigeAcceptButton);
            Thread.sleep(300);
            rootShells[0].doTap(Coordinates.prestigeAcceptButton);
            Thread.sleep(5000);
            bot.resetTickCounter();
            bot.executeTask(InitTask.class);
            lastPrestigeCheck = System.currentTimeMillis();
        }
    }
}
