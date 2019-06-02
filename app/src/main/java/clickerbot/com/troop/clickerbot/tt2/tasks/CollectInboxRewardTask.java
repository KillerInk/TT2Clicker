package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.CollectInBoxRewward;

public class CollectInboxRewardTask extends ExecuterTask {

    private CollectInBoxRewward collectInBoxRewward;

    public CollectInboxRewardTask(CollectInBoxRewward inBoxRewward)
    {
        this.collectInBoxRewward = inBoxRewward;
    }

    @Override
    public void doTask() {
        try {
            collectInBoxRewward.collectInboxReward(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
