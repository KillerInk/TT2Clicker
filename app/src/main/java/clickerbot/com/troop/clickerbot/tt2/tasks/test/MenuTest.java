package clickerbot.com.troop.clickerbot.tt2.tasks.test;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
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
            menu.menuTest(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
