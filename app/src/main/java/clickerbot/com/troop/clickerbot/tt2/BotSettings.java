package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import clickerbot.com.troop.clickerbot.R;

public class BotSettings {

    private final String TAG = BotSettings.class.getSimpleName();

    /**
     * activate heavenly strike
     */
    public final boolean useHS;
    /**
     * activate deadly strike
     */
    public final boolean useDS;
    /**
     * activate hand of midas
     */
    public final boolean useHOM;
    /**
     * activate fire sword
     */
    public final boolean useFS;
    /**
     * activate war cry
     */
    public final boolean useWC;
    /**
     * activate shadowclone
     */
    public final boolean useSC;
    /**
     * time till prestige in ms
     */
    public final long timeToPrestige;

    /**
     *after how many boss fails it should prestige.
     *this overrides the prestigetime,
     */
    public final long bossFailedCount;

    /**
     * do crazytap(mainly used for petbuilds?)
     */
    public final boolean doAutoTap;
    /**
     * autmatic level up heros
     */
    public final boolean autoLvlHeros;
    /**
     * automatic level up skills
     * need later to add specfic wich skill should get level about how many levels
     */
    public final boolean autoLvlSkills;
    /**
     * click on fairys, if true fairy taps gets added to random taps
     */
    public final boolean clickOnFairys;
    /**
     * accept fairy adds, should get used with VIP!
     */
    public final boolean acceptFairyAdds;
    /**
     * do auto prestige
     */
    public final boolean autoPrestige;
    /**
     * continouse level up swordmaster
     */
    public final boolean autoLvlSwordMaster;
    /**
     * if true astral awakening touch points gets added to randomtaps
     */
    public final boolean useAAW;
    /**
     * if true coordinate offensive touch points get added to randomtaps
     */
    public final boolean useCO;
    /**
     * if true heart of midas touch point get added to randomtaps
     */
    public final boolean usePHOM;
    /**
     * if true egg touch point get added to randomtaps
     */
    public final boolean collectEggs;
    /**
     * if true flashzip touch points get added to randomtaps
     */
    public final boolean useFlashZip;

    /**
     * if true only the added test executed.
     * it help to test new ExecuterTasks/Functions
     */
    public final boolean runTests;

    /**
     * time how often the mainLoop should trigger in ms
     */
    public final long mainLooperSleepTime;
    /**
     * time how often a screenCapture should trigger
     */
    public final long captureFrameSleepTime;
    /**
     * the delay between clicks for random and crazy taps
     * if its to fast it can happen that the rootshells get flooded and die.
     * for 1 shell you should take a value around 120-150ms.
     * with using more shells you can decrase the time
     */
    public final long clickSleepTime;

    /**
     * number of shells that get created to run random and crazy taps
     */
    public final long shellcount;

    /**
     * time after that the first 6 heros gets leveld
     */
    public final  long levelTop6HeroTime;
    /**
     * time after that all heros gets leveld
     */
    public final  long levelAllHeroTime;
    /**
     * time after that the swordmaster gets leveld
     */
    public final  long levelSwordMasterTime;

    //unused
    public final long swipeLength;

    public BotSettings(SharedPreferences sharedPreferences,Context context)
    {
        useHS = sharedPreferences.getBoolean(context.getString(R.string.useHs),false);
        useDS = sharedPreferences.getBoolean(context.getString(R.string.useDS),false);
        useHOM = sharedPreferences.getBoolean(context.getString(R.string.useHom),false);
        useFS = sharedPreferences.getBoolean(context.getString(R.string.useFS),false);
        useWC = sharedPreferences.getBoolean(context.getString(R.string.useWC),false);
        useSC = sharedPreferences.getBoolean(context.getString(R.string.useSC),false);
        doAutoTap = sharedPreferences.getBoolean(context.getString(R.string.autoTap),false);
        autoLvlHeros = sharedPreferences.getBoolean(context.getString(R.string.autolvlheros),false);
        autoLvlSkills = sharedPreferences.getBoolean(context.getString(R.string.autolvlskills),false);
        clickOnFairys = sharedPreferences.getBoolean(context.getString(R.string.clickonfairyadds),false);
        acceptFairyAdds = sharedPreferences.getBoolean(context.getString(R.string.acceptfairyadds),false);
        autoPrestige = sharedPreferences.getBoolean(context.getString(R.string.auto_prestige),false);
        autoLvlSwordMaster = sharedPreferences.getBoolean(context.getString(R.string.autolvlswordmaster),false);
        useAAW = sharedPreferences.getBoolean(context.getString(R.string.useaaw),false);
        useCO = sharedPreferences.getBoolean(context.getString(R.string.useco),false);
        usePHOM = sharedPreferences.getBoolean(context.getString(R.string.usephom),false);
        collectEggs = sharedPreferences.getBoolean(context.getString(R.string.autocollectegg),false);
        useFlashZip = sharedPreferences.getBoolean(context.getString(R.string.useflashzip),false);
        runTests = sharedPreferences.getBoolean(context.getString(R.string.runtests),false);
        timeToPrestige = sharedPreferences.getLong(context.getString(R.string.prestigetime),60)*60*1000;
        bossFailedCount = sharedPreferences.getLong(context.getString(R.string.prestigeafterbossfail),3);
        mainLooperSleepTime = sharedPreferences.getLong(context.getString(R.string.mainLooperSleep),500);
        captureFrameSleepTime = sharedPreferences.getLong(context.getString(R.string.captureFrameSleep),800);
        clickSleepTime = sharedPreferences.getLong(context.getString(R.string.clicksleeptime),80);
        shellcount = sharedPreferences.getLong(context.getString(R.string.shellcount),10) +1;
        swipeLength = sharedPreferences.getLong(context.getString(R.string.swipelength),100);
        levelTop6HeroTime = sharedPreferences.getLong(context.getString(R.string.levelherostime),3)*60*1000;
        levelAllHeroTime = sharedPreferences.getLong(context.getString(R.string.levelallherostime),9)*60*1000;
        levelSwordMasterTime = sharedPreferences.getLong(context.getString(R.string.levelswordmastertime),3)*60*1000;
        Log.i(TAG, "Skills used HS:" + useHS + " DS:" + useDS+" Hom:"+useHOM+" FS:" + useFS + " WC:"+useWC +" SC: "+useSC);
        Log.i(TAG,"auto lvl heros:" + autoLvlHeros + " auto Tap:" + doAutoTap + " auto lvl skills"+ autoLvlSkills);
        Log.i(TAG, "time to prestige ms:" + timeToPrestige + " bossfailed:" + bossFailedCount);

    }


}
