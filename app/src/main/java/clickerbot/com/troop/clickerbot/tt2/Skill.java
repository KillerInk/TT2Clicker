package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class Skill {
    private final String TAG = Skill.class.getSimpleName();

    public enum SkillType
    {
        HS,
        DS,
        HOM,
        FS,
        WC,
        SC
    }

    public enum SkillState
    {
        locked,
        unlocked,
        active,
        deactive,
        outOfMana
    }

    private final int Y_Pos_Skills = 683;
    private final Point HS_COLOR_POS = new Point(119,Y_Pos_Skills);
    private final Point DS_COLOR_POS = new Point(119,Y_Pos_Skills);
    private final Point HOM_COLOR_POS = new Point(199,Y_Pos_Skills);
    private final Point FS_COLOR_POS = new Point(279,Y_Pos_Skills);
    private final Point WC_COLOR_POS = new Point(359,Y_Pos_Skills);
    private final Point SC_COLOR_POS = new Point(439,Y_Pos_Skills);

    private final  int COLOR_ACTIVE_SKILL = Color.argb(255,188,134,18);
    private final  int COLOR_RDY_SKILL = Color.argb(255,255,255,255);
    private final  int COLOR_OUT_OF_MANA = Color.argb(255,127,127,127);

    private TT2Bot bot;
    private SkillType skillType;
    private SkillState skillState;
    private TouchInterface touchInput;
    private boolean unlockSkilll = false;
    private boolean activateSkill = false;
    private int skillLvl;
    private int maxLVL;

    private final Point Menu_HSPos = new Point(408,232);
    private final Point Menu_HSPos_color = new Point(470,232);
    private final Point Menu_DSPos = new Point(408,310);
    private final Point Menu_DSPos_color = new Point(470,310);
    private final Point Menu_HOMPos = new Point(408,385);
    private final Point Menu_HOMPos_color = new Point(470,385);
    private final Point Menu_FSMPos = new Point(408,459);
    private final Point Menu_FSMPos_color = new Point(470,459);
    private final Point Menu_WCMPos = new Point(408,537);
    private final Point Menu_WCMPos_color = new Point(470,537);
    private final Point Menu_SCMPos = new Point(408,614);
    private final Point Menu_SCMPos_color = new Point(470,614);


    public Skill(TT2Bot bot,TouchInterface touchInput,SkillType type, boolean unlockSkill, boolean activateSkill, int maxLVL)
    {
        this.bot = bot;
        this.touchInput =touchInput;
        this.skillType = type;
        this.activateSkill = activateSkill;
        this.unlockSkilll = unlockSkill;
        this.skillState = SkillState.locked;
        this.maxLVL = maxLVL;
    }

    public void setSkillLvl(int skillLvl)
    {
        this.skillLvl = skillLvl;
    }

    public void checkIfSkillUnlocked()
    {
        switch (skillType)
        {
            case HS:
                if (isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.Hs_Pos)))
                    skillState = SkillState.unlocked;
                break;
            case DS:
                if (isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.DS_Pos)))
                    skillState = SkillState.unlocked;
                break;
            case HOM:
                if (isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.HOM_Pos)))
                    skillState = SkillState.unlocked;
                break;
            case FS:
                if (isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.FS_Pos)))
                    skillState = SkillState.unlocked;
                break;
            case WC:
                if (isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.WC_Pos)))
                    skillState = SkillState.unlocked;
                break;
            case SC:
                if (isSkillUnlocked(bot.getScreeCapture().getColor(Coordinates.SC_Pos)))
                    skillState = SkillState.unlocked;
                break;
        }
    }

    public void detectSkillState()
    {
        if (!Menu.MenuOpen.get()) {
            int color = bot.getScreeCapture().getColor(getSkillColorPoint());
            SkillState state;
            if (color == COLOR_RDY_SKILL)
                state = SkillState.deactive;
            else if (color == COLOR_ACTIVE_SKILL)
                state = SkillState.active;
            else if(color == COLOR_OUT_OF_MANA)
                state = SkillState.outOfMana;
            else
                state = SkillState.locked;
            if (true/*skillState != state*/)
            {
                skillState = state;
                if (skillState == SkillState.deactive && activateSkill)
                    activateSkill();
            }
        }
    }

    private Point getSkillColorPoint()
    {
        switch (skillType) {
            case HS:
                return HS_COLOR_POS;
            case DS:
                return DS_COLOR_POS;
            case HOM:
                return HOM_COLOR_POS;
            case FS:
                return FS_COLOR_POS;
            case WC:
                return WC_COLOR_POS;
            case SC:
                return SC_COLOR_POS;
        }
        return null;
    }

    private boolean isSkillUnlocked(int color)
    {
        return color == COLOR_ACTIVE_SKILL || color == COLOR_RDY_SKILL || color == COLOR_OUT_OF_MANA;
    }

    public void activateSkill()
    {
        if (skillState != SkillState.locked && skillState != SkillState.active && skillState != SkillState.outOfMana && !Menu.MenuOpen.get())
        {
            WaitLock.checkForErrorAndWait();
            if (WaitLock.clanquest.get())
                return;
            try {
                switch (skillType) {
                    case HS:
                        touchInput.tap(Coordinates.Hs_Pos,30);
                        Thread.sleep(100);
                        break;
                    case DS:
                        touchInput.tap(Coordinates.DS_Pos,30);
                        Thread.sleep(100);
                        break;
                    case HOM:
                        touchInput.tap(Coordinates.HOM_Pos,30);
                        Thread.sleep(100);
                        break;
                    case FS:
                        touchInput.tap(Coordinates.FS_Pos,30);
                        Thread.sleep(100);
                        break;
                    case WC:
                        touchInput.tap(Coordinates.WC_Pos,30);
                        Thread.sleep(100);
                        break;
                    case SC:
                        touchInput.tap(Coordinates.SC_Pos,30);
                        Thread.sleep(100);
                        break;
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            WaitLock.checkForErrorAndWait();
        }
    }

    public void levelSkill(ExecuterTask task)
    {
        int dif = maxLVL - skillLvl;
        if (dif < 0)
            dif = 0;
        switch (skillType) {
            case HS:
                levelSkill(dif,Menu_HSPos, Menu_HSPos_color,task);
                break;
            case DS:
                levelSkill(dif,Menu_DSPos,Menu_DSPos_color,task);
                break;
            case HOM:
                levelSkill(dif,Menu_HOMPos,Menu_HOMPos_color,task);
                break;
            case FS:
                levelSkill(dif,Menu_FSMPos,Menu_FSMPos_color,task);
                break;
            case WC:
                levelSkill(dif,Menu_WCMPos,Menu_WCMPos_color,task);
                break;
            case SC:
                levelSkill(dif,Menu_SCMPos,Menu_SCMPos_color,task);
                break;
        }
    }

    private void levelSkill(int lvl, Point pos, Point color_pos,ExecuterTask task)
    {
        for (int i = 0; i< lvl; i++)
        {
            if (task.cancelTask)
                return;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int color = bot.getScreeCapture().getColor(color_pos);
            if (ColorUtils.isGray(color))
                return;
            try {
                touchInput.tap(pos,30);
                WaitLock.checkForErrorAndWait();
                Thread.sleep(100);
                WaitLock.checkForErrorAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
