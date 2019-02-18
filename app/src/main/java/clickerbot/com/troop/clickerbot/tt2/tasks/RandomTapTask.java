package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.RandomTaps;

public class RandomTapTask extends ExecuterTask {
    protected final RandomTaps bot;

    public RandomTapTask(RandomTaps bot)
    {
        this.bot = bot;
    }

    @Override
    public void doTask() {
        bot.doTap(this);
    }
}
