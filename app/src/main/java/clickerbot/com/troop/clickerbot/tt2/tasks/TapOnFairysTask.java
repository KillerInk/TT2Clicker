package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.tt2.Fairy;

public class TapOnFairysTask extends TapOnFairyVipWindowTask {
    public TapOnFairysTask(Fairy fairy) {
        super(fairy);
    }

    @Override
    public void doTask() {
        fairy.tapFairys(this);
    }
}
