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
import clickerbot.com.troop.clickerbot.tt2.tasks.ClanQuestTask;

public class ClanQuest extends Menu {

    private final String TAG = ClanQuest.class.getSimpleName();

    private final Point boss_head_pos = new Point(235,280);
    private final Point boss_body_pos = new Point(235,408);

    private final Point boss_leftshoulder_pos = new Point(60,285);
    private final Point boss_rightshoulder_pos = new Point(410,285);

    private final Point boss_leftarm_pos = new Point(50,450);
    private final Point boss_rightarm_pos = new Point(420,450);

    private final Point boss_leftfoot_pos = new Point(150,570);
    private final Point boss_righfoot_pos = new Point(320,570);

    private final Point cq_fight_timer_color_pos = new Point(83,50);
    private final Point cq_fight_timer_color_pos2 = new Point(84,50);

    private int howOftenDetected = 0;
    private final Random rand;

    public ClanQuest(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        howOftenDetected = 0;
        rand = new Random();
    }

    @Override
    void init(ExecuterTask task) {
        howOftenDetected = 0;
    }

    @Override
    boolean checkIfRdyToExecute() {
        return false;
    }

    public void doCQ(ExecuterTask executerTask) throws InterruptedException {
        //tap on cq and open clanchat
        Log.d(TAG,"Open ClanChat");
        WaitLock.clanquest.set(true);

        //wait for battle to start
        int loopbreaker = 0;
        while (isTimerRunning() && !isTimerStarted()&& !executerTask.cancelTask && loopbreaker < 15)
        {
            loopbreaker++;
            Log.d(TAG,"Wait for battel to start");
            Thread.sleep(200);
        }
        Log.d(TAG,"Fight is starting");
        //fight boss

        Log.d(TAG,"CreateRandomTaps prepare for FIght");
        List<Point> randomTaps = new ArrayList();
        for(int i = 0; i< 4; i++) {
            if (botSettings.clickOnBossHead)
                randomTaps.add(boss_head_pos);
            if (botSettings.clickOnBossBody)
                randomTaps.add(boss_body_pos);
            if (botSettings.clickOnBossLeftShoulder)
                randomTaps.add(boss_leftshoulder_pos);
            if (botSettings.clickOnBossRightShoulder)
                randomTaps.add(boss_rightshoulder_pos);
            if (botSettings.clickOnBossLeftArm)
                randomTaps.add(boss_leftarm_pos);
            if (botSettings.clickOnBossRightArm)
                randomTaps.add(boss_rightarm_pos);
            if (botSettings.clickOnBossLeftFoot)
                randomTaps.add(boss_leftfoot_pos);
            if (botSettings.clickOnBossRightFoot)
                randomTaps.add(boss_righfoot_pos);
        }


        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() -start < 31000 && !Thread.interrupted() && !executerTask.cancelTask)
        {
            try {
                for (int i = 0; i < randomTaps.size(); i++) {
                    touchInput.tap(getRandomPoint(randomTaps.get(i)), 20);
                    Thread.sleep(10);
                }
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG,"Fight is over, wait for ClanQuest Close");

        WaitLock.clanquest.set(false);
        bot.stop();
    }

    private boolean isTimerRunning()
    {
        int color = bot.getScreeCapture().getColor(cq_fight_timer_color_pos);
        return Color.red(color) > 90 && Color.blue(color) > 90 && Color.green(color)>90;
    }

    private boolean isTimerStarted()
    {
        int color = bot.getScreeCapture().getColor(cq_fight_timer_color_pos2);
        return  Color.red(color) < 90 && Color.blue(color) < 90 && Color.green(color)< 90;
    }

    private int getRandomX(int x)
    {
        return rand.nextInt(10) + 10 +x;
    }

    private int getRandomY(int y) {
        return rand.nextInt(10) + 10 + y;
    }

    private Point getRandomPoint(Point p)
    {
        return new Point(getRandomX(p.x),getRandomY(p.y));
    }
}
