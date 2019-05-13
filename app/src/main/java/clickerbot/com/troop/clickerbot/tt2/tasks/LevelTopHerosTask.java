package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Heros;

public class LevelTopHerosTask extends ExecuterTask {

    protected final Heros heros;

    public LevelTopHerosTask(Heros heros)
    {
        this.heros = heros;
    }

    @Override
    public void doTask() {
        try {
            heros.levelTopHerosMaximised(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
