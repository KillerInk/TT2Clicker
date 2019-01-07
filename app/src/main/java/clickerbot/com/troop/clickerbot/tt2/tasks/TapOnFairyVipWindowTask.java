package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Fairy;

public class TapOnFairyVipWindowTask extends ExecuterTask {

    protected Fairy fairy;
    public TapOnFairyVipWindowTask(Fairy fairy)
    {
        this.fairy = fairy;
    }

    @Override
    public void doTask() {
        fairy.tapOnFairyVipWindow();
    }
}
