package clickerbot.com.troop.clickerbot;

public abstract class ExecuterTask implements Runnable, ITask {

    public volatile boolean cancelTask = false;

    private Thread thread;
    @Override
    public void run() {
        thread = Thread.currentThread();
        doTask();
        thread = null;

    }

    public void cancel()
    {
        if (thread != null && thread.isAlive())
        {
            thread.interrupt();
        }
        thread = null;
    }

}
