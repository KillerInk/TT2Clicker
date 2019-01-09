package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import clickerbot.com.troop.clickerbot.ExecuterTask;
import clickerbot.com.troop.clickerbot.OCR;
import clickerbot.com.troop.clickerbot.RootShell;
import clickerbot.com.troop.clickerbot.tt2.tasks.CrazyTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.RandomTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.TaskFactory;

public class TT2Bot extends AbstractBot
{
    private static final String TAG = TT2Bot.class.getSimpleName();

    private HashMap<Class, ExecuterTask> executerTaskHashMap;

    private RootShell rootShells[];

    private Skills skills;
    private Heros heros;
    private Boss boss;
    private Prestige prestige;
    private Fairy fairy;
    private OCR ocr;

    private final int runRandomTapActivator = 1000;//ms
    private long lastRandomTapActivated = 0;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    Random rand = new Random();

    private List<Point> randomTaps;
    private List<Point> crazyTouchPoints;

    private long lastSwordMasterLeveled =0;
    private long lastUiUpdate = 0;

    public static long convertMinToMs(int min)
    {
        return min*60*1000;
    }

    public TT2Bot(Context context,BotSettings botSettings)
    {
        super(botSettings);
        rootShells = new RootShell[(int)botSettings.shellcount];
        for (int i = 0; i < rootShells.length; i++)
        {
            rootShells[i] = new RootShell(i);
        }
        boss = new Boss(this,botSettings, rootShells);
        skills = new Skills(this,botSettings, rootShells);
        heros = new Heros(this,botSettings, rootShells,boss);

        prestige = new Prestige(this,botSettings, rootShells,boss);
        ocr = new OCR(context,"eng");
        fairy = new Fairy(this,botSettings, rootShells);

        executerTaskHashMap = new TaskFactory().getTasksmap(this,heros,skills,prestige,fairy);

        Log.d(TAG,"TT2Bot()");
    }

    public void executeTask(Class task)
    {
        execute(executerTaskHashMap.get(task));
    }

    public void putFirstAndExecuteTask(Class task) {
        putFirstAndExecute(executerTaskHashMap.get(task));
    }

    public void destroy()
    {
        Log.d(TAG,"destroy");
        ocr.destroy();
        getScreeCapture().destroy();
        for (int i = 0; i < rootShells.length; i++)
        {
            rootShells[i].Close();
        }
    }


    @Override
    public OCR getOcr() {
        return ocr;
    }

    public void start()
    {
        Log.d(TAG,"start");
        createRandomTaps();
        lastTestExecuted = 0;
        for (int i = 0; i < rootShells.length; i++)
            rootShells[i].startProcess();
        super.start();
    }

    public void stop()
    {
        Log.d(TAG,"stop");
        super.stop();
        for (int i = 0; i < rootShells.length; i++)
            rootShells[i].stopProcess();
    }

    @Override
    void onTick(long tickCounter) {
        if (tickCounter == 1) {
           executeTask(InitTask.class);
        }
        else if (botSettings.runTests)
        {
            executeTests();
        }
        else {
            if(!prestige.rdToExecute())
            {
                if (System.currentTimeMillis() - lastUiUpdate > 1000) {
                    long now = System.currentTimeMillis();
                    long dif =  prestige.getTimeSinceLastPrestige() - now -(8*60*60*1000);
                    String out = "Prestige:" + dateFormat.format(new Date(dif))+"\n";
                    //Log.d(TAG, out);
                    out += "BossFailed:" + boss.getBossFailedCounter();
                    UpdatePrestigeTime(out);
                    lastUiUpdate = System.currentTimeMillis();
                }
                swordMasterRdyToExecute();
                skills.rdToExecute();
                heros.rdToExecute();
                if (System.currentTimeMillis() - lastRandomTapActivated > runRandomTapActivator){
                    executeTask(RandomTapTask.class);
                    fairy.executeTapFairys();
                    lastRandomTapActivated = System.currentTimeMillis();
                }
                else if (botSettings.doAutoTap)
                {
                    executeTask(CrazyTapTask.class);
                }
            }
        }
    }

    private long lastTestExecuted;
    private void executeTests() {
        if (System.currentTimeMillis() - lastTestExecuted > 20000) {
            execute(new LevelAllHerosTask(heros));
            lastTestExecuted = System.currentTimeMillis();
        }
    }

    private boolean swordMasterRdyToExecute()
    {
        if (botSettings.autoLvlSwordMaster && System.currentTimeMillis() - lastSwordMasterLeveled > botSettings.levelSwordMasterTime)
        {
            executeTask(LevelSwordMasterTask.class);
            lastSwordMasterLeveled = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    private void createRandomTaps() {
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
        if (botSettings.useFlashZip)
            randomTaps.addAll(Arrays.asList(Coordinates.FLASH_ZIP_Areas));

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
        Log.i(TAG,"init");
        skills.init();
    }

    private int getRandomX()
    {
        return rand.nextInt(20) + 20 +Coordinates.crazy_touch_pos.x;
    }

    private int getRandomY() {
        return rand.nextInt(20) + 20 + Coordinates.crazy_touch_pos.y;
    }

    public void doTap(ExecuterTask task)
    {
        //Log.d(TAG,"doRandomTap");
        tapOnPoints(randomTaps,task);
    }

    public void doCrazyTap(ExecuterTask task)
    {
        //Log.d(TAG,"doCrazyTap");
        tapOnPoints(crazyTouchPoints,task);
    }

    public void tapOnPoints(List<Point> points, ExecuterTask task)
    {
        for (int i=0; i < points.size(); i+= rootShells.length-1)
        {
            for(int s = 1; s< rootShells.length; s++)
            {
                if (task.cancelTask)
                    return;
                int pos = i+s-1;
                if (pos < points.size()) {
                    try {
                        rootShells[s].doTap(points.get(pos));
                        try {
                            Thread.sleep(botSettings.clickSleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (ArrayIndexOutOfBoundsException e) {
                        //e.printStackTrace();
                    }
                }
                else
                    break;
            }
        }
    }

    @Override
    public void onScreenCapture() {
        //UpdateImage(getScreeCapture().getScreenDumpBmp());
        boss.checkIfActiveBossFight();
        fairy.checkIfFairyWindowOpen();
    }
}
