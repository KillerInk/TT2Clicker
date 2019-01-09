package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.tt2.TT2Bot;

public class CrazyTapTask extends RandomTapTask {
    public CrazyTapTask(TT2Bot bot) {
        super(bot);
    }

    @Override
    public void doTask() {
        bot.doCrazyTap(this);
    }
}
