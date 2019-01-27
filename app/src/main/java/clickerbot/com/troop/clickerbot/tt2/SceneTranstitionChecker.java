package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;

import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class SceneTranstitionChecker extends Menu {

    private final Point settingsButtonPos = new Point(29,16);
    private final int settingsButtonColor = Color.argb(255,89,104,109);
    private final int settingsButtonColorMenuMax = Color.argb(255,44,52,54);
    public SceneTranstitionChecker(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init() {

    }

    @Override
    boolean checkIfRdyToExecute() {
        int color =bot.getScreeCapture().getColor(settingsButtonPos);
        if (color != settingsButtonColor && color != settingsButtonColorMenuMax)
            WaitLock.lockSceneTransition(true);
        else {
            WaitLock.lockSceneTransition(false);
        }
        return false;
    }
}
