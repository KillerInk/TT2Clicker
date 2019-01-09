package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import clickerbot.com.troop.clickerbot.R;

public class BotSettings {

    private final String TAG = BotSettings.class.getSimpleName();

    public final boolean useHS;
    public final boolean useDS;
    public final boolean useHOM;
    public final boolean useFS;
    public final boolean useWC;
    public final boolean useSC;
    /**
     * time till prestige in ms
     */
    public final long timeToPrestige;

    // after how many boss fails it should prestige.
    //this overrides the prestigetime,
    public final long bossFailedCount;

    public final boolean doAutoTap;
    public final boolean autoLvlHeros;
    public final boolean autoLvlSkills;
    public final boolean clickOnFairys;
    public final boolean acceptFairyAdds;
    public final boolean autoPrestige;
    public final boolean autoLvlSwordMaster;
    public final boolean useAAW;
    public final boolean useCO;
    public final boolean usePHOM;
    public final boolean collectEggs;
    public final boolean useFlashZip;


    public final boolean runTests;

    public final long mainLooperSleepTime;
    public final long captureFrameSleepTime;
    public final long clickSleepTime;

    public final  long levelTop6HeroTime;
    public final  long levelAllHeroTime;
    public final  long levelSwordMasterTime;

    public final long shellcount;

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
