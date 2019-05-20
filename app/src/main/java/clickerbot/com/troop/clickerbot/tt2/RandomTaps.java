package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class RandomTaps extends Item {

    private final String TAG = RandomTaps.class.getSimpleName();
    /**
     * hold all randomtap points
     */
    private List<Point> randomTaps;
    /**
     * hold all crazytouchpoints
     */
    private List<Point> crazyTouchPoints;

    /**
     * time after a randomtap gets executed
     */
    private final int runRandomTapActivator = 1000;//ms
    /**
     * last time a randomtap got activated
     */
    private long lastRandomTapActivated = 0;

    public RandomTaps(TT2Bot ibot, BotSettings botSettings, TouchInterface touchInput) {
        super(ibot, botSettings, touchInput);
    }

    @Override
    public void init(ExecuterTask task) {
        int startTapPoints = 40;
        if (botSettings.useFlashZip || botSettings.useAAW)
            startTapPoints -=30;
        if (botSettings.doAutoTap)
        {
            crazyTouchPoints = new ArrayList<>();
            for (int i=0; i< startTapPoints;i++)
            {
                crazyTouchPoints.add(new Point(bot.getRandomX(),bot.getRandomY()));
            }
        }
    }

    @Override
    boolean checkIfRdyToExecute() {
        return false;
    }

    public void createRandomTaps() {
        Log.i(TAG, "createRandomTaps()");
        randomTaps = new ArrayList<>();
        if (botSettings.useAAW)
            randomTaps.addAll(Arrays.asList(Coordinates.AAW_Areas));
        if (botSettings.usePHOM)
            randomTaps.add(Coordinates.Phom_Pos);
        if (botSettings.useCO)
            randomTaps.add(Coordinates.CO_Pos);
        if (botSettings.collectEggs)
            randomTaps.add(Coordinates.egg_to_collect_pos);

    }

    /**
     * execute randomtaps
     * @param task that execute it
     */
    public void doTap(ExecuterTask task)
    {
        //Log.d(TAG,"doRandomTap");
        tapOnPoints(randomTaps,task);
    }

    /**
     * execute fast clicking on screen
     * @param task that execute it
     */
    public void doCrazyTap(ExecuterTask task)
    {
        //Log.d(TAG,"doCrazyTap");
        tapOnPoints(crazyTouchPoints,task);
    }

    /**
     * Taps fast on screen
     * @param points to touch
     * @param task task that execute it
     */
    public void tapOnPoints(List<Point> points, ExecuterTask task)
    {
        if (points == null )
            return;
        try {
            for (int i=0; i < points.size(); i++)
            {
                if (task.cancelTask)
                    return;
                WaitLock.checkForErrorAndWait();
                WaitLock.waitForFlashZip();
                touchInput.tap(points.get(i),30);
                WaitLock.checkForErrorAndWait();
                Thread.sleep(botSettings.clickSleepTime);
                WaitLock.checkForErrorAndWait();
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
