package clickerbot.com.troop.clickerbot;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;

public interface IBot {
    boolean getIsRunning();
    void start();
    void stop();

    MediaProjectionScreenCapture getScreeCapture();
    public void resetTickCounter();

    void execute(ExecuterTask runnable);
    void putFirstAndExecute(ExecuterTask runnable);
    void clearExecuterQueue();
}
