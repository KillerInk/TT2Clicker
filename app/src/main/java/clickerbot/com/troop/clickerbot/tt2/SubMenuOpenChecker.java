package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;

import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class SubMenuOpenChecker extends Menu {

    private final int hero_sub_menu_color = Color.argb(255,69,56,48);
    private final Point hero_sub_menu_point = new Point(428,34);

    public SubMenuOpenChecker(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init() {

    }

    @Override
    boolean checkIfRdyToExecute() {
        int color = bot.getScreeCapture().getColor(hero_sub_menu_point);
        if (color == hero_sub_menu_color) {
            WaitLock.lockError(true);
            doSingelTap(hero_sub_menu_point);
        }
        else
        {
            WaitLock.lockError(false);
        }
        return false;
    }
}
