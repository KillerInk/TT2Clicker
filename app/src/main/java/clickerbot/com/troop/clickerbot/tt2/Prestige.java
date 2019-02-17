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
        if (botSettings.randomTimeToPrestige > 0)
            randomTimeToPrestige = botSettings.timeToPrestige + (bot.rand.nextInt((int)botSettings.randomTimeToPrestige)*60*1000);
        else
            randomTimeToPrestige = botSettings.timeToPrestige;
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
        if(botSettings.autoPrestige && System.currentTimeMillis() - lastPrestigeCheck > runPrestigeCheckActivator && !WaitLock.prestige.get()) {
            Log.d(TAG, "check if boss failed or time to prestige");

            if ((System.currentTimeMillis() - timeSinceLastPrestige > randomTimeToPrestige && randomTimeToPrestige > 0)
                    || (boss.getBossFailedCounter() >= botSettings.bossFailedCount && botSettings.bossFailedCount > 0)) {
                Log.d(TAG, "reason to prestige: bossfailed:" + boss.getBossFailedCounter() + " time toprestige:" + (System.currentTimeMillis() - timeSinceLastPrestige > randomTimeToPrestige));
                bot.clearExecuterQueue();
                if (!WaitLock.prestige.get())
                    bot.executeTask(PrestigeTask.class);

            }
            lastPrestigeCheck = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void doPrestige(ExecuterTask task) throws InterruptedException, IOException {
        Log.d(TAG, "doPrestige:" + botSettings.autoPrestige);
        if (botSettings.autoPrestige) {
            bot.clearExecuterQueue();
            openSwordMasterMenu(task);
            for (int i =0; i < 13; i++)
                swipeDown();

            int loopbreaker = 0;
            WaitLock.prestige.set(true);
            while(!checkPrestigButton() && !Thread.currentThread().isInterrupted() && !task.cancelTask && loopbreaker  < 10) {
                loopbreaker++;
                doLongerSingelTap(new Point(prestigeMenuButton.x -3 +random.nextInt(6),prestigeMenuButton.y -3 +random.nextInt(6)),"on Prestige Menu Button");
                Thread.sleep(200);
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
                Thread.sleep(200);
            }
            loopbreaker = 0;
            while (checkPrestigAcceptColor()&& !Thread.currentThread().isInterrupted()&& !task.cancelTask && loopbreaker  < 5) {
                loopbreaker++;
                doLongerSingelTap(prestigeAcceptButton,  "on Prestige accept Button");
                Thread.sleep(100);
                if (!checkPrestigAcceptColor())
                    loopbreaker = 5;
            }
            Thread.sleep(12000);
            loopbreaker = 0;
            while (checkLoginInfoColor()&& !Thread.currentThread().isInterrupted()&& !task.cancelTask && loopbreaker  < 5) {
                loopbreaker++;
                doLongerSingelTap(loginInfoButton,"close loginInfo");
                Thread.sleep(200);
            }

            WaitLock.prestige.set(false);
            bot.resetTickCounter();
            setMenuState(MenuState.closed);
            bot.clearExecuterQueue();
            init(task);
            if (botSettings.autoLvlBos)
                bot.executeTask(AutoLevelBOSTask.class);
            bot.executeTask(InitTask.class);
            lastPrestigeCheck = System.currentTimeMillis();

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
