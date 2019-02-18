package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.TT2Bot;

public class InitTask extends ExecuterTask {

    private TT2Bot bot;

    public InitTask(TT2Bot bot) {
        this.bot = bot;
    }

    @Override
    public void doTask() {
        bot.init(this);
    }
}
