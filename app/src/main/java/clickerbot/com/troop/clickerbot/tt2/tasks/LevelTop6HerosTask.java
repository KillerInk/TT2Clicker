package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Heros;

public class LevelTop6HerosTask extends ExecuterTask {

    protected final Heros heros;

    public LevelTop6HerosTask(Heros heros)
    {
        this.heros = heros;
    }

    @Override
    public void doTask() {
        try {
            heros.levelTop9Heros(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
