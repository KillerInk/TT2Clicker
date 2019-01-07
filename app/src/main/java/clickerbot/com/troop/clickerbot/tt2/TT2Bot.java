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
import clickerbot.com.troop.clickerbot.tt2.tasks.CrazyTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.RandomTapTask;

public class TT2Bot extends AbstractBot
{

    private static final String TAG = TT2Bot.class.getSimpleName();
    private final int CLICK_DELAY = 80;//ms


    private final static int ROOTSHELLS_CLICKERCOUNT = 8;

    private RootShell rootShellClick[] = new RootShell[ROOTSHELLS_CLICKERCOUNT];

    private Skills skills;
    private Heros heros;
    private Boss boss;
    private Prestige prestige;
    private Fairy fairy;
    private OCR ocr;

    private LevelSwordMasterTask lvlswordMasterTask;
    private RandomTapTask randomTapTask;
    private CrazyTapTask crazyTapTask;
    private InitTask initTask;

    private final int runRandomTapActivator = 1000;//ms
    private long lastRandomTapActivated = 0;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");



    private boolean useAAW = false;
    private boolean usePhom = true;
    private boolean useCo = true;


    Random rand = new Random();


    private List<Point> randomTaps;
    private List<Point> crazyTouchPoints;

    public static long convertMinToMs(int min)
    {
        return min*60*1000;
    }


    public TT2Bot(Context context,BotSettings botSettings)
    {
        super(botSettings);
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            rootShellClick[i] = new RootShell(i);
        }
        boss = new Boss(this,botSettings,rootShellClick);
        skills = new Skills(this,botSettings,rootShellClick);
        heros = new Heros(this,botSettings,rootShellClick,boss);

        prestige = new Prestige(this,botSettings,rootShellClick,boss);
        ocr = new OCR(context,"eng");
        fairy = new Fairy(this,botSettings,rootShellClick);

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
        for (int i=0; i < rootShellClick.length; i++)
            rootShellClick[i].startProcess();

        super.start();


    }

    public void stop()
    {
        Log.d(TAG,"stop");
        super.stop();
        for (int i=0; i < rootShellClick.length; i++)
            rootShellClick[i].stopProcess();
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
                    fairy.executeTapFairys();
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

    }



    public void init()
    {
        prestige.init();

        boss.resetBossFailedCounter();
        if (botSettings.doAutoTap)
        {
            crazyTouchPoints = new ArrayList<>();
            for (int i=0; i< 40;i++)
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
        tapOnPoints(randomTaps);
    }

    public void doCrazyTap()
    {
        Log.d(TAG,"doCrazyTap");
        tapOnPoints(crazyTouchPoints);
    }

    public void tapOnPoints(List<Point> points)
    {
        for (int i=0; i < points.size(); i+=rootShellClick.length)
        {
            for(int s = 0; s<rootShellClick.length;s++)
            {
                int pos = i+s;
                if (pos < points.size()) {
                    try {
                        rootShellClick[s].doTap(points.get(pos));
                        try {
                            Thread.sleep(CLICK_DELAY);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (ArrayIndexOutOfBoundsException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
    }


    @Override
    public void onScreenCapture() {
        //UpdateImage(getScreeCapture().getScreenDumpBmp());
        boss.checkIfActiveBossFight();
        //fairy.checkIfFairyWindowOpen();
    }
}
