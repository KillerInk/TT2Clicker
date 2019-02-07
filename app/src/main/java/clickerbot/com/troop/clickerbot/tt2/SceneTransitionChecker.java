package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import clickerbot.com.troop.clickerbot.executer.Executer;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class SceneTransitionChecker extends Menu {

    private final String TAG  = SceneTransitionChecker.class.getSimpleName();
    private final Point shopbuttonColorPos = new Point(479,799);
    private final int shopButtonColor = Color.argb(255,60,131,134);
    private long lastTap =0;
    public SceneTransitionChecker(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init(ExecuterTask task) {

    }

    @Override
    boolean checkIfRdyToExecute() {
        int color =bot.getScreeCapture().getColor(shopbuttonColorPos);
        if (color != shopButtonColor && color != 0) {
            //Log.d(TAG, "scene Transition color:" + color + " expectedcolor:" + shopButtonColor);
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
