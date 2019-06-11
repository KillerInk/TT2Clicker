package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
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
    private RandomTaps randomTaps;


    public Fairy(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell,RandomTaps randomTaps) {
        super(ibot, botSettings, rootShell);
        this.randomTaps = randomTaps;
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
            if (botSettings.isVipFairy)
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
            randomTaps.tapOnPoints(fairyTaps,task);
    }

    public void tapOnFairyVipWindow()
    {
        Log.d(TAG, "tapOnFairyVipWindow");
        if (botSettings.isVipFairy)
            doSingelTap(accept_Pos,"accept fairy add");
        else
            doSingelTap(decline_Pos,"decline fairy add");
    }

    public void checkIfFairyWindowOpen() throws InterruptedException {
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

                if (isDiasFairy() && botSettings.collectDiasFairy) {
                    Log.d(TAG, "Dias Fairy detected");
                    acceptFairyAndHandelAdds();
                }
                else if (isGoldFairy() && botSettings.collectGoldFairy) {
                    Log.d(TAG, "Gold Fairy detected");
                    acceptFairyAndHandelAdds();
                }
                else if (isManaFairy() && botSettings.collectManaFairy) {
                    Log.d(TAG, "Mana Fairy detected");
                    acceptFairyAndHandelAdds();
                }
                else if (isReduceGoldFairy() && botSettings.collectReduceGoldFairy) {
                    Log.d(TAG, "ReduceGold Fairy detected");
                    acceptFairyAndHandelAdds();
                }
                else if (isSkillFairy() && botSettings.collectSkillsFairy) {
                    Log.d(TAG, "Skill Fairy detected");
                    acceptFairyAndHandelAdds();
                }
                else
                {
                    doSingelTap(decline_Pos,"decline fairy add");
                }

            }
            //bot.putFirstAndExecuteTask(TapOnFairyVipWindowTask.class);
        }
        else {
            WaitLock.lockFairyWindow(false);
            if (howOftenDetected > 0)
                howOftenDetected--;
        }
    }

    private void acceptFairyAndHandelAdds() throws InterruptedException {
        doSingelTap(accept_Pos,"accept fairy add");
        if (!botSettings.isVipFairy)
        {
            Thread.sleep(31000);
            touchInput.backButton();
            Thread.sleep(1000);
            doSingelTap(new Point(239,604),"Collect");
        }
    }

    private int getRandomFairyY()
    {
        return rand.nextInt(maxYFairyPos - minYFairyPos) + minYFairyPos;
    }


    private int manaColor1 = Color.argb(255,56,227,251);
    private Point manaColor1_Pos = new Point(240,371);
    private int manaColor2 = Color.argb(255,4,50,94);
    private Point manaColor2_Pos = new Point(71,540);


    private boolean isColorFromPos(Point one, Point two, int color1, int color2)
    {
        int c1 = bot.getScreeCapture().getColor(one);
        int c2 = bot.getScreeCapture().getColor(two);
        Log.d(TAG, "Color1Input: " + ColorUtils.logColor(c1) + " Color1Set: " + ColorUtils.logColor(color1));
        Log.d(TAG, "Color2Input: " + ColorUtils.logColor(c2) + " Color2Set: " + ColorUtils.logColor(color2));
        if (c1 == color1 && c2 == color2)
            return true;
        return  false;
    }

    private boolean isManaFairy()
    {
        Log.d(TAG, "Mana Fairy");
        return isColorFromPos(manaColor1_Pos, manaColor2_Pos, manaColor1, manaColor2);
    }

    private int diasColor1 = Color.argb(255,59,230,244);
    private Point diasColor1_Pos = new Point(249,382);
    private int diasColor2 = Color.argb(255,130,45,8);
    private Point diasColor2_Pos = new Point(265,423);

    private boolean isDiasFairy()
    {
        Log.d(TAG, "DIas Fairy");
        return isColorFromPos(diasColor1_Pos,diasColor2_Pos, diasColor1,diasColor2);
    }

    private int goldColor1 = Color.argb(255,249,215,11);
    private Point goldColor1_Pos = new Point(249,382);
    private int goldColor2 = Color.argb(255,134,48,8);
    private Point goldColor2_Pos = new Point(265,423);

    private boolean isGoldFairy()
    {
        Log.d(TAG, "Gold Fairy");
        return isColorFromPos(goldColor1_Pos,goldColor2_Pos,goldColor1,goldColor2);
    }


    private int skillColor1 = Color.argb(255,153,70,192);
    private Point skillColor1_Pos = new Point(343,354);
    private int skillColor2 = Color.argb(255,128,117,113);
    private Point skillColor2_Pos = new Point(339,246);

    private boolean isSkillFairy()
    {
        Log.d(TAG, "Skill Fairy");
        return isColorFromPos(skillColor1_Pos,skillColor2_Pos,skillColor1,skillColor2);
    }


    private int reduceGoldColor1 = Color.argb(255,215,42,113);
    private Point reduceGoldColor1_Pos = new Point(365,292);
    private int reduceGoldColor2 = Color.argb(255,251,223,28);
    private Point reduceGoldColor2_Pos = new Point(327,267);

    private boolean isReduceGoldFairy()
    {
        Log.d(TAG, "Reduce Gold Fairy");
        return isColorFromPos(reduceGoldColor1_Pos,reduceGoldColor2_Pos, reduceGoldColor1,reduceGoldColor2);
    }
}