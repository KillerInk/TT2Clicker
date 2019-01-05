package clickerbot.com.troop.clickerbot.tt2;

import clickerbot.com.troop.clickerbot.IBot;
import clickerbot.com.troop.clickerbot.RootShell;

public abstract class Item {

    protected final IBot bot;
    protected final BotSettings botSettings;
    protected RootShell rootShells[];



    public Item(IBot ibot, BotSettings botSettings, RootShell rootShell[]){
        this.bot = ibot;
        this.botSettings = botSettings;
        this.rootShells = rootShell;
    }

    abstract void init();
    abstract boolean rdToExecute();
}
