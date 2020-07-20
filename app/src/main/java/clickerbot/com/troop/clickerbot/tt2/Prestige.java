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
import clickerbot.com.troop.clickerbot.tt2.tasks.ExtractArtifactsImageTask;


public class Prestige extends Menu {

    private final String TAG = Prestige.class.getSimpleName();

    private final long runPrestigeCheckActivator = 5000;
    private long lastPrestigeCheck = 0;
    private long timeSinceLastPrestige = 0;
    private Boss boss;

    //Prestige button inside the hero menu, under the hero level button and over heavenly strike ( yellow)
    private final Point prestigeButtonSwordMasterMenu_pos = new Point(401,240);
    //Prestige button inside the prestige submenu that appear after the prestige button from hero menu got clicked (light blue)
    private final Point prestigeButtonSubMenu_pos = new Point(240,706);
    private final Point prestigeButtonSubMenu_pos2 = new Point(240,739);
    //Position to check  if the prestige submenu got open, if the color at this postion is light blue, the submenu is open
    private final Point prestigeSubmenuButton_color_pos = new Point(240,707);
    private final Point prestigeAcceptButton = new Point(326,574);
    private final Point prestigeAcceptButton_color = new Point(326,573);
    private final Point prestigeAcceptButton_color2 = new Point(326,608);
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
            gotToTopMaximised(task);

            int loopbreaker = 0;

            Log.d(TAG, "Press Hero menu Prestige Button");
            clickOnPrestigeSwordMasterMenu(task, loopbreaker);
            loopbreaker = 0;
            Log.d(TAG, "Press Prestige Submenu Prestige button");
            clickOnPrestigeSubmenuPrestigeButton(task, loopbreaker);
            loopbreaker = 0;
            Log.d(TAG, "Press Prestige Submenu Accept Prestige button");
            clickOnSubSubMenuPrestigeButton(task, loopbreaker);
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
            else if(botSettings.levelArtifacts)
                bot.executeTask(ExtractArtifactsImageTask.class);
            bot.executeTask(InitTask.class);
            WaitLock.prestige.set(false);
        }
    }

    public void clickOnSubSubMenuPrestigeButton(ExecuterTask task, int loopbreaker) throws InterruptedException {
        while ((checkPrestigAcceptColor() || checkPrestigAcceptColorBottom()) && !Thread.currentThread().isInterrupted()&& !task.cancelTask && loopbreaker  < 5) {
            loopbreaker++;
            Thread.sleep(600);
            if (checkPrestigAcceptColor())
                doLongerSingelTap(prestigeAcceptButton,  "on Prestige accept Button");
            else if (checkPrestigAcceptColorBottom())
                doLongerSingelTap(prestigeAcceptButton_color2,  "on Prestige accept Button");
            Thread.sleep(600);
            if (!checkPrestigAcceptColor())
                loopbreaker = 5;
        }
    }

    public void clickOnPrestigeSubmenuPrestigeButton(ExecuterTask task, int loopbreaker) throws InterruptedException {
        while ((!checkPrestigAcceptColor() || !checkPrestigAcceptColor2()) && !Thread.currentThread().isInterrupted()&& !task.cancelTask && loopbreaker  < 5) {
            loopbreaker++;
            if (checkPrestigAcceptColor())
                doLongerSingelTap(prestigeButtonSubMenu_pos,"on Prestige Button");
            else if (checkPrestigAcceptColor2())
                doLongerSingelTap(prestigeButtonSubMenu_pos2,"on Prestige Button");
            Thread.sleep(600);
        }
    }

    public void clickOnPrestigeSwordMasterMenu(ExecuterTask task, int loopbreaker) throws InterruptedException {
        while(!checkIfPrestigeSubMenuIsOpen() && !Thread.currentThread().isInterrupted() && !task.cancelTask && loopbreaker  < 10) {
            loopbreaker++;
            if (!checkIfPrestigeSubMenuIsOpen() && !checkPrestigAcceptColor2())
                doLongerSingelTap(new Point(prestigeButtonSwordMasterMenu_pos.x -3 +random.nextInt(6), prestigeButtonSwordMasterMenu_pos.y -3 +random.nextInt(6)),"on Prestige Menu Button");
            Thread.sleep(600);
        }
    }

    private boolean checkPrestigAcceptColor()
    {
        int color = bot.getScreeCapture().getColor(prestigeAcceptButton_color);
        return Color.blue(color)> 200;
    }

    private boolean checkPrestigAcceptColorBottom()
    {
        int color = bot.getScreeCapture().getColor(prestigeAcceptButton_color2);
        return Color.blue(color)> 200;
    }

    private boolean checkIfPrestigeSubMenuIsOpen()
    {
        int color = bot.getScreeCapture().getColor(prestigeSubmenuButton_color_pos);
        return Color.blue(color)> 200;
    }

    private boolean checkPrestigAcceptColor2()
    {
        int color = bot.getScreeCapture().getColor(prestigeButtonSubMenu_pos2);
        return Color.blue(color)> 200;
    }

    private boolean checkLoginInfoColor()
    {
        int color = bot.getScreeCapture().getColor(loginInfoButton);
        return Color.blue(color)> 200;
    }
}
