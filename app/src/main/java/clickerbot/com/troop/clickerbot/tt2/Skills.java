package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;

public class Skills extends Menu {
    private final String TAG = Skills.class.getSimpleName();

    private boolean useHS = false;

    private boolean hsUnlocked = false;
    private boolean dsUnlocked = false;
    private boolean homUnlocked = false;
    private boolean fsUnlocked = false;
    private boolean wcUnlocked = false;
    private boolean scUnlocked = false;
    private IBot bot;

    public void setIBot(IBot iBot)
    {
        this.bot = iBot;
    }

    public void activateAllSkills() throws IOException, InterruptedException {
        if (scUnlocked) {
            rootShellClick[0].doTap(Coordinates.SC_Pos);
            Thread.sleep(20);
        }
        if (wcUnlocked) {
            rootShellClick[0].doTap(Coordinates.WC_Pos);
            Thread.sleep(20);
        }
        if (fsUnlocked) {
            rootShellClick[0].doTap(Coordinates.FS_Pos);
            Thread.sleep(20);
        }
        if (homUnlocked) {
            rootShellClick[0].doTap(Coordinates.HOM_Pos);
            Thread.sleep(20);
        }
        if (dsUnlocked) {
            rootShellClick[0].doTap(Coordinates.DS_Pos);
            Thread.sleep(20);
        }
        if (hsUnlocked) {
            rootShellClick[0].doTap(Coordinates.Hs_Pos);
            Thread.sleep(20);
        }

    }

    public void init() throws InterruptedException, IOException {
        closeMenu();
        checkSkillsUnlocked();

        openSwordMasterMenu();
        Thread.sleep(20);

        maximiseMenu();
        Thread.sleep(50);

        swipeUp();
        Thread.sleep(500);

        /*bot.dumpScreen();
        Log.d(TAG, (Coordinates.dsLvlArea.right - Coordinates.dsLvlArea.left) + (Coordinates.dsLvlArea.bottom -Coordinates.dsLvlArea.top) +"" );
        String dsLvl = bot.getOcr().getOCRResult(bot.getAreaFromScreen(Coordinates.dsLvlArea));*/

        levelSkills();
        Thread.sleep(50);

        minimiseMenu();
        Thread.sleep(50);

        closeMenu();
        Thread.sleep(50);
    }

    private void levelSkills()
    {
        try {
            rootShellClick[0].doTap(Coordinates.Menu_SwordMasterPos);
            Thread.sleep(20);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (useHS && !hsUnlocked)
            levelSkill(25,Coordinates.Menu_HSPos);
        if (!dsUnlocked)
            levelSkill(25,Coordinates.Menu_DSPos);
        if (!homUnlocked)
            levelSkill(25,Coordinates.Menu_HOMPos);
        if (!fsUnlocked)
            levelSkill(25,Coordinates.Menu_FSMPos);
        if (!wcUnlocked)
            levelSkill(25,Coordinates.Menu_WCMPos);
        if (!wcUnlocked)
            levelSkill(25,Coordinates.Menu_SCMPos);
    }

    private void levelSkill(int lvl, Point pos)
    {
        for (int i = 0; i< lvl; i++)
        {
            try {
                rootShellClick[0].doTap(pos);
                Thread.sleep(20);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



    private void checkSkillsUnlocked()
    {
        try {
            bot.dumpScreen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"checkSkillsUnlocked");
        if (useHS)
            hsUnlocked = isSkillUnlocked(bot.getColor(Coordinates.Hs_Pos));
        dsUnlocked = isSkillUnlocked(bot.getColor(Coordinates.DS_Pos));
        homUnlocked = isSkillUnlocked(bot.getColor(Coordinates.HOM_Pos));
        fsUnlocked = isSkillUnlocked(bot.getColor(Coordinates.FS_Pos));
        wcUnlocked = isSkillUnlocked(bot.getColor(Coordinates.WC_Pos));
        scUnlocked = isSkillUnlocked(bot.getColor(Coordinates.SC_Pos));
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
