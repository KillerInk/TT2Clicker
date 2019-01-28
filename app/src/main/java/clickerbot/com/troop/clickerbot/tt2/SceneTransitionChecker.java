package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class SceneTranstitionChecker extends Menu {

    private final String TAG  = SceneTranstitionChecker.class.getSimpleName();
    private final Point shopbuttonColorPos = new Point(479,799);
    private final int shopButtonColor = Color.argb(255,60,131,134);
    private long lastTap =0;
    public SceneTranstitionChecker(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init() {

    }

    @Override
    boolean checkIfRdyToExecute() {
        int color =bot.getScreeCapture().getColor(shopbuttonColorPos);
        if (color != shopButtonColor) {
            Log.d(TAG, "scene Transition color:" + color + " expectedcolor:" + shopButtonColor);
            WaitLock.lockSceneTransition(true);
            if (System.currentTimeMillis() - lastTap > 200)
            {
                lastTap = System.currentTimeMillis();
                doSingelTap(new Point(bot.getRandomX(),bot.getRandomY()));
            }
        }
        else {
            WaitLock.lockSceneTransition(false);
        }
        return false;
    }
}
