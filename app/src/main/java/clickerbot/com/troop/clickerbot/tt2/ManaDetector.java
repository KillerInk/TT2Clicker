package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class ManaDetector extends Menu
{
    private final String TAG = ManaDetector.class.getSimpleName();
    private final int manaBarPos_Y = 640;
    private final int manaBarPosMAX_X = 208;
    private int manaPercentage = 0;
    private long lastTick;

    public ManaDetector(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init(ExecuterTask task) {

    }

    @Override
    boolean checkIfRdyToExecute() {
        if (System.currentTimeMillis() - lastTick > 1000)
        {
            lastTick = System.currentTimeMillis();
            detectManaPercentage();
        }
        return false;
    }

    public synchronized int getManaPercentage()
    {
        return manaPercentage;
    }

    public void detectManaPercentage()
    {
        int i = manaBarPosMAX_X;
        while (i > 0)
        {
            if (!Menu.MenuOpen.get() && Menu.getMenuState() != Menu.MenuState.maximise) {
                int color = bot.getScreeCapture().getColor(new Point(i, manaBarPos_Y));
                if (color == Color.WHITE) {
                    manaPercentage = (int)((double)i / (double)manaBarPosMAX_X * 100);
                    i = 0;
                    Log.d(TAG, "Mana: " + manaPercentage + "%");
                } else
                    i--;
            }
            else
                i = 0;
        }
    }
}
