package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class SubMenuOpenChecker extends Menu {

    private final String TAG = SubMenuOpenChecker.class.getSimpleName();
    private final int hero_sub_menu_color = Color.argb(255,69,56,48);
    private final Point hero_sub_menu_point = new Point(428,34);
    private final Point skill_sub_menu_point = new Point(430,204);
    private final Point cq_close_button_click_pos = new Point(415,47);
    private final Point profile_close_button_click_pos = new Point(422,166);

    public SubMenuOpenChecker(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init(ExecuterTask task) {

    }

    @Override
    boolean checkIfRdyToExecute() {
        int color = bot.getScreeCapture().getColor(hero_sub_menu_point);
        int color2 = bot.getScreeCapture().getColor(skill_sub_menu_point);
        int color3 = bot.getScreeCapture().getColor(cq_close_button_click_pos);
        int color4 = bot.getScreeCapture().getColor(profile_close_button_click_pos);
        if (!WaitLock.prestige.get()){
        //window from hero menu and clicked on a hero
        if (color == hero_sub_menu_color) {
            WaitLock.lockError(true);
            doSingelTap(hero_sub_menu_point,"Random Touch opened Hero Submenu");
        }
        //window from swordmaster menu and clicked on a skill
        else if (color2 == hero_sub_menu_color) {
            WaitLock.lockError(true);
            doSingelTap(skill_sub_menu_point,"Random Touch opened Skill Submenu");
        }
        else if (!WaitLock.clanquest.get() && color3 == hero_sub_menu_color)
        {
            doSingelTap(cq_close_button_click_pos,"Close cq window");
        }
        //window from swordmaster menu and clicked on swordmaster
        else if (color4 == hero_sub_menu_color)
        {
            doSingelTap(profile_close_button_click_pos,"Random Touch opened profile Submenu");
        }
        else
        {
            WaitLock.lockError(false);
        }
        }
        return false;
    }
}
