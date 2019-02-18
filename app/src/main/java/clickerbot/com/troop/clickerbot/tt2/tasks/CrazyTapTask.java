package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.tt2.RandomTaps;

public class CrazyTapTask extends RandomTapTask {

    public CrazyTapTask(RandomTaps bot) {
        super(bot);
    }

    @Override
    public void doTask() {
        bot.doCrazyTap(this);
    }
}
