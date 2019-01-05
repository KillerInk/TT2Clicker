package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import clickerbot.com.troop.clickerbot.OCR;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.ScreenCapture;
import clickerbot.com.troop.clickerbot.tt2.tasks.CrazyTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.RandomTapTask;

public class TT2Bot extends AbstractBot
{

    private static final String TAG = TT2Bot.class.getSimpleName();
    private final int CLICK_DELAY = 40;//ms


    private final static int ROOTSHELLS_CLICKERCOUNT = 4;

    private RootShell rootShellClick[] = new RootShell[ROOTSHELLS_CLICKERCOUNT];
    private Skills skills;
    private Heros heros;
    private Boss boss;
    private Prestige prestige;
    private OCR ocr;



    private LevelSwordMasterTask lvlswordMasterTask;
    private RandomTapTask randomTapTask;
    private CrazyTapTask crazyTapTask;
    private InitTask initTask;


    private int randomTapCount =0;
    private int crazyTapCount =0;

    private final int runRandomTapActivator = 1000;//ms
    private long lastRandomTapActivated = 0;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");



    private boolean useAAW = false;
    private boolean usePhom = true;
    private boolean useCo = true;


    Random rand = new Random();
    private int minYFairyPos = 110;
    private int maxYFairyPos = 181;

    private List<Point> randomTaps;
    private List<Point> crazyTouchPoints;
    private final BotSettings botSettings;

    public static long convertMinToMs(int min)
    {
        return min*60*1000;
    }


    public TT2Bot(Context context,BotSettings botSettings)
    {
        super();
        this.botSettings = botSettings;
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            rootShellClick[i] = new RootShell(i);
        }
        boss = new Boss(this,botSettings,rootShellClick);
        skills = new Skills(this,botSettings,rootShellClick);
        heros = new Heros(this,botSettings,rootShellClick,boss);

        prestige = new Prestige(this,botSettings,rootShellClick,boss);
        ocr = new OCR(context,"eng");

        lvlswordMasterTask = new LevelSwordMasterTask(skills);
        randomTapTask = new RandomTapTask(this);
        crazyTapTask = new CrazyTapTask(this);
        initTask = new InitTask(this);

        Log.d(TAG,"TT2Bot()");
    }

    public void destroy()
    {
        Log.d(TAG,"destroy");
        ocr.destroy();
        getScreeCapture().destroy();
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            rootShellClick[i].Close();
        }
    }

    public InitTask getInitTask()
    {
        return initTask;
    }

    @Override
    public OCR getOcr() {
        return ocr;
    }



    public void start()
    {
        Log.d(TAG,"start");
        createRandomTaps();

        super.start();


    }

    public void stop()
    {
        Log.d(TAG,"stop");
        super.stop();



    }

    long lastBossActiveFightCheck = 0;
    long lastSwordMasterLeveled =0;

    long lastUiUpdate = 0;


    @Override
    void onTick(long tickCounter) {
        if (tickCounter == 1) {
            execute(initTask);
            //init();
        }
        else {
            if(!prestige.rdToExecute())
            {
                if (System.currentTimeMillis() - lastUiUpdate > 1000) {
                    long now = System.currentTimeMillis();
                    long dif =  prestige.getTimeSinceLastPrestige() - now -(8*60*60*1000);
                    String out = "Prestige:" + dateFormat.format(new Date(dif))+"\n";
                    Log.d(TAG, out);
                    out += "BossFailed:" + boss.getBossFailedCounter() + "\n";
                    UpdatePrestigeTime(out);
                    lastUiUpdate = System.currentTimeMillis();
                }
                swordMasterRdyToExecute();
                skills.rdToExecute();
                heros.rdToExecute();
                if (System.currentTimeMillis() - lastRandomTapActivated > runRandomTapActivator){

                    execute(randomTapTask);
                    lastRandomTapActivated = System.currentTimeMillis();

                }
                else if (botSettings.doAutoTap)
                {
                    execute(crazyTapTask);

                }
            }

        }
        //doTap();
    }

    private boolean swordMasterRdyToExecute()
    {
        if (botSettings.autoLvlSwordMaster && System.currentTimeMillis() - lastSwordMasterLeveled > 60000)
        {
            execute(lvlswordMasterTask);
            lastSwordMasterLeveled = System.currentTimeMillis();
            return true;
        }
        return false;
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

    public void init()
    {
        prestige.init();

        boss.resetBossFailedCounter();
        if (botSettings.doAutoTap)
        {
            crazyTouchPoints = new ArrayList<>();
            for (int i=0; i< 20;i++)
            {
                crazyTouchPoints.add(new Point(getRandomX(),getRandomY()));
            }
        }
        Log.d(TAG,"init");
        skills.init();
    }

    private int getRandomX()
    {
        return rand.nextInt(20) + 20 +Coordinates.crazy_touch_pos.x;
    }

    private int getRandomY() {
        return rand.nextInt(20) + 20 + Coordinates.crazy_touch_pos.y;
    }



    public void doTap()
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

    public void doCrazyTap()
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


    @Override
    public void onScreenCapture() {
        boss.checkIfActiveBossFight();
    }
}
