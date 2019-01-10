package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.tt2.Heros;

public class MenuCloseTask extends LevelAllHerosTask {
    public MenuCloseTask(Heros heros) {
        super(heros);
    }

    @Override
    public void doTask() {
        heros.closeMenu();
    }
}
