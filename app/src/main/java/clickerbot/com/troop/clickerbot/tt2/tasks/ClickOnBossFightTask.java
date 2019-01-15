package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Boss;

public class ClickOnBossFightTask extends ExecuterTask {

    private Boss boss;

    public ClickOnBossFightTask(Boss boss)
    {
        this.boss = boss;
    }
    @Override
    public void doTask() {
        try {
            boss.clickOnBossFight();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
