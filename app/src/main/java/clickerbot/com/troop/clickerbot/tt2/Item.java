package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public abstract class Item {

    protected final TT2Bot bot;
    protected final BotSettings botSettings;
    protected TouchInterface touchInput;



    public Item(TT2Bot ibot, BotSettings botSettings, TouchInterface touchInput){
        this.bot = ibot;
        this.botSettings = botSettings;
        this.touchInput = touchInput;
    }

    abstract void init(ExecuterTask task);
    abstract boolean checkIfRdyToExecute();

    public void doSingelTap(Point point)
    {
        try {
            touchInput.tap(point,30);
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void doLongerSingelTap(Point point)
    {
        try {
            touchInput.tap(point,70);
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
