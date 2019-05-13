package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Random;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.screencapture.MediaProjectionScreenCapture;
import clickerbot.com.troop.clickerbot.touch.NativeTouchHandler;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.CrazyTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.PrestigeTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.RandomTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.TaskFactory;
import clickerbot.com.troop.clickerbot.tt2.tasks.test.ParseSkilllvlTest;

public class TT2Bot extends AbstractBot
{
    private static final String TAG = TT2Bot.class.getSimpleName();
    //private final ClanQuest clanQuest;

    private TouchInterface touchInput;

    /**
     * Holds the tasks to execute
     */
    private HashMap<Class, ExecuterTask> executerTaskHashMap;


    /**
     * handels everything releated about skills
     */
    private Skills skills;
    /**
     * handels everthing releated about heros
     */
    private Heros heros;
    /**
     * handels everything releated about bossfights
     */
    private Boss boss;
    /**
     * handels everything releated about prestige
     */
    private Prestige prestige;
    /**
     * handels everything releated about fairys
     */
    private Fairy fairy;

    private FlashZip flashZip;

    private RandomTaps randomTaps;

    private SubMenuOpenChecker subMenuOpenChecker;
    private SceneTransitionChecker sceneTransitionChecker;

    private BOS autoLevelBos;

    /**
     * time after a randomtap gets executed
     */
    private final int runRandomTapActivator = 1000;//ms
    /**
     * last time a randomtap got activated
     */
    private long lastRandomTapActivated = 0;

    Random rand = new Random();



    /**
     * holds the time last swordmaster lvl got executed
     */
    private long lastSwordMasterLeveled =0;
    /**
     * holds time last ui got updated
     */
    private long lastUiUpdate = 0;




    public TT2Bot(Context context, BotSettings botSettings, MediaProjectionScreenCapture mediaProjectionScreenCapture)
    {
        super(context,botSettings,mediaProjectionScreenCapture);

        touchInput = new NativeTouchHandler(botSettings.inputPath,botSettings.mouseInput);

        randomTaps = new RandomTaps(this,botSettings,touchInput);
        boss = new Boss(this,botSettings, touchInput);
        skills = new Skills(this,botSettings, touchInput);
        heros = new Heros(this,botSettings, touchInput,boss);
        prestige = new Prestige(this,botSettings, touchInput,boss);
        fairy = new Fairy(this,botSettings, touchInput,randomTaps);
        flashZip = new FlashZip(this,botSettings,touchInput);
        subMenuOpenChecker = new SubMenuOpenChecker(this,botSettings,touchInput);
        sceneTransitionChecker = new SceneTransitionChecker(this,botSettings,touchInput);
        //clanQuest = new ClanQuest(this,botSettings,touchInput);
        autoLevelBos = new BOS(this,botSettings,touchInput);

        executerTaskHashMap = new TaskFactory().getTasksmap(this,heros,skills,prestige,fairy,boss,autoLevelBos,randomTaps);
        mediaProjectionScreenCapture.setScreenCaptureCallBack(this::onScreenCapture);

        Log.d(TAG,"TT2Bot()");
    }

    public void executeTask(Class task)
    {
        execute(executerTaskHashMap.get(task));
    }

    public void putFirstAndExecuteTask(Class task) {
        putFirstAndExecute(executerTaskHashMap.get(task));
    }
    public void putTaskAtPos(Class task, int pos)
    {
        putAtPos(executerTaskHashMap.get(task),pos);
    }

    public boolean containsTask(Class task)
    {
        return containsT(executerTaskHashMap.get(task));
    }

    public void destroy()
    {
        Log.d(TAG,"destroy");
        //getScreeCapture().destroy();
        touchInput.close();
    }

    private long startTime;
    /**
     * start the bot
     */
    public void start()
    {
        Log.d(TAG,"start");
        randomTaps.createRandomTaps();
        lastTestExecuted = 0;
        touchInput.start();
        startTime = System.currentTimeMillis();
        super.start();


    }

    /**
     * stop the bot
     */
    public void stop()
    {
        Log.d(TAG,"stop");
        super.stop();
        touchInput.stop();
        Log.d(TAG,"######################################stopped");

    }

    /**
     * runs every xxx ms to fill the executer queue with tasks
     * @param tickCounter how often it has triggered
     */
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
            if(!prestige.checkIfRdyToExecute())
            {
                if (System.currentTimeMillis() - lastUiUpdate > 300) {
                    sendToUi();
                }
                //clanQuest.checkIfRdyToExecute();
                swordMasterRdyToExecute();
                heros.checkIfRdyToExecute();
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

    private void sendToUi() {
        long now = System.currentTimeMillis();
        long dif =  (prestige.getTimeSinceLastPrestige()+ prestige.getRandomTimeToPrestige() - now)/1000;
        String out;
        if (prestige.getTimeSinceLastPrestige() + prestige.getRandomTimeToPrestige() > now)
            out = "Prestige:" +getTimeString((int)dif)+"\n";
        else
            out = "Prestige: It's time\n";
        //Log.d(TAG, out);
        if (botSettings.bossFailedCount >0)
            out += "BossFailed:" + boss.getBossFailedCounter()+"/"+ botSettings.bossFailedCount + "\n";

        out += "Do: " + getActiveTaskName();
        UpdatePrestigeTime(out);
        lastUiUpdate = System.currentTimeMillis();
    }

    @Override
    void onScreenParserTick() {
        if (!WaitLock.clanquest.get()) {
            sceneTransitionChecker.checkIfRdyToExecute();
            subMenuOpenChecker.checkIfRdyToExecute();
        }

        fairy.checkIfFairyWindowOpen();
        heros.checkIfMenuOpen();
        if (botSettings.useFlashZip)
            flashZip.checkFlashZipAreasAndTap();
        if (System.currentTimeMillis() - startTime > 30000) {
            skills.checkIfRdyToExecute();
            boss.checkIfActiveBossFight();
        }
    }

    private String getTimeString(int dif)
    {
        int hour = ((int)dif / 60) / 60;
        int min = (int)(dif - (hour*60*60)) / 60;
        int sec = (int)(dif - (hour*60*60)) - (min *60);
        StringBuilder builder =new StringBuilder();
        if (hour <= 9)
            builder.append(0);
        builder.append(hour);
        builder.append(":");
        if (min <= 9)
            builder.append(0);
        builder.append(min);
        builder.append(":");
        if (sec <= 9)
            builder.append(0);
        builder.append(sec);
        return builder.toString();
    }

    private long lastTestExecuted;
    private void executeTests() {
        if (System.currentTimeMillis() - lastTestExecuted > 20000) {
            executeTask(PrestigeTask.class);
            lastTestExecuted = System.currentTimeMillis();
        }
    }

    private boolean swordMasterRdyToExecute()
    {
        if (botSettings.autoLvlSwordMaster && System.currentTimeMillis() - lastSwordMasterLeveled > botSettings.levelSwordMasterTime && boss.getBossState() != Boss.BossState.BossFightActive)
        {
            executeTask(LevelSwordMasterTask.class);
            lastSwordMasterLeveled = System.currentTimeMillis();
            return true;
        }
        return false;
    }



    /**
     * init the bot
     */
    public void init(ExecuterTask task)
    {
        startTime = System.currentTimeMillis();
        prestige.init(task);
        heros.init(task);
        boss.resetBossFailedCounter();
        //clanQuest.init(task);
        lastSwordMasterLeveled =0;
        randomTaps.init(task);


        Log.i(TAG,"init");
        skills.init(task);
    }

    public int getRandomX()
    {
        return rand.nextInt(20) + 20 +Coordinates.crazy_touch_pos.x;
    }

    public int getRandomY() {
        return rand.nextInt(20) + 20 + Coordinates.crazy_touch_pos.y;
    }



    /**
     * gets repeating called by the Screencapture
     * after a new frame is availible, reccomend not to hook in and do stuff there because
     * it slowdown the frame copy
     */
    @Override
    public void onScreenCapture() {
        //UpdateImage(getScreeCapture().getBitmap());

    }
}
