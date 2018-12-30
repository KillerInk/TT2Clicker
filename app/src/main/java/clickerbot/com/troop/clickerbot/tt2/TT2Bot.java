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
    private final int CLICK_DELAY = 2;//ms


    private final static int ROOTSHELLS_CLICKERCOUNT = 4;

    private RootShell rootShellClick[] = new RootShell[ROOTSHELLS_CLICKERCOUNT];
    private Skills skills;
    private Heros heros;
    private Boss boss;
    private Prestige prestige;
    private OCR ocr;

    private final int runSkillsActivator = 30000;//ms
    private long lastSkillsActivated = 0;
    private final int runHeroActivator = 30000;//ms
    private long lastHerossActivated = 0;
    private int randomTapCount =0;

    private final long runPrestigeCheckActivator =convertMinToMs(1);
    private long lastPrestigeCheck = 0;
    private int prestigeTime = 60;
    private long timeSinceLastPrestige = 0;

    private boolean useAAW = false;
    private boolean usePhom = true;
    private boolean useCo = true;
    private boolean acceptAddsFairy = true;
    private boolean clickOnFairys = true;


    Random rand = new Random();
    private int minYFairyPos = 110;
    private int maxYFairyPos = 181;

    private List<Point> randomTaps;

    private static long convertMinToMs(int min)
    {
        return min*60*1000;
    }


    public TT2Bot(Context context)
    {
        skills = new Skills();
        heros = new Heros();
        boss = new Boss();
        prestige = new Prestige();
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
        skills.setIBot(this);
        heros.setIBot(this);
        boss.setIBot(this);
        prestige.setIBot(this);
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

    @Override
    void onTick(long tickCounter) {
        if (tickCounter == 1) {
            init();
        }
        else {
            if(System.currentTimeMillis() - lastPrestigeCheck > runPrestigeCheckActivator)
            {
                lastPrestigeCheck = System.currentTimeMillis();
                try {
                    boss.checkIfLockedOnBoss();
                    if (System.currentTimeMillis() - timeSinceLastPrestige > prestigeTime + timeSinceLastPrestige || boss.getBossFailedCounter() >= 3) {
                        Log.d(TAG,"reason to prestige: bossfailed:" +boss.getBossFailedCounter()+ " time toprestige:" + (System.currentTimeMillis() - timeSinceLastPrestige > prestigeTime));
                        prestige.doPrestige();
                        resetTickCounter();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (System.currentTimeMillis() - lastSkillsActivated > runSkillsActivator) {
                lastSkillsActivated = System.currentTimeMillis();
                try {
                    Log.d(TAG,"activateSkills");
                    skills.activateAllSkills();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (System.currentTimeMillis() - lastHerossActivated > runHeroActivator) {
                lastHerossActivated = System.currentTimeMillis();
                try {
                    Log.d(TAG,"activateSkills");
                    heros.levelHeros();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else
                doTap();
        }
        //doTap();
    }

    private void createRandomTaps() {
        randomTaps = new ArrayList<>();
        if (useAAW)
            randomTaps.addAll(Arrays.asList(Coordinates.AAW_Areas));
        if (usePhom)
            randomTaps.add(Coordinates.Phom_Pos);
        if (useCo)
            randomTaps.add(Coordinates.CO_Pos);
        if (clickOnFairys)
        {
            for (int i = 0; i < 5; i++)
            {
                randomTaps.add(new Point(400, getRandomFairyY()));
            }
            if (acceptAddsFairy)
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
        try {
            Log.d(TAG,"init");
            skills.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void doTap()
    {
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
    }








}
