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

    private boolean hsUnlocked = false;
    private boolean dsUnlocked = false;
    private boolean homUnlocked = false;
    private boolean fsUnlocked = false;
    private boolean wcUnlocked = false;
    private boolean scUnlocked = false;

    public Skills(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }


    public void activateAllSkills() throws IOException, InterruptedException {
        if (scUnlocked && !Menu.MenuOpen) {
            doSingelTap(Coordinates.SC_Pos);
            Thread.sleep(100);
        }
        if (wcUnlocked && !Menu.MenuOpen) {
            doSingelTap(Coordinates.WC_Pos);
            Thread.sleep(100);
        }
        if (fsUnlocked && !Menu.MenuOpen) {
            doSingelTap(Coordinates.FS_Pos);
            Thread.sleep(100);
        }
        if (homUnlocked && !Menu.MenuOpen) {
            doSingelTap(Coordinates.HOM_Pos);
            Thread.sleep(100);
        }
        if (dsUnlocked && !Menu.MenuOpen) {
            doSingelTap(Coordinates.DS_Pos);
            Thread.sleep(100);
        }
        if (hsUnlocked && !Menu.MenuOpen) {
            doSingelTap(Coordinates.Hs_Pos);
            Thread.sleep(100);
        }

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

        /*bot.dumpScreen();
        Log.d(TAG, (Coordinates.dsLvlArea.right - Coordinates.dsLvlArea.left) + (Coordinates.dsLvlArea.bottom -Coordinates.dsLvlArea.top) +"" );
        String dsLvl = bot.getOcr().getOCRResult(bot.getAreaFromScreen(Coordinates.dsLvlArea));*/

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
        if (System.currentTimeMillis() - lastSkillsActivated > runSkillsActivator) {

            try {
                Log.d(TAG, "activate Skills");
                activateAllSkills();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lastSkillsActivated = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    private boolean skipLevelUpSkills()
    {
        if (!hsUnlocked && !botSettings.useHS
                && dsUnlocked
                && homUnlocked
                && fsUnlocked
                && wcUnlocked
                && scUnlocked)
            return true;
        else if (hsUnlocked && botSettings.useHS && dsUnlocked && homUnlocked && fsUnlocked && wcUnlocked && scUnlocked)
            return true;
        else
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
                touchInput.swipeVertical(Coordinates.lvlFIrsHeroButton_click, Coordinates.lvlThirdHeroButton_click);
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

        if (botSettings.useHS && !hsUnlocked)
            levelSkill(25,Coordinates.Menu_HSPos);
        if (!dsUnlocked && botSettings.useDS)
            levelSkill(25,Coordinates.Menu_DSPos);
        if (!homUnlocked && botSettings.useHOM)
            levelSkill(25,Coordinates.Menu_HOMPos);
        if (!fsUnlocked && botSettings.useFS)
            levelSkill(25,Coordinates.Menu_FSMPos);
        if (!wcUnlocked && botSettings.useWC)
            levelSkill(25,Coordinates.Menu_WCMPos);
        if (!scUnlocked && botSettings.useSC)
            levelSkill(25,Coordinates.Menu_SCMPos);
    }

    private void levelSkill(int lvl, Point pos)
    {
        for (int i = 0; i< lvl; i++)
        {
            try {
                doSingelTap(pos);
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



    private void checkSkillsUnlocked()
    {
        bot.getScreeCapture().getColor(new Point(1,1));
        Log.d(TAG,"checkSkillsUnlocked");
        if (botSettings.useHS)
            hsUnlocked = isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.Hs_Pos));
        dsUnlocked = isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.DS_Pos));
        homUnlocked = isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.HOM_Pos));
        fsUnlocked = isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.FS_Pos));
        wcUnlocked = isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.WC_Pos));
        scUnlocked = isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.SC_Pos));
        Log.d(TAG, "Skills Unlocked HS:" + hsUnlocked + " DS:" + dsUnlocked+" Hom:"+homUnlocked+" FS:" + fsUnlocked + " WC:"+wcUnlocked +" SC: "+scUnlocked);

    }

    private boolean isSkillUnlocked(int color)
    {
        int r,g,b;
        r = Color.red(color);
        g = Color.green(color);
        b = Color.blue(color);
        Log.d(TAG, "Color:" +color + " rgb:" + r +" " +g +" " +b);
        if (r < 50 && g <50 && b < 50)
            return false;
        return true;
    }
}
