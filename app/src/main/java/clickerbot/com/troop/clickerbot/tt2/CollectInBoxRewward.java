package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.CollectDailyTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.CollectInboxRewardTask;

public class CollectInBoxRewward extends Menu {

    private final String TAG = CollectInBoxRewward.class.getSimpleName();

    private long lastCheck;

    private final Point screenButtonColor1_pos = new Point(31,349);
    final int scrennButtoncolor1 = Color.argb(255,174,91,138);
    private final Point screenButtonColor2_pos = new Point(30,376);
    final int scrennButtoncolor2 = Color.argb(255,120,62,93);
    private final Point screenButtonClick_pos = new Point(31,363);
    private final Point clanButtonClick_pos = new Point(340,106);
    private final Point collectButtonClick_pos = new Point(236,279);
    private final Point collectButtonColor = new Point(293,278);

    public CollectInBoxRewward(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init(ExecuterTask task) {
        lastCheck = 0;
    }

    @Override
    boolean checkIfRdyToExecute() {
        if (System.currentTimeMillis() - lastCheck > 1000 && Menu.getMenuState() == MenuState.closed && !WaitLock.sceneTransition.get())
        {
            lastCheck = System.currentTimeMillis();
            WaitLock.checkForErrorAndWait();
            int color = bot.getScreeCapture().getColor(screenButtonColor1_pos);
            int color2 = bot.getScreeCapture().getColor(screenButtonColor2_pos);
            if (color == scrennButtoncolor1 && color2 == scrennButtoncolor2)
                bot.executeTask(CollectInboxRewardTask.class);
        }
        return false;
    }

    public void collectInboxReward(ExecuterTask executerTask) throws InterruptedException {
        WaitLock.lockFairyWindow(true);
        doLongerSingelTap(screenButtonClick_pos,TAG);
        Thread.sleep(500);
        doLongerSingelTap(clanButtonClick_pos, TAG);
        Thread.sleep(500);
        int color = bot.getScreeCapture().getColor(collectButtonColor);
        if (ColorUtils.redIsInRange(color,38,40) && ColorUtils.greenIsInRange(color,153,170) && ColorUtils.blueIsInRange(color, 202,218)){
            doLongerSingelTap(collectButtonClick_pos,TAG);
            Thread.sleep(500);
        }
        WaitLock.lockFairyWindow(false);
    }
}
