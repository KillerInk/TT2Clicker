package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Heros;

public class LevelHerosTask extends ExecuterTask {

    private final Heros heros;

    public LevelHerosTask(Heros heros)
    {
        this.heros = heros;
    }

    @Override
    public void doTask() {
        try {
            heros.levelHeros();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
