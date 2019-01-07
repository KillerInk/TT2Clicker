package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.tt2.tasks.TapOnFairyVipWindowTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.TapOnFairysTask;

public class Fairy extends Menu {

    private final String TAG = Fairy.class.getSimpleName();

    private Random rand;
    private List<Point> fairyTaps;
    private TapOnFairyVipWindowTask tapOnFairyVipWindowTask;
    private TapOnFairysTask tapOnFairysTask;

    private final int minYFairyPos = 110;
    private final int maxYFairyPos = 181;

    private Point fairyColorDecline = new Point(57,593);
    private Point fairyColorAccept = new Point(420,589);


    public Fairy(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell) {
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
        tapOnFairyVipWindowTask = new TapOnFairyVipWindowTask(this);
        tapOnFairysTask = new TapOnFairysTask(this);
    }

    @Override
    void init() {
        if (botSettings.clickOnFairys)
        {
            for (int i = 0; i < 8; i++)
            {
                fairyTaps.add(new Point(400, getRandomFairyY()));
            }
            if (botSettings.acceptFairyAdds)
                fairyTaps.add(Coordinates.accept_Pos);
            else
                fairyTaps.add(Coordinates.decline_Pos);
        }
    }

    @Override
    boolean rdToExecute() {
        return !Menu.MenuOpen;
    }

    public void executeTapFairys()
    {
        if (botSettings.clickOnFairys)
            bot.execute(tapOnFairysTask);
    }

    public void tapFairys()
    {
        if (botSettings.clickOnFairys)
            bot.tapOnPoints(fairyTaps);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tapOnFairyVipWindow();
    }

    public void tapOnFairyVipWindow()
    {
        if (!Menu.MenuOpen) {
            if (botSettings.acceptFairyAdds)
                doSingelTap(Coordinates.accept_Pos);
            else
                doSingelTap(Coordinates.decline_Pos);
        }
    }

    public void checkIfFairyWindowOpen()
    {
        int color = bot.getScreeCapture().getColor(fairyColorDecline);//-294644
        Log.d(TAG,"checkIfFairyWindowOpen color decline " + ColorUtils.getColorString(color));
        int color2 = bot.getScreeCapture().getColor(fairyColorAccept); // -13981229
        Log.d(TAG,"checkIfFairyWindowOpen color accept " + ColorUtils.getColorString(color2));
        Log.d(TAG, "fairy window open: "  + (ColorUtils.redEquals(color,251) && ColorUtils.blueIsInRange(color2,194,262)));
        if (ColorUtils.redEquals(color,251) && ColorUtils.blueIsInRange(color2,194,262))
        {
            Log.d(TAG,"Tap on Fairy Ads Window");
            bot.putFirstAndExecute(tapOnFairyVipWindowTask);
        }
    }

    private int getRandomFairyY()
    {
        return rand.nextInt(maxYFairyPos - minYFairyPos) + minYFairyPos;
    }
}
