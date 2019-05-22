package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.CollectDailyTask;

public class CollectDailyReward extends Menu {

    private final String TAG = CollectDailyReward.class.getSimpleName();
    private long lastDailyCheck;
    private Point present_colorPos = new Point(32,186);
    private int presentColor = Color.argb(255,206,206,206);
    private Point bottomButton_colorPos = new Point(30,198);
    private int bottomButtontColor = -15451051;

    private Point dailyRewardsCollectButton_Pos = new Point(243,546);

    public CollectDailyReward(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init(ExecuterTask task) {
        lastDailyCheck = 0;
    }

    @Override
    boolean checkIfRdyToExecute() {
        if (System.currentTimeMillis() - lastDailyCheck > 1000 && !Menu.MenuOpen.get() && !WaitLock.sceneTransition.get())
        {
            lastDailyCheck = System.currentTimeMillis();
            WaitLock.checkForErrorAndWait();
            int color = bot.getScreeCapture().getColor(present_colorPos);
            int color2 = bot.getScreeCapture().getColor(bottomButton_colorPos);
            if (color == presentColor && color2 == bottomButtontColor)
                bot.executeTask(CollectDailyTask.class);
        }
        return false;
    }

    public void collectDailyReward(ExecuterTask executerTask) throws InterruptedException {
        WaitLock.lockFairyWindow(true);
        doLongerSingelTap(present_colorPos,TAG);
        Thread.sleep(500);
        doLongerSingelTap(dailyRewardsCollectButton_Pos, TAG);
        Thread.sleep(500);
        WaitLock.lockFairyWindow(false);
    }
}
