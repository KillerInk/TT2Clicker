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


    public Skills(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        hs =new Skill(bot,rootShell,Skill.SkillType.HS,botSettings.unlockHS, botSettings.useHS,(int)botSettings.hsMaxLvl);
        ds =new Skill(bot,rootShell,Skill.SkillType.DS,botSettings.unlockDS, botSettings.useDS,(int)botSettings.dsMaxLvl);
        hom =new Skill(bot,rootShell,Skill.SkillType.HOM,botSettings.unlockHOM, botSettings.useHOM,(int)botSettings.homMaxLvl);
        fs =new Skill(bot,rootShell,Skill.SkillType.FS,botSettings.unlockFS, botSettings.useFS,(int)botSettings.fsMaxLVL);
        wc =new Skill(bot,rootShell,Skill.SkillType.WC,botSettings.unlockWC, botSettings.useWC,(int)botSettings.wcMaxLvl);
        sc =new Skill(bot,rootShell,Skill.SkillType.SC,botSettings.unlockSC, botSettings.useSC,(int)botSettings.scMaxLvl);
        skillLevelParser = new SkillLevelParser(ibot);
    }


    public void activateAllSkills() throws IOException, InterruptedException {
        hs.activateSkill();
        ds.activateSkill();
        hom.activateSkill();
        fs.activateSkill();
        wc.activateSkill();
        sc.activateSkill();
    }

    @Override
    public void init() {
        hs.setSkillLvl(0);
        ds.setSkillLvl(0);
        hom.setSkillLvl(0);
        fs.setSkillLvl(0);
        wc.setSkillLvl(0);
        sc.setSkillLvl(0);
        WaitLock.checkForErrorAndWait();
        closeMenu();
        WaitLock.checkForErrorAndWait();
        checkSkillsUnlocked();
        Log.d(TAG,"init autolvl skills:" + botSettings.autoLvlSkills);
        if (botSettings.autoLvlSkills) {
            if (!skipLevelUpSkills()) {

                openSwordMasterMenu();
                while (!isMenuTopReached() && Menu.MenuOpen.get()) {
                    WaitLock.checkForErrorAndWait();
                    try {
                        touchInput.swipeVertical(Heros.lvlFIrsHeroButton_click, Heros.lvlThirdHeroButton_click);
                        WaitLock.checkForErrorAndWait();

                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    WaitLock.checkForErrorAndWait();
                }

                //make sure we are truly on top
                WaitLock.checkForErrorAndWait();
                try {
                    touchInput.swipeVertical(Heros.lvlFIrsHeroButton_click, Heros.lvlThirdHeroButton_click);
                    WaitLock.checkForErrorAndWait();

                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                WaitLock.checkForErrorAndWait();

                maximiseMenu();

                try {
                    WaitLock.checkForErrorAndWait();
                    Thread.sleep(5000);
                    WaitLock.checkForErrorAndWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                skillLevelParser.parseAllSkillLevels(hs, ds, hom,fs, wc,sc);
                WaitLock.checkForErrorAndWait();
                levelUpSkills();
                WaitLock.checkForErrorAndWait();
                try {
                    Thread.sleep(50);
                    WaitLock.checkForErrorAndWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                minimiseMenu();
                closeSwordMasterMenu();
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
        return false;
    }

    public void testSkillLvlDetection()
    {
        openSwordMasterMenu();

        maximiseMenu();

        //swipeUp();
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
        minimiseMenu();
        closeSwordMasterMenu();

    }

    private boolean skipLevelUpSkills()
    {
        /*if (!hsUnlocked && !botSettings.useHS
                && dsUnlocked
                && homUnlocked
                && fsUnlocked
                && wcUnlocked
                && scUnlocked)
            return true;
        else if (hsUnlocked && botSettings.useHS && dsUnlocked && homUnlocked && fsUnlocked && wcUnlocked && scUnlocked)
            return true;
        else*/
            return false;
    }

    public void lvlSwordMaster(ExecuterTask task)
    {
        Log.d(TAG,"lvlSwordMaster");
        openSwordMasterMenu();
        try {
            int loopbreaker = 0;
            while (!isMenuTopReached() && loopbreaker < 25 && !task.cancelTask && Menu.MenuOpen.get()) {
                loopbreaker++;
                touchInput.swipeVertical(Heros.lvlFIrsHeroButton_click, Heros.lvlThirdHeroButton_click);
                Thread.sleep(200);
            }
            doSingelTap(Coordinates.Menu_Minimized_SwordMaster);
            Thread.sleep(1);
            doSingelTap(Coordinates.Menu_Minimized_SwordMaster);
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeSwordMasterMenu();
    }

    private void levelUpSkills()
    {
        try {
            WaitLock.checkForErrorAndWait();
            doSingelTap(Coordinates.Menu_SwordMasterPos);
            Thread.sleep(20);
            WaitLock.checkForErrorAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (botSettings.unlockHS)
            hs.levelSkill();
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockDS)
            ds.levelSkill();
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockHOM)
            hom.levelSkill();
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockFS)
            fs.levelSkill();
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockWC)
            wc.levelSkill();
        WaitLock.checkForErrorAndWait();
        if (botSettings.unlockSC)
            sc.levelSkill();
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
