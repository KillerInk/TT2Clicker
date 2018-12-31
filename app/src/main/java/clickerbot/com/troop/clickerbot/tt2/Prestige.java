package clickerbot.com.troop.clickerbot.tt2;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.IBot;

public class Prestige extends Menu {


    public Prestige(IBot bot, BotSettings botSettings) {
        super(bot, botSettings);
    }

    public void doPrestige() throws InterruptedException, IOException {
        if (botSettings.autoPrestige) {
            openSwordMasterMenu();
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(200);
            swipeUp(-200);
            Thread.sleep(1000);
            rootShellClick[0].doTap(Coordinates.prestigeMenuButton);
            Thread.sleep(1000);
            rootShellClick[0].doTap(Coordinates.prestigeButton);
            Thread.sleep(3000);
            rootShellClick[0].doTap(Coordinates.prestigeAcceptButton);
            Thread.sleep(300);
            rootShellClick[0].doTap(Coordinates.prestigeAcceptButton);
            Thread.sleep(5000);
        }
    }
}
