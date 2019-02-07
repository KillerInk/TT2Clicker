package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.tt2.TT2Bot;

public class InitTask extends RandomTapTask {
    public InitTask(TT2Bot bot) {
        super(bot);
    }

    @Override
    public void doTask() {
        bot.init(this);
    }
}
