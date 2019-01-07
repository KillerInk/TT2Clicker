package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.RootShell;

public abstract class Item {

    protected final TT2Bot bot;
    protected final BotSettings botSettings;
    protected RootShell rootShells[];



    public Item(TT2Bot ibot, BotSettings botSettings, RootShell[] rootShell){
        this.bot = ibot;
        this.botSettings = botSettings;
        this.rootShells = rootShell;
    }

    abstract void init();
    abstract boolean rdToExecute();

    public void doSingelTap(Point point)
    {
        try {
            rootShells[0].doTap(point);
            Thread.sleep(50);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
