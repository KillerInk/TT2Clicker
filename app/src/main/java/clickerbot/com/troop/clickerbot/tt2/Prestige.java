package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.PrestigeTask;

import static clickerbot.com.troop.clickerbot.tt2.TT2Bot.convertMinToMs;

public class Prestige extends Menu {

    private final String TAG = Prestige.class.getSimpleName();

    private final long runPrestigeCheckActivator = convertMinToMs(1);
    private long lastPrestigeCheck = 0;
    private long timeSinceLastPrestige = 0;
    private Boss boss;

    private final Point prestigeMenuButton = new Point(401,734);
    private final Point prestigeButton = new Point(239,630);
    private final Point prestigeAcceptButton = new Point(326,539);

    private long randomTimeToPrestige;

    public Prestige(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
    }

    @Override
    void init() {
        randomTimeToPrestige = botSettings.timeToPrestige + (bot.rand.nextInt(10)*60*1000);
        timeSinceLastPrestige = System.currentTimeMillis();
    }

    public long getTimeSinceLastPrestige(){
        return timeSinceLastPrestige;
    }
    public long getRandomTimeToPrestige(){
        return randomTimeToPrestige;
    }

    @Override
    boolean checkIfRdyToExecute() {
        if(botSettings.autoPrestige && System.currentTimeMillis() - lastPrestigeCheck > runPrestigeCheckActivator) {
            Log.d(TAG, "check if boss failed or time to prestige");

            if ((System.currentTimeMillis() - timeSinceLastPrestige > botSettings.timeToPrestige && randomTimeToPrestige > 0)
                    || (boss.getBossFailedCounter() >= botSettings.bossFailedCount && botSettings.bossFailedCount > 0)) {
                Log.d(TAG, "reason to prestige: bossfailed:" + boss.getBossFailedCounter() + " time toprestige:" + (System.currentTimeMillis() - timeSinceLastPrestige > randomTimeToPrestige));
                bot.clearExecuterQueue();
                bot.executeTask(PrestigeTask.class);

            }
            lastPrestigeCheck = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void doPrestige() throws InterruptedException, IOException {
        Log.d(TAG, "doPrestige:" + botSettings.autoPrestige);
        if (botSettings.autoPrestige) {
            bot.clearExecuterQueue();
            openSwordMasterMenu();
            for (int i =0; i < 20; i++)
                touchInput.swipeVertical(new Point(240, 707), new Point(240, 556));
            Thread.sleep(1000);
            doSingelTap(prestigeMenuButton);
            Thread.sleep(1000);
            doSingelTap(prestigeButton);
            Thread.sleep(3000);
            doSingelTap(prestigeAcceptButton);
            Thread.sleep(300);
            doSingelTap(prestigeAcceptButton);
            Thread.sleep(8000);
            bot.resetTickCounter();
            setMenuState(MenuState.closed);
            bot.clearExecuterQueue();
            bot.executeTask(InitTask.class);
            lastPrestigeCheck = System.currentTimeMillis();
        }
    }
}
