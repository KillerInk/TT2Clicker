package clickerbot.com.troop.clickerbot.tt2.tasks.test;

import clickerbot.com.troop.clickerbot.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Menu;

public class MenuTest extends ExecuterTask {

    private Menu menu;

    public MenuTest(Menu menu)
    {
        this.menu = menu;
    }

    @Override
    public void doTask() {

        try {
            menu.menuTest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
