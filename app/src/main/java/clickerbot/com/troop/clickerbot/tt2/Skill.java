package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

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
        if (!Menu.MenuOpen) {
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
        if (skillState != SkillState.locked && skillState != SkillState.active && skillState != SkillState.outOfMana && !Menu.MenuOpen)
        {
            try {
                switch (skillType) {
                    case HS:
                        touchInput.tap(Coordinates.Hs_Pos);
                        Thread.sleep(100);
                        break;
                    case DS:
                        touchInput.tap(Coordinates.DS_Pos);
                        Thread.sleep(100);
                        break;
                    case HOM:
                        touchInput.tap(Coordinates.HOM_Pos);
                        Thread.sleep(100);
                        break;
                    case FS:
                        touchInput.tap(Coordinates.FS_Pos);
                        Thread.sleep(100);
                        break;
                    case WC:
                        touchInput.tap(Coordinates.WC_Pos);
                        Thread.sleep(100);
                        break;
                    case SC:
                        touchInput.tap(Coordinates.SC_Pos);
                        Thread.sleep(100);
                        break;
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void levelSkill()
    {
        int dif = maxLVL - skillLvl;
        if (dif < 0)
            dif = 0;
        switch (skillType) {
            case HS:
                levelSkill(dif,Coordinates.Menu_HSPos);
                break;
            case DS:
                levelSkill(dif,Coordinates.Menu_DSPos);
                break;
            case HOM:
                levelSkill(dif,Coordinates.Menu_HOMPos);
                break;
            case FS:
                levelSkill(dif,Coordinates.Menu_FSMPos);
                break;
            case WC:
                levelSkill(dif,Coordinates.Menu_WCMPos);
                break;
            case SC:
                levelSkill(dif,Coordinates.Menu_SCMPos);
                break;
        }
    }

    private void levelSkill(int lvl, Point pos)
    {
        for (int i = 0; i< lvl; i++)
        {
            try {
                touchInput.tap(pos);
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
