package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.TT2Bot;

public class RandomTapTask extends ExecuterTask {
    protected final TT2Bot bot;

    public RandomTapTask(TT2Bot bot)
    {
        this.bot = bot;
    }

    @Override
    public void doTask() {
        bot.doTap(this);
    }
}
