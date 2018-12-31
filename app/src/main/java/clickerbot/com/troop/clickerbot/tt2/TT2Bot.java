package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import clickerbot.com.troop.clickerbot.OCR;
import clickerbot.com.troop.clickerbot.RootShell;

public class TT2Bot extends AbstractBot
{

    private static final String TAG = TT2Bot.class.getSimpleName();
    private final int CLICK_DELAY = 5;//ms


    private final static int ROOTSHELLS_CLICKERCOUNT = 8;

    private RootShell rootShellClick[] = new RootShell[ROOTSHELLS_CLICKERCOUNT];
    private Skills skills;
    private Heros heros;
    private Boss boss;
    private Prestige prestige;
    private OCR ocr;

    private final int runSkillsActivator = 30000;//ms
    private long lastSkillsActivated = 0;

    private final int runHeroActivator = 60000;//ms
    private long lastHerossActivated = 0;

    private int randomTapCount =0;
    private int crazyTapCount =0;

    private final int runRandomTapActivator = 1000;//ms
    private long lastRandomTapActivated = 0;

    private final long runPrestigeCheckActivator =convertMinToMs(1);
    private long lastPrestigeCheck = 0;
    //private int prestigeTime = 60;
    private long timeSinceLastPrestige = 0;

    private boolean useAAW = false;
    private boolean usePhom = true;
    private boolean useCo = true;


    Random rand = new Random();
    private int minYFairyPos = 110;
    private int maxYFairyPos = 181;

    private List<Point> randomTaps;
    private List<Point> crazyTouchPoints;
    private final BotSettings botSettings;

    private static long convertMinToMs(int min)
    {
        return min*60*1000;
    }


    public TT2Bot(Context context,BotSettings botSettings)
    {
        this.botSettings = botSettings;
        skills = new Skills(this,botSettings);
        heros = new Heros(this,botSettings);
        boss = new Boss(this,botSettings);
        prestige = new Prestige(this,botSettings);
        ocr = new OCR(context,"eng");
        Log.d(TAG,"TT2Bot()");
    }

    public void destroy()
    {
        Log.d(TAG,"destroy");
        ocr.destroy();
    }

    @Override
    public OCR getOcr() {
        return ocr;
    }


    public void start()
    {
        Log.d(TAG,"start");
        createRandomTaps();

        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            rootShellClick[i] = new RootShell(i,80);
        }
        skills.setRootShellClick(rootShellClick);
        heros.setRootShellClick(rootShellClick);
        boss.setRootShellClick(rootShellClick);
        prestige.setRootShellClick(rootShellClick);
        super.start();

    }

    public void stop()
    {
        Log.d(TAG,"stop");
        super.stop();
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            rootShellClick[i].Close();
        }

    }

    long lastBossActiveFightCheck = 0;
    long lastSwordMasterLeveled =0;


    @Override
    void onTick(long tickCounter) {
        if (tickCounter == 1) {
            init();
        }
        else {
            if(!boss.isActiveBossFight() && System.currentTimeMillis() - lastPrestigeCheck > runPrestigeCheckActivator)
            {
                Log.d(TAG, "check if boss failed or time to prestige");

                try {
                    boss.checkIfLockedOnBoss();
                    if (System.currentTimeMillis() - timeSinceLastPrestige > botSettings.timeToPrestige + timeSinceLastPrestige || boss.getBossFailedCounter() >= botSettings.bossFailedCount) {
                        Log.d(TAG,"reason to prestige: bossfailed:" +boss.getBossFailedCounter()+ " time toprestige:" + (System.currentTimeMillis() - timeSinceLastPrestige > botSettings.timeToPrestige));
                        prestige.doPrestige();
                        resetTickCounter();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lastPrestigeCheck = System.currentTimeMillis();
            }
            else if (botSettings.autoLvlSwordMaster && System.currentTimeMillis() - lastSwordMasterLeveled > 60000)
            {
                skills.lvlSwordMaster();
                lastSwordMasterLeveled = System.currentTimeMillis();
            }
            else if (System.currentTimeMillis() - lastSkillsActivated > runSkillsActivator) {

                try {
                    Log.d(TAG,"activate Skills");
                    skills.activateAllSkills();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lastSkillsActivated = System.currentTimeMillis();
            }
            else if (!boss.isActiveBossFight() && botSettings.autoLvlHeros && System.currentTimeMillis() - lastHerossActivated > runHeroActivator) {
                lastHerossActivated = System.currentTimeMillis();
                try {
                    Log.d(TAG,"level Heros");
                    heros.levelHeros();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (System.currentTimeMillis() - lastRandomTapActivated > runRandomTapActivator){

                doTap();
                lastRandomTapActivated = System.currentTimeMillis();
            }
            else if (botSettings.doAutoTap)
            {
                doCrazyTap();
            }

        }
        //doTap();
    }

    @Override
    void onScreenDump() {
        synchronized (this)
        {
            this.notify();
        }
        boss.checkIfActiveBossFight();
    }

    private void createRandomTaps() {
        randomTaps = new ArrayList<>();
        if (useAAW)
            randomTaps.addAll(Arrays.asList(Coordinates.AAW_Areas));
        if (usePhom)
            randomTaps.add(Coordinates.Phom_Pos);
        if (useCo)
            randomTaps.add(Coordinates.CO_Pos);
        if (botSettings.clickOnFairys)
        {
            for (int i = 0; i < 5; i++)
            {
                randomTaps.add(new Point(400, getRandomFairyY()));
            }
            if (botSettings.acceptFairyAdds)
                randomTaps.add(Coordinates.accept_Pos);
            else
                randomTaps.add(Coordinates.decline_Pos);
        }
    }

    private int getRandomFairyY()
    {
        return rand.nextInt(maxYFairyPos - minYFairyPos) + minYFairyPos;
    }

    private void init()
    {
        timeSinceLastPrestige = System.currentTimeMillis();
        boss.resetBossFailedCounter();
        if (botSettings.doAutoTap)
        {
            crazyTouchPoints = new ArrayList<>();
            for (int i=0; i< 20;i++)
            {
                crazyTouchPoints.add(new Point(getRandomX(),getRandomY()));
            }
        }
        try {
            Log.d(TAG,"init");
                skills.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getRandomX()
    {
        return rand.nextInt(20) + 20 +Coordinates.crazy_touch_pos.x;
    }

    private int getRandomY() {
        return rand.nextInt(20) + 20 + Coordinates.crazy_touch_pos.y;
    }



    private void doTap()
    {
        Log.d(TAG,"doRandomTap");
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            try {
                Point p = randomTaps.get(randomTapCount++);
                //Log.d(TAG, "doTap x:" + p.x + " y:" + p.y);
                rootShellClick[i].doTap(p);
                if (randomTapCount == randomTaps.size())
                    randomTapCount = 0;
                Thread.sleep(CLICK_DELAY);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doCrazyTap()
    {
        Log.d(TAG,"doCrazyTap");
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            try {
                Point p = crazyTouchPoints.get(crazyTapCount++);
                rootShellClick[i].doTap(p);
                if (crazyTapCount == crazyTouchPoints.size())
                    crazyTapCount = 0;
                Thread.sleep(CLICK_DELAY);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }








}
