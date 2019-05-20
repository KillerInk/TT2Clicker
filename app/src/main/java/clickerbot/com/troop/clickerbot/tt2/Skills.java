package clickerbot.com.troop.clickerbot.tt2;

import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class Skills extends Menu {
    private final String TAG = Skills.class.getSimpleName();

    private final Skill hs;
    private final Skill ds;
    private final Skill hom;
    private final Skill fs;
    private final Skill wc;
    private final Skill sc;
    private final SkillLevelParser skillLevelParser;
    private final ManaDetector manaDetector;


    public Skills(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell, ManaDetector manaDetector) {
        super(ibot, botSettings, rootShell);
        hs =new Skill(bot,rootShell,Skill.SkillType.HS,(int)botSettings.hsMaxLvl);
        ds =new Skill(bot,rootShell,Skill.SkillType.DS,(int)botSettings.dsMaxLvl);
        hom =new Skill(bot,rootShell,Skill.SkillType.HOM,(int)botSettings.homMaxLvl);
        fs =new Skill(bot,rootShell,Skill.SkillType.FS,(int)botSettings.fsMaxLVL);
        wc =new Skill(bot,rootShell,Skill.SkillType.WC,(int)botSettings.wcMaxLvl);
        sc =new Skill(bot,rootShell,Skill.SkillType.SC,(int)botSettings.scMaxLvl);
        skillLevelParser = new SkillLevelParser(ibot);
        this.manaDetector = manaDetector;
    }

    @Override
    public void init(ExecuterTask task) {
        hs.setSkillLvl(0);
        ds.setSkillLvl(0);
        hom.setSkillLvl(0);
        fs.setSkillLvl(0);
        wc.setSkillLvl(0);
        sc.setSkillLvl(0);
        WaitLock.checkForErrorAndWait();
        if (isMenuOpen() || isMenuMaximized())
            closeMenu();
        WaitLock.checkForErrorAndWait();
        checkSkillsUnlocked();
        Log.d(TAG,"init autolvl skills:" + botSettings.autoLvlSkills);
        if (botSettings.autoLvlSkills) {
            if (!skipLevelUpSkills()) {

                openSwordMasterMenu(task);
                if (!isMenuMaximized())
                    maximiseMenu(task);
                try {
                    gotToTopMaximised(task);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WaitLock.checkForErrorAndWait();
                try {
                    Thread.sleep(1000);
                    WaitLock.checkForErrorAndWait();
                    doLongerSingelTap(Coordinates.Menu_SwordMasterPos,"on minimize");
                    Thread.sleep(50);
                    WaitLock.checkForErrorAndWait();
                    Thread.sleep(1000);
                    WaitLock.checkForErrorAndWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    WaitLock.checkForErrorAndWait();
                    Thread.sleep(1000);
                    WaitLock.checkForErrorAndWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                skillLevelParser.parseAllSkillLevels(hs, ds, hom,fs, wc,sc);
                WaitLock.checkForErrorAndWait();
                levelUpSkills(task);
                WaitLock.checkForErrorAndWait();
                try {
                    Thread.sleep(50);
                    WaitLock.checkForErrorAndWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                closeSwordMasterMenu(task);
            }
        }
    }

    @Override
    boolean checkIfRdyToExecute() {
        hs.detectSkillState();
        ds.detectSkillState();
        hom.detectSkillState();
        fs.detectSkillState();
        wc.detectSkillState();
        sc.detectSkillState();
        if (manaDetector.getManaPercentage() >= botSettings.skillManaLimit) {
            if (botSettings.useDS)
                ds.activateSkill();
            if (botSettings.useHOM)
                hom.activateSkill();
            if (botSettings.useFS)
                fs.activateSkill();
            if (botSettings.useWC)
                wc.activateSkill();
            if (botSettings.useSC)
                sc.activateSkill();
            if ((ds.getSkillState() == Skill.SkillState.active && botSettings.useDS)
                    && (hom.getSkillState() == Skill.SkillState.active && botSettings.useHOM)
                    && (fs.getSkillState() == Skill.SkillState.active && botSettings.useFS)
                    && (wc.getSkillState() == Skill.SkillState.active && botSettings.useWC)
                    && (sc.getSkillState() == Skill.SkillState.active && botSettings.useSC)
                    && botSettings.useHS
            )
                hs.activateSkill();
        }
        return false;
    }

    public void testSkillLvlDetection(ExecuterTask task)
    {
        openSwordMasterMenu(task);

        maximiseMenu(task);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        skillLevelParser.parseAllSkillLevels(hs, ds, hom,fs, wc,sc);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WaitLock.checkForErrorAndWait();
        minimiseMenu(task);
        closeSwordMasterMenu(task);

    }

    private boolean skipLevelUpSkills()
    {
            return false;
    }

    public void lvlSwordMaster(ExecuterTask task)
    {
        Log.d(TAG,"lvlSwordMaster");
        openSwordMasterMenu(task);
        try {
            int loopbreaker = 0;
            while (!isMenuTopReached() && loopbreaker < 25 && !task.cancelTask && Menu.MenuOpen.get()) {
                loopbreaker++;
                swipeUp();
                Thread.sleep(200);
            }
            doSingelTap(Coordinates.Menu_Minimized_SwordMaster,"on minimize");

            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeSwordMasterMenu(task);
    }

    private void levelUpSkills(ExecuterTask task)
    {
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockHS)
            hs.levelSkill(task);
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockDS)
            ds.levelSkill(task);
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockHOM)
            hom.levelSkill(task);
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockFS)
            fs.levelSkill(task);
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockWC)
            wc.levelSkill(task);
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockSC)
            sc.levelSkill(task);
    }



    private void checkSkillsUnlocked()
    {
        Log.d(TAG,"checkSkillsUnlocked");
        hs.checkIfSkillUnlocked();
        ds.checkIfSkillUnlocked();
        hom.checkIfSkillUnlocked();
        fs.checkIfSkillUnlocked();
        wc.checkIfSkillUnlocked();
        sc.checkIfSkillUnlocked();

        //Log.d(TAG, "Skills Unlocked HS:" + hsUnlocked + " DS:" + dsUnlocked+" Hom:"+homUnlocked+" FS:" + fsUnlocked + " WC:"+wcUnlocked +" SC: "+scUnlocked);
    }

}
