package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
    public final boolean isVipFairy;
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

    public final boolean collectGoldFairy;
    public final boolean collectReduceGoldFairy;
    public final boolean collectManaFairy;
    public final boolean collectSkillsFairy;
    public final boolean collectDiasFairy;

    public final boolean enableClanQuest;
    public final boolean clickOnBossHead;
    public final boolean clickOnBossBody;
    public final boolean clickOnBossLeftShoulder;
    public final boolean clickOnBossRightShoulder;
    public final boolean clickOnBossLeftArm;
    public final boolean clickOnBossRightArm;
    public final boolean clickOnBossLeftFoot;
    public final boolean clickOnBossRightFoot;

    public final List<Artifacts> artifactsListToLvl;
    public final boolean levelArtifacts;

    public final int tapOnBooksOfShadowsCount;
    public final int tapSTierCount;
    public final int tapATierCount;

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
        autoLvlBos = sharedPreferences.getBoolean(context.getString(R.string.autobos),false);
        autoClanQuest = sharedPreferences.getBoolean(context.getString(R.string.autoqc),false);
        autoLvlHeros = sharedPreferences.getBoolean(context.getString(R.string.autolvlheros),false);
        lvlHerosWhileInBossFight = sharedPreferences.getBoolean(context.getString(R.string.lvlheroswhileinbossfight),false);
        autoLvlSkills = sharedPreferences.getBoolean(context.getString(R.string.autolvlskills),false);
        clickOnFairys = sharedPreferences.getBoolean(context.getString(R.string.clickonfairyadds),false);
        isVipFairy = sharedPreferences.getBoolean(context.getString(R.string.acceptfairyadds),false);
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

        collectGoldFairy = sharedPreferences.getBoolean(context.getString(R.string.collectGoldFairy),false);
        collectReduceGoldFairy = sharedPreferences.getBoolean(context.getString(R.string.collectReduceGoldFairy),false);
        collectSkillsFairy = sharedPreferences.getBoolean(context.getString(R.string.collectskillsFairy),false);
        collectManaFairy = sharedPreferences.getBoolean(context.getString(R.string.collectManaFairy),false);
        collectDiasFairy = sharedPreferences.getBoolean(context.getString(R.string.collectdiasFairy),false);


        enableClanQuest = sharedPreferences.getBoolean(context.getString(R.string.enableclanquest),false);
        clickOnBossHead = sharedPreferences.getBoolean(context.getString(R.string.clickonhead),false);
        clickOnBossBody = sharedPreferences.getBoolean(context.getString(R.string.clickonbody),false);
        clickOnBossLeftArm = sharedPreferences.getBoolean(context.getString(R.string.clickonleftarm),false);
        clickOnBossRightArm = sharedPreferences.getBoolean(context.getString(R.string.clickonrightarm),false);
        clickOnBossLeftShoulder = sharedPreferences.getBoolean(context.getString(R.string.clickonlefshoulder),false);
        clickOnBossRightShoulder = sharedPreferences.getBoolean(context.getString(R.string.clickonrightshoulder),false);
        clickOnBossLeftFoot = sharedPreferences.getBoolean(context.getString(R.string.clickonleftfoot),false);
        clickOnBossRightFoot = sharedPreferences.getBoolean(context.getString(R.string.clickonrightfoot),false);
        Log.i(TAG, "Skills used HS:" + useHS + " DS:" + useDS+" Hom:"+useHOM+" FS:" + useFS + " WC:"+useWC +" SC: "+useSC);
        Log.i(TAG,"auto lvl heros:" + autoLvlHeros + " auto Tap:" + doAutoTap + " auto lvl skills"+ autoLvlSkills);
        Log.i(TAG, "time to prestige ms:" + timeToPrestige + " bossfailed:" + bossFailedCount);


        levelArtifacts = sharedPreferences.getBoolean(context.getString(R.string.lvlartifacts),false);
        tapOnBooksOfShadowsCount = (int)sharedPreferences.getLong(context.getString(R.string.taponbookofshadows),25);
        tapSTierCount = (int)sharedPreferences.getLong(context.getString(R.string.lvlstierarti),6);
        tapATierCount = (int)sharedPreferences.getLong(context.getString(R.string.lvlatierarti),1);
        artifactsListToLvl = new ArrayList<>();
        if(levelArtifacts)
        {
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlbookofshadows),false))
                artifactsListToLvl.add(Artifacts.BookOfShadows);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlchargedcard),false))
                artifactsListToLvl.add(Artifacts.ChargedCard);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlstof),false))
                artifactsListToLvl.add(Artifacts.StonesOfValrunes);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlchestofcontentment),false))
                artifactsListToLvl.add(Artifacts.ChestOfContentment);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlheroicshield),false))
                artifactsListToLvl.add(Artifacts.HeroicShield);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlbookofprophecy),false))
                artifactsListToLvl.add(Artifacts.BookOfProphecy);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlkhrysosbowl),false))
                artifactsListToLvl.add(Artifacts.KhrysosBowl);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlzakyntoscoin),false))
                artifactsListToLvl.add(Artifacts.ZakynthosCoin);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlgreatfaymedallion),false))
                artifactsListToLvl.add(Artifacts.GreatFayMedallion);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlnekosculpture),false))
                artifactsListToLvl.add(Artifacts.NekoSculpture);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlcoinofebizu),false))
                artifactsListToLvl.add(Artifacts.CoinsOfEbizu);
            if (sharedPreferences.getBoolean(context.getString(R.string.lvlbronzecompass),false))
                artifactsListToLvl.add(Artifacts.TheBronzedCompass);
            if (sharedPreferences.getBoolean(context.getString(R.string.evergrowingstack),false))
                artifactsListToLvl.add(Artifacts.EvergrowingStack);
            if (sharedPreferences.getBoolean(context.getString(R.string.FluteOfTheSoloist),false))
                artifactsListToLvl.add(Artifacts.FluteOfTheSoloist);
            if (sharedPreferences.getBoolean(context.getString(R.string.HeavenlySword),false))
                artifactsListToLvl.add(Artifacts.HeavenlySword);
            if (sharedPreferences.getBoolean(context.getString(R.string.DivineRetribution),false))
                artifactsListToLvl.add(Artifacts.DivineRetribution);
            if (sharedPreferences.getBoolean(context.getString(R.string.DrunkenHammer),false))
                artifactsListToLvl.add(Artifacts.DrunkenHammer);
            if (sharedPreferences.getBoolean(context.getString(R.string.SamosekSword),false))
                artifactsListToLvl.add(Artifacts.SamosekSword);
            if (sharedPreferences.getBoolean(context.getString(R.string.TheRetaliator),false))
                artifactsListToLvl.add(Artifacts.TheRetaliator);
            if (sharedPreferences.getBoolean(context.getString(R.string.StryfesPace),false))
                artifactsListToLvl.add(Artifacts.StryfesPace);
            if (sharedPreferences.getBoolean(context.getString(R.string.HerosBlade),false))
                artifactsListToLvl.add(Artifacts.HerosBlade);
            if (sharedPreferences.getBoolean(context.getString(R.string.TheSwordOfStorms),false))
                artifactsListToLvl.add(Artifacts.TheSwordOfStorms);
            if (sharedPreferences.getBoolean(context.getString(R.string.FuriesBow),false))
                artifactsListToLvl.add(Artifacts.FuriesBow);
            if (sharedPreferences.getBoolean(context.getString(R.string.CharmOfTheAncient),false))
                artifactsListToLvl.add(Artifacts.CharmOfTheAncient);
            if (sharedPreferences.getBoolean(context.getString(R.string.TinyTitanTree),false))
                artifactsListToLvl.add(Artifacts.TinyTitanTree);
            if (sharedPreferences.getBoolean(context.getString(R.string.HelmOfHermes),false))
                artifactsListToLvl.add(Artifacts.HelmOfHermes);
            if (sharedPreferences.getBoolean(context.getString(R.string.FruitOfEden),false))
                artifactsListToLvl.add(Artifacts.FruitOfEden);
            if (sharedPreferences.getBoolean(context.getString(R.string.InfluentialElixir),false))
                artifactsListToLvl.add(Artifacts.InfluentialElixir);
            if (sharedPreferences.getBoolean(context.getString(R.string.ORyansCharm),false))
                artifactsListToLvl.add(Artifacts.ORyansCharm);
            if (sharedPreferences.getBoolean(context.getString(R.string.HeartOfStorms),false))
                artifactsListToLvl.add(Artifacts.HeartOfStorms);
            if (sharedPreferences.getBoolean(context.getString(R.string.ApolloOrb),false))
                artifactsListToLvl.add(Artifacts.ApolloOrb);
            if (sharedPreferences.getBoolean(context.getString(R.string.EaringsOfPortara),false))
                artifactsListToLvl.add(Artifacts.EaringsOfPortara);
            if (sharedPreferences.getBoolean(context.getString(R.string.AvianFeather),false))
                artifactsListToLvl.add(Artifacts.AvianFeather);
            if (sharedPreferences.getBoolean(context.getString(R.string.CorruptedRuneHeart),false))
                artifactsListToLvl.add(Artifacts.CorruptedRuneHeart);
            if (sharedPreferences.getBoolean(context.getString(R.string.DurendalSword),false))
                artifactsListToLvl.add(Artifacts.DurendalSword);
            if (sharedPreferences.getBoolean(context.getString(R.string.HelheimSkull),false))
                artifactsListToLvl.add(Artifacts.HelheimSkull);
            if (sharedPreferences.getBoolean(context.getString(R.string.OathsBurden),false))
                artifactsListToLvl.add(Artifacts.OathsBurden);
            if (sharedPreferences.getBoolean(context.getString(R.string.CrownOfConstellation),false))
                artifactsListToLvl.add(Artifacts.CrownOfConstellation);
            if (sharedPreferences.getBoolean(context.getString(R.string.TitaniasSceptre),false))
                artifactsListToLvl.add(Artifacts.TitaniasSceptre);
            if (sharedPreferences.getBoolean(context.getString(R.string.FaginsGrip),false))
                artifactsListToLvl.add(Artifacts.FaginsGrip);
            if (sharedPreferences.getBoolean(context.getString(R.string.RingOfCallisto),false))
                artifactsListToLvl.add(Artifacts.RingOfCallisto);
            if (sharedPreferences.getBoolean(context.getString(R.string.BladeOfDamocles),false))
                artifactsListToLvl.add(Artifacts.BladeOfDamocles);
            if (sharedPreferences.getBoolean(context.getString(R.string.HelmetOfMadness),false))
                artifactsListToLvl.add(Artifacts.HelmetOfMadness);
            if (sharedPreferences.getBoolean(context.getString(R.string.TitaniumPlating),false))
                artifactsListToLvl.add(Artifacts.TitaniumPlating);
            if (sharedPreferences.getBoolean(context.getString(R.string.MoonlightBracelet),false))
                artifactsListToLvl.add(Artifacts.MoonlightBracelet);
            if (sharedPreferences.getBoolean(context.getString(R.string.AmethystStaff),false))
                artifactsListToLvl.add(Artifacts.AmethystStaff);
            if (sharedPreferences.getBoolean(context.getString(R.string.SwordOfTheRoyals),false))
                artifactsListToLvl.add(Artifacts.SwordOfTheRoyals);
            if (sharedPreferences.getBoolean(context.getString(R.string.SpearitsVigil),false))
                artifactsListToLvl.add(Artifacts.SpearitsVigil);
            if (sharedPreferences.getBoolean(context.getString(R.string.TheCobaltPlate),false))
                artifactsListToLvl.add(Artifacts.TheCobaltPlate);
            if (sharedPreferences.getBoolean(context.getString(R.string.SigilsOfJudgement),false))
                artifactsListToLvl.add(Artifacts.SigilsOfJudgement);
            if (sharedPreferences.getBoolean(context.getString(R.string.FoliageOfTheKeeper),false))
                artifactsListToLvl.add(Artifacts.FoliageOfTheKeeper);
            if (sharedPreferences.getBoolean(context.getString(R.string.InvadersGjalarhorn),false))
                artifactsListToLvl.add(Artifacts.InvadersGjalarhorn);
            if (sharedPreferences.getBoolean(context.getString(R.string.TitansMask),false))
                artifactsListToLvl.add(Artifacts.TitansMask);
            if (sharedPreferences.getBoolean(context.getString(R.string.RoyalToxin),false))
                artifactsListToLvl.add(Artifacts.RoyalToxin);
            if (sharedPreferences.getBoolean(context.getString(R.string.LaborersPendant),false))
                artifactsListToLvl.add(Artifacts.LaborersPendant);
            if (sharedPreferences.getBoolean(context.getString(R.string.BringerOfRagnarok),false))
                artifactsListToLvl.add(Artifacts.BringerOfRagnarok);
            if (sharedPreferences.getBoolean(context.getString(R.string.ParchmentOfForesight),false))
                artifactsListToLvl.add(Artifacts.ParchmentOfForesight);
            if (sharedPreferences.getBoolean(context.getString(R.string.ElixirOfEden),false))
                artifactsListToLvl.add(Artifacts.ElixirOfEden);

        }

    }


}
