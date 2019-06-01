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
    public final boolean unlockHS;
    public long hsMaxLvl;
    /**
     * activate deadly strike
     */
    public final boolean useDS;
    public final boolean unlockDS;
    public long dsMaxLvl;
    /**
     * activate hand of midas
     */
    public final boolean useHOM;
    public final boolean unlockHOM;
    public long homMaxLvl;
    /**
     * activate fire sword
     */
    public final boolean useFS;
    public final boolean unlockFS;
    public long fsMaxLVL;
    /**
     * activate war cry
     */
    public final boolean useWC;
    public final boolean unlockWC;
    public long wcMaxLvl;
    /**
     * activate shadowclone
     */
    public final boolean useSC;
    public final boolean unlockSC;
    public long scMaxLvl;
    /**
     * time till prestige in ms
     */
    public final long timeToPrestige;
    public final long randomTimeToPrestige;

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

    public final boolean lvlHerosWhileInBossFight;
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


    public final boolean autoClanQuest;

    /**
     * if true only the added test executed.
     * it help to test new ExecuterTasks/Functions
     */
    public final boolean runTests;

    /**
     * time after that the first 6 heros gets leveld
     */
    public final  long levelTop9HeroTime;
    /**
     * time after that all heros gets leveld
     */
    public final  long levelAllHeroTime;
    public final  long levelAllHeroCount;
    /**
     * time after that the swordmaster gets leveld
     */
    public final  long levelSwordMasterTime;

    //unused
    public final long swipeLength;

    public final String inputPath;

    public final boolean mouseInput;
    public final boolean autoLvlBos;

    public final int skillManaLimit;

    public BotSettings(SharedPreferences sharedPreferences,Context context)
    {
        useHS = sharedPreferences.getBoolean(context.getString(R.string.useHs),false);
        useDS = sharedPreferences.getBoolean(context.getString(R.string.useDS),false);
        useHOM = sharedPreferences.getBoolean(context.getString(R.string.useHom),false);
        useFS = sharedPreferences.getBoolean(context.getString(R.string.useFS),false);
        useWC = sharedPreferences.getBoolean(context.getString(R.string.useWC),false);
        useSC = sharedPreferences.getBoolean(context.getString(R.string.useSC),false);
        unlockHS = sharedPreferences.getBoolean(context.getString(R.string.unlockHs),false);
        unlockDS = sharedPreferences.getBoolean(context.getString(R.string.unlockDS),false);
        unlockHOM = sharedPreferences.getBoolean(context.getString(R.string.unlockHom),false);
        unlockFS = sharedPreferences.getBoolean(context.getString(R.string.unlockFS),false);
        unlockWC = sharedPreferences.getBoolean(context.getString(R.string.unlockWC),false);
        unlockSC = sharedPreferences.getBoolean(context.getString(R.string.unlockSC),false);
        doAutoTap = sharedPreferences.getBoolean(context.getString(R.string.autoTap),false);
        mouseInput = sharedPreferences.getBoolean(context.getString(R.string.mouseinput),true);
        autoLvlBos = sharedPreferences.getBoolean(context.getString(R.string.autobos),true);
        autoClanQuest = sharedPreferences.getBoolean(context.getString(R.string.autoqc),false);
        autoLvlHeros = sharedPreferences.getBoolean(context.getString(R.string.autolvlheros),false);
        lvlHerosWhileInBossFight = sharedPreferences.getBoolean(context.getString(R.string.lvlheroswhileinbossfight),false);
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
        randomTimeToPrestige = sharedPreferences.getLong(context.getString(R.string.prestigerandomtime),10);
        bossFailedCount = sharedPreferences.getLong(context.getString(R.string.prestigeafterbossfail),3);
        swipeLength = sharedPreferences.getLong(context.getString(R.string.swipelength),100);
        levelTop9HeroTime = sharedPreferences.getLong(context.getString(R.string.levelherostime),120)*1000;
        levelAllHeroTime = sharedPreferences.getLong(context.getString(R.string.levelallherostime),9)*60*1000;
        levelSwordMasterTime = sharedPreferences.getLong(context.getString(R.string.levelswordmastertime),3)*60*1000;
        levelAllHeroCount = sharedPreferences.getLong(context.getString(R.string.levelallheroscount),3);
        hsMaxLvl = sharedPreferences.getLong(context.getString(R.string.hsmaxlvl),0);
        dsMaxLvl = sharedPreferences.getLong(context.getString(R.string.dsmaxlvl),0);
        homMaxLvl = sharedPreferences.getLong(context.getString(R.string.hommaxlvl),0);
        fsMaxLVL = sharedPreferences.getLong(context.getString(R.string.fsmaxlvl),0);
        wcMaxLvl = sharedPreferences.getLong(context.getString(R.string.wcmaxlvl),0);
        scMaxLvl = sharedPreferences.getLong(context.getString(R.string.scmaxlvl),0);
        skillManaLimit = (int)sharedPreferences.getLong(context.getString(R.string.maxskillpercentage),0);
        inputPath = sharedPreferences.getString(context.getString(R.string.inputpath),"/dev/input/event6");
        Log.i(TAG, "Skills used HS:" + useHS + " DS:" + useDS+" Hom:"+useHOM+" FS:" + useFS + " WC:"+useWC +" SC: "+useSC);
        Log.i(TAG,"auto lvl heros:" + autoLvlHeros + " auto Tap:" + doAutoTap + " auto lvl skills"+ autoLvlSkills);
        Log.i(TAG, "time to prestige ms:" + timeToPrestige + " bossfailed:" + bossFailedCount);

    }


}
