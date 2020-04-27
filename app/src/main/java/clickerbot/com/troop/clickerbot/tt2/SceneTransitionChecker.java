package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class SceneTransitionChecker extends Menu {

    private final String TAG  = SceneTransitionChecker.class.getSimpleName();
    private final Point shopbuttonColorPos = new Point(479,799);
    private final int shopButtonColor = -12680574;//Color.argb(255,61,130,132);
    private long lastTap =0;
    private int howOftenDetected;
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
            howOftenDetected++;
            WaitLock.lockSceneTransition(true);
            if(howOftenDetected > 3) {
                //Log.d(TAG, "scene Transition color:" + color + " expectedcolor:" + shopButtonColor);
                howOftenDetected++;
                if (System.currentTimeMillis() - lastTap > 200 && Menu.getMenuState() == MenuState.closed) {
                    lastTap = System.currentTimeMillis();
                    doSingelTap(new Point(bot.getRandomX(), bot.getRandomY()), "sceneTransition");
                }
            }
        }
        else {
            howOftenDetected--;
            WaitLock.lockSceneTransition(false);
        }
        return false;
    }
}
