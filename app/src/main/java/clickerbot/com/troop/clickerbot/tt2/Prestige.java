package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.PrestigeTask;


public class Prestige extends Menu {

    private final String TAG = Prestige.class.getSimpleName();

    private final long runPrestigeCheckActivator = 5000;
    private long lastPrestigeCheck = 0;
    private long timeSinceLastPrestige = 0;
    private Boss boss;

    private final Point prestigeMenuButton = new Point(401,734);
    private final Point prestigeButton = new Point(239,630);
    private final Point prestigeAcceptButton = new Point(326,539);
    private final Point prestigeAcceptButton_color = new Point(326,525);


    private final Point loginInfoButton = new Point(241,596);

    private long randomTimeToPrestige;

    public Prestige(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
    }

    @Override
    void init(ExecuterTask task) {
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

            if ((System.currentTimeMillis() - timeSinceLastPrestige > randomTimeToPrestige && randomTimeToPrestige > 0)
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
            doLongerSingelTap(prestigeMenuButton);
            Thread.sleep(1000);
            while (!checkPrestigAcceptColor() && !Thread.currentThread().isInterrupted()) {
                doLongerSingelTap(prestigeButton);
                Thread.sleep(1000);
            }

            while (checkPrestigAcceptColor()&& !Thread.currentThread().isInterrupted()) {
                doLongerSingelTap(prestigeAcceptButton);
                Thread.sleep(500);
            }
            Thread.sleep(10000);

            while (checkLoginInfoColor()&& !Thread.currentThread().isInterrupted()) {
                doLongerSingelTap(loginInfoButton);
                Thread.sleep(200);
            }

            bot.resetTickCounter();
            setMenuState(MenuState.closed);
            bot.clearExecuterQueue();
            bot.executeTask(InitTask.class);
            lastPrestigeCheck = System.currentTimeMillis();
        }
    }

    private boolean checkPrestigAcceptColor()
    {
        int color = bot.getScreeCapture().getColor(prestigeAcceptButton_color);
        return Color.blue(color)> 200;
    }

    private boolean checkLoginInfoColor()
    {
        int color = bot.getScreeCapture().getColor(loginInfoButton);
        return Color.blue(color)> 200;
    }
}
