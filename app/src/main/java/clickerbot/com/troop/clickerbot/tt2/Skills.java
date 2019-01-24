package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class Skills extends Menu {
    private final String TAG = Skills.class.getSimpleName();

    private final int runSkillsActivator = 30000;//ms
    private long lastSkillsActivated = 0;

    private final Skill hs;
    private final Skill ds;
    private final Skill hom;
    private final Skill fs;
    private final Skill wc;
    private final Skill sc;
    private final SkillLevelParser skillLevelParser;


    public Skills(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        hs =new Skill(bot,rootShell,Skill.SkillType.HS,botSettings.unlockHS, botSettings.useHS);
        ds =new Skill(bot,rootShell,Skill.SkillType.DS,botSettings.unlockDS, botSettings.useDS);
        hom =new Skill(bot,rootShell,Skill.SkillType.HOM,botSettings.unlockHOM, botSettings.useHOM);
        fs =new Skill(bot,rootShell,Skill.SkillType.FS,botSettings.unlockFS, botSettings.useFS);
        wc =new Skill(bot,rootShell,Skill.SkillType.WC,botSettings.unlockWC, botSettings.useWC);
        sc =new Skill(bot,rootShell,Skill.SkillType.SC,botSettings.unlockSC, botSettings.useSC);
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
        closeMenu();
        checkSkillsUnlocked();
        Log.d(TAG,"init autolvl skills:" + botSettings.autoLvlSkills);
        if (botSettings.autoLvlSkills) {
            if (!skipLevelUpSkills()) {

                openSwordMasterMenu();

                maximiseMenu();

                swipeUp();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                levelUpSkills();
                try {
                    Thread.sleep(50);
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

        skillLevelParser.parseAllSkillLevels();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            while (!isMenuTopReached() && loopbreaker < 25 && !task.cancelTask && Menu.MenuOpen) {
                loopbreaker++;
                touchInput.swipeVertical(Heros.lvlFIrsHeroButton_click, Heros.lvlThirdHeroButton_click);
                Thread.sleep(300);
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
            doSingelTap(Coordinates.Menu_SwordMasterPos);
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (botSettings.unlockHS)
            hs.levelSkill();
        if (botSettings.unlockDS)
            ds.levelSkill();
        if (botSettings.unlockHOM)
            hom.levelSkill();
        if (botSettings.unlockFS)
            fs.levelSkill();
        if (botSettings.unlockWC)
            wc.levelSkill();
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
