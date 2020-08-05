package clickerbot.com.troop.clickerbot;

import android.content.Context;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.screencapture.ScreenCapture;

public interface IBot {
    boolean getIsRunning();
    void start();
    void stop();

    ScreenCapture getScreeCapture();
    public void resetTickCounter();

    void execute(ExecuterTask runnable);
    void putFirstAndExecute(ExecuterTask runnable);
    void clearExecuterQueue();
    Context getContext();
}
