package clickerbot.com.troop.clickerbot;

public interface IBot {
    boolean getIsRunning();
    void start();
    void stop();

    OCR getOcr();

    ScreenCapture getScreeCapture();
    public void resetTickCounter();

    void execute(ExecuterTask runnable);
    void putFirstAndExecute(ExecuterTask runnable);
    void clearExecuterQueue();
}
