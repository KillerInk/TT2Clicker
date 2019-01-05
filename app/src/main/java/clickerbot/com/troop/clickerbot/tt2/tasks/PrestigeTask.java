package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Prestige;

public class PrestigeTask extends ExecuterTask {

    private final Prestige prestige;

    public PrestigeTask(Prestige prestige)
    {
        this.prestige = prestige;
    }

    @Override
    public void doTask() {
        try {
            prestige.doPrestige();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
