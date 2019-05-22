package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.CollectDailyReward;

public class CollectDailyTask extends ExecuterTask {

    private CollectDailyReward reward;
    public CollectDailyTask(CollectDailyReward reward)
    {
        this.reward = reward;
    }

    @Override
    public void doTask() {

        try {
            reward.collectDailyReward(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
