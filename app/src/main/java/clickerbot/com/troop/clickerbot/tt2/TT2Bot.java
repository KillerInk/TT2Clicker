package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.Random;

import clickerbot.com.troop.clickerbot.ThreadController;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.screencapture.MediaProjectionScreenCapture;
import clickerbot.com.troop.clickerbot.touch.NativeTouchHandler;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.ClanQuestTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.CrazyTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.InitTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.PrestigeTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.RandomTapTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.TaskFactory;
import clickerbot.com.troop.clickerbot.tt2.tasks.test.ExtractArtifactsImageTask;

/**
 * Main class that handels all Stuff
 */
public class TT2Bot implements ThreadController.TickInterface
{

    public interface UpdateUi
    {
        void updatePrestigeTime(String time);
        void updateImage(Bitmap bitmap);
    }

    private UpdateUi updateUi;

    private static final String TAG = TT2Bot.class.getSimpleName();
    private final ClanQuest clanQuest;

    //Interface to the FakeTouchInput
    private TouchInterface touchInput;

    private ThreadController threadController;

    /**
     * Holds the tasks to execute
     * Taks gets execute one by one.
     */
    private HashMap<Class, ExecuterTask> executerTaskHashMap;


    private BotSettings botSettings;

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

    /**
     * handels flashzip detection and click
     */
    private FlashZip flashZip;

    /**
     * handels fairy collect clicks, fast on screen clicks and aaw clicks
     */
    private RandomTaps randomTaps;

    /**
     * checks if a fail click happen and opened a submenu and close it in that case and let all tasks wait.
     */
    private SubMenuOpenChecker subMenuOpenChecker;
    /**
     * checks if a screen transiotion happen and let all tasks wait till its over
     */
    private SceneTransitionChecker sceneTransitionChecker;
    /**
     * Detects the amount of avail mana in %
     */
    private ManaDetector manaDetector;

    /**
     * handels autolevel bos after a prestige
     */
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

    /**
     * checks if the daily reward is avail and collect it
     */
    private CollectDailyReward collectDailyReward;

    private CollectInBoxRewward collectInBoxRewward;

    private ArtifactsColorExtractor artifactsColorExtractor;

    /**
     *
     * @param context from the Application
     * @param botSettings to apply
     * @param mediaProjectionScreenCapture to capture screenframes and get colors from it
     */
    public TT2Bot(Context context, BotSettings botSettings, MediaProjectionScreenCapture mediaProjectionScreenCapture)
    {
        this.botSettings = botSettings;
        threadController =new ThreadController(context, mediaProjectionScreenCapture,this);
        touchInput = new NativeTouchHandler(botSettings.inputPath,botSettings.mouseInput);
        touchInput.start();
        randomTaps = new RandomTaps(this,botSettings,touchInput);
        boss = new Boss(this,botSettings, touchInput);

        heros = new Heros(this,botSettings, touchInput,boss);
        prestige = new Prestige(this,botSettings, touchInput,boss);
        fairy = new Fairy(this,botSettings, touchInput,randomTaps);
        flashZip = new FlashZip(this,botSettings,touchInput);
        subMenuOpenChecker = new SubMenuOpenChecker(this,botSettings,touchInput);
        sceneTransitionChecker = new SceneTransitionChecker(this,botSettings,touchInput);
        clanQuest = new ClanQuest(this,botSettings,touchInput);
        autoLevelBos = new BOS(this,botSettings,touchInput);
        manaDetector = new ManaDetector(this,botSettings,touchInput);
        skills = new Skills(this,botSettings, touchInput, manaDetector);
        collectDailyReward = new CollectDailyReward(this,botSettings,touchInput);
        collectInBoxRewward = new CollectInBoxRewward(this,botSettings,touchInput);
        artifactsColorExtractor = new ArtifactsColorExtractor(this,botSettings,touchInput);

        //create the different tasks
        executerTaskHashMap = new TaskFactory().getTasksmap(this,heros,skills,prestige,fairy,boss,autoLevelBos,randomTaps,collectDailyReward,collectInBoxRewward, artifactsColorExtractor,clanQuest);
        //mediaProjectionScreenCapture.setScreenCaptureCallBack(this::onScreenCapture);

        Log.d(TAG,"TT2Bot()");
    }

    public MediaProjectionScreenCapture getScreeCapture() {
        return threadController.getScreeCapture();
    }

    public Context getContext() {
        return threadController.getContext();
    }

    /**
     * add a new task to the executer queue
     * @param task
     */
    public void executeTask(Class task)
    {
        threadController.execute(executerTaskHashMap.get(task));
    }

    /**
     * Add a new Task to the first position in the queue
     * @param task
     */
    public void putFirstAndExecuteTask(Class task) {
        threadController.putFirstAndExecute(executerTaskHashMap.get(task));
    }

    public void clearExecuterQueue(){ threadController.clearExecuterQueue(); }

    public boolean getIsRunning()
    {
        return threadController.getIsRunning();
    }

    /**
     * Add a new Task to the specific position in the queue
     * @param task
     * @param pos
     */
    public void putTaskAtPos(Class task, int pos)
    {
        threadController.putAtPos(executerTaskHashMap.get(task),pos);
    }

    /**
     * checks if the task is already added to the queue
     * @param task
     * @return
     */
    public boolean containsTask(Class task)
    {
        return threadController.containsT(executerTaskHashMap.get(task));
    }

    /**
     * destroy the bot
     */
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
        startTime = System.currentTimeMillis();
        WaitLock.resetWaitLocks();
        threadController.start();
        Log.d(TAG, "clanquest: " + botSettings.enableClanQuest);
        if (botSettings.enableClanQuest)
            executeTask(ClanQuestTask.class);
    }

    /**
     * stop the bot
     */
    public void stop()
    {
        if (updateUi != null)
            updateUi.updatePrestigeTime("Stopping");
        Log.d(TAG,"stop");
        threadController.stop();
        Log.d(TAG,"######################################stopped");
        if (updateUi != null)
            updateUi.updatePrestigeTime("Stopped");
    }

    /**
     * runs every xxx ms to fill the executer queue with tasks
     *
     * @param tickCounter how often it has triggered
     */
    @Override
    public void onTick(long tickCounter) {
        if (!botSettings.enableClanQuest) {
            //as first action on start run the InitTask
            if (tickCounter == 1) {
                executeTask(InitTask.class);
            } else if (botSettings.runTests) // runs only if test are activated in devsettings. can get used to test a new feature
            {
                executeTests();
            } else {
                if (!prestige.checkIfRdyToExecute()) {
                    if (System.currentTimeMillis() - lastUiUpdate > 300) {
                        //update the timer, bossfail counter and the currently executed task
                        sendToUi();
                    }
                    //clanQuest.checkIfRdyToExecute();
                    swordMasterRdyToExecute();
                    heros.checkIfRdyToExecute();
                    if (System.currentTimeMillis() - lastRandomTapActivated > runRandomTapActivator) {
                        executeTask(RandomTapTask.class);
                        fairy.executeTapFairys();
                        lastRandomTapActivated = System.currentTimeMillis();
                    } else if (botSettings.doAutoTap) {
                        executeTask(CrazyTapTask.class);
                    }
                }
            }
        }

    }

    private void sendToUi() {
        long now = System.currentTimeMillis();
        String out = new String();
        if (botSettings.timeToPrestige > 0) {
            long dif = (prestige.getTimeSinceLastPrestige() + prestige.getRandomTimeToPrestige() - now) / 1000;

            if (prestige.getTimeSinceLastPrestige() + prestige.getRandomTimeToPrestige() > now)
                out = "Prestige:" + getTimeString((int) dif) + "\n";
            else
                out = "Prestige: It's time\n";
        }
        else
        {
            out = "Run: " + getTimeString((now - startTime)/1000) + "\n";
        }
        //Log.d(TAG, out);
        if (botSettings.bossFailedCount >0)
            out += "BossFailed:" + boss.getBossFailedCounter()+"/"+ botSettings.bossFailedCount + "\n";

        out += "Do: " + threadController.getActiveTaskName();
        UpdatePrestigeTime(out);
        lastUiUpdate = System.currentTimeMillis();
    }

    @Override
    public void onScreenParserTick() {
        if (!botSettings.enableClanQuest) {
            if (!WaitLock.clanquest.get()) {
                sceneTransitionChecker.checkIfRdyToExecute();
                subMenuOpenChecker.checkIfRdyToExecute();


                try {
                    fairy.checkIfFairyWindowOpen();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                heros.checkIfMenuOpen();
                if (botSettings.useFlashZip)
                    flashZip.checkFlashZipAreasAndTap();
                if (System.currentTimeMillis() - startTime > 30000) {
                    skills.checkIfRdyToExecute();
                    boss.checkIfActiveBossFight();
                }
                manaDetector.checkIfRdyToExecute();
                collectDailyReward.checkIfRdyToExecute();
                collectInBoxRewward.checkIfRdyToExecute();
            }
        }
    }

    private String getTimeString(long dif)
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
        if (System.currentTimeMillis() - lastTestExecuted > 120000) {
            executeTask(ExtractArtifactsImageTask.class);
            //executeTask(ClanQuestTask.class);
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

    public void setUpdateUiCallBack(UpdateUi updateUiCallBack)
    {
        this.updateUi = updateUiCallBack;
    }

    public void UpdatePrestigeTime(String out)
    {
        if (updateUi != null)
        {
            updateUi.updatePrestigeTime(out);
        }
    }

    public void UpdateImage(Bitmap bitmap)
    {
        if (updateUi != null)
        {
            updateUi.updateImage(bitmap);
        }
    }

    public void resetTickCounter()
    {
        threadController.resetTickCounter();
    }

}
