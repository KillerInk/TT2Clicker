package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.TapOnFairyVipWindowTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.TapOnFairysTask;

public class Fairy extends Menu {

    private final String TAG = Fairy.class.getSimpleName();
    private final boolean debug = false;

    private Random rand;
    private List<Point> fairyTaps;

    private final int minYFairyPos = 110;
    private final int maxYFairyPos = 181;

    private Point fairyColorDecline = new Point(35,580); // <<<<<<<< DIFF
    private Point fairyColorAccept = new Point(313 ,591); // <<<<<<<< DIFF

    private final Point accept_Pos = new Point(313 ,591); // <<<<<<<< DIFF
    private final Point decline_Pos = new Point(200 ,630);
    private int howOftenDetected;


    public Fairy(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        rand = new Random();
        fairyTaps = new ArrayList<>();
        if (botSettings.clickOnFairys)
        {
            for (int i = 0; i < 5; i++)
            {
                fairyTaps.add(new Point(400, getRandomFairyY()));
            }
        }
    }

    @Override
    void init(ExecuterTask task) {
        if (botSettings.clickOnFairys)
        {
            for (int i = 0; i < 8; i++)
            {
                fairyTaps.add(new Point(400, getRandomFairyY()));
            }
            if (botSettings.acceptFairyAdds)
                fairyTaps.add(accept_Pos);
            else
                fairyTaps.add(decline_Pos);
        }
    }

    @Override
    boolean checkIfRdyToExecute() {
        return true;
    }

    public void executeTapFairys()
    {
        if (botSettings.clickOnFairys)
            bot.executeTask(TapOnFairysTask.class);
    }

    public void tapFairys(ExecuterTask task)
    {
        if (botSettings.clickOnFairys)
            bot.tapOnPoints(fairyTaps,task);
    }

    public void tapOnFairyVipWindow()
    {
        Log.d(TAG, "tapOnFairyVipWindow");
        if (botSettings.acceptFairyAdds)
            doSingelTap(accept_Pos,"accept fairy add");
        else
            doSingelTap(decline_Pos,"decline fairy add");
    }

    public void checkIfFairyWindowOpen()
    {
        int color = bot.getScreeCapture().getColor(fairyColorDecline);//-294644
        // Log.v(TAG,"checkIfFairyWindowOpen color decline " + ColorUtils.getColorString(color));
        int color2 = bot.getScreeCapture().getColor(fairyColorAccept); // -13981229
        // Log.v(TAG,"checkIfFairyWindowOpen color accept " + ColorUtils.getColorString(color2));
        // Log.d(TAG, "fairy window open: "  + (ColorUtils.redEquals(color,251) && ColorUtils.blueIsInRange(color2,194,262)));
        if (ColorUtils.redEquals(color,117) && ColorUtils.blueIsInRange(color2,189,262))// <<<<<<<< DIFF
        {
            WaitLock.lockFairyWindow(true);
            howOftenDetected++;
            if(howOftenDetected > 2) {
                howOftenDetected = 0;

                Log.d(TAG, "Tap on Fairy Ads Window");
                tapOnFairyVipWindow();
            }
            //bot.putFirstAndExecuteTask(TapOnFairyVipWindowTask.class);
        }
        else {
            WaitLock.lockFairyWindow(false);
            if (howOftenDetected > 0)
                howOftenDetected--;
        }
    }

    private int getRandomFairyY()
    {
        return rand.nextInt(maxYFairyPos - minYFairyPos) + minYFairyPos;
    }
}