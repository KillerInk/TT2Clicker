package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.tt2.Heros;

public class LevelAllHerosTask extends LevelTopHerosTask {

    public LevelAllHerosTask(Heros heros) {
        super(heros);
    }

    @Override
    public void doTask() {
        try {
            heros.levelAllHerosMaximised(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
