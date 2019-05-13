package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.AutoLevelBOSTask;
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
    private final Point prestigeButton_color_pos = new Point(292,617);
    private final Point prestigeAcceptButton = new Point(326,539);
    private final Point prestigeAcceptButton_color = new Point(326,525);
    private Random random;


    private final Point loginInfoButton = new Point(241,596);

    private long randomTimeToPrestige;

    public Prestige(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, Boss boss) {
        super(ibot, botSettings, rootShell);
        this.boss = boss;
        random = new Random();
    }

    @Override
    void init(ExecuterTask task) {

        randomTimeToPrestige = botSettings.timeToPrestige;
        if (botSettings.randomTimeToPrestige > 0)
            randomTimeToPrestige += (bot.rand.nextInt((int)botSettings.randomTimeToPrestige)*60*1000);
        setTimeSinceLastPrestige(System.currentTimeMillis());
        setLastPrestigeCheck(System.currentTimeMillis());
    }

    public synchronized long getTimeSinceLastPrestige(){
        return timeSinceLastPrestige;
    }
    private synchronized void setTimeSinceLastPrestige(long time)
    {
        this.timeSinceLastPrestige = time;
    }
    public long getRandomTimeToPrestige(){
        return randomTimeToPrestige;
    }

    private synchronized long getLastPrestigeCheck()
    {
        return lastPrestigeCheck;
    }

    private synchronized void setLastPrestigeCheck(long time)
    {
        this.lastPrestigeCheck = time;
    }

    @Override
    boolean checkIfRdyToExecute() {
        if(botSettings.autoPrestige && System.currentTimeMillis() - getLastPrestigeCheck() > runPrestigeCheckActivator && !WaitLock.prestige.get()) {
            Log.d(TAG, "check if boss failed or time to prestige");

            if ((System.currentTimeMillis() - getTimeSinceLastPrestige() > randomTimeToPrestige && randomTimeToPrestige > 0)
                    || (boss.getBossFailedCounter() >= botSettings.bossFailedCount && botSettings.bossFailedCount > 0)) {
                Log.d(TAG, "reason to prestige: bossfailed:" + boss.getBossFailedCounter() + " time toprestige:" + (System.currentTimeMillis() - getTimeSinceLastPrestige() > randomTimeToPrestige));
                bot.clearExecuterQueue();
                if (!WaitLock.prestige.get())
                    bot.executeTask(PrestigeTask.class);

            }
            setLastPrestigeCheck(System.currentTimeMillis());
            return true;
        }
        return false;
    }

    public void doPrestige(ExecuterTask task) throws InterruptedException, IOException {
        Log.d(TAG, "doPrestige:" + botSettings.autoPrestige);
        if (botSettings.autoPrestige) {
            WaitLock.prestige.set(true);
            bot.clearExecuterQueue();
            openSwordMasterMenu(task);
            if (!isMenuMaximized())
                maximiseMenu(task);
            for (int i =0; i < 5; i++)
                swipeDownMaximised();

            int loopbreaker = 0;

            while(!checkPrestigButton() && !Thread.currentThread().isInterrupted() && !task.cancelTask && loopbreaker  < 10) {
                loopbreaker++;
                doLongerSingelTap(new Point(prestigeMenuButton.x -3 +random.nextInt(6),prestigeMenuButton.y -3 +random.nextInt(6)),"on Prestige Menu Button");
                Thread.sleep(600);
            }
            /*if (loopbreaker == 19)
            {
                Log.d(TAG,"Prestige menu button click failed.");
                closeMenu();
                bot.clearExecuterQueue();
                bot.executeTask(PrestigeTask.class);
                WaitLock.prestige.set(false);
                return;
            }*/
            loopbreaker = 0;
            while (!checkPrestigAcceptColor() && !Thread.currentThread().isInterrupted()&& !task.cancelTask && loopbreaker  < 5) {
                loopbreaker++;
                doLongerSingelTap(prestigeButton,"on Prestige Button");
                Thread.sleep(600);
            }
            loopbreaker = 0;
            while (checkPrestigAcceptColor()&& !Thread.currentThread().isInterrupted()&& !task.cancelTask && loopbreaker  < 5) {
                loopbreaker++;
                Thread.sleep(600);
                doLongerSingelTap(prestigeAcceptButton,  "on Prestige accept Button");
                Thread.sleep(600);
                if (!checkPrestigAcceptColor())
                    loopbreaker = 5;
            }
            Thread.sleep(15000);
            loopbreaker = 0;
            while (checkLoginInfoColor()&& !Thread.currentThread().isInterrupted()&& !task.cancelTask && loopbreaker  < 5) {
                loopbreaker++;
                doLongerSingelTap(loginInfoButton,"close loginInfo");
                Thread.sleep(400);
            }


            bot.resetTickCounter();
            setMenuState(MenuState.closed);
            bot.clearExecuterQueue();
            setLastPrestigeCheck(System.currentTimeMillis());
            init(task);
            boss.resetBossFailedCounter();
            if (botSettings.autoLvlBos)
                bot.executeTask(AutoLevelBOSTask.class);
            bot.executeTask(InitTask.class);
            WaitLock.prestige.set(false);
        }
    }

    private boolean checkPrestigAcceptColor()
    {
        int color = bot.getScreeCapture().getColor(prestigeAcceptButton_color);
        return Color.blue(color)> 200;
    }

    private boolean checkPrestigButton()
    {
        int color = bot.getScreeCapture().getColor(prestigeButton_color_pos);
        return Color.blue(color)> 200;
    }

    private boolean checkLoginInfoColor()
    {
        int color = bot.getScreeCapture().getColor(loginInfoButton);
        return Color.blue(color)> 200;
    }
}
