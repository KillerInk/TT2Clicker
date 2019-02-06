package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;
import clickerbot.com.troop.clickerbot.tt2.tasks.ClanQuestTask;

public class ClanQuest extends Menu {

    private final String TAG = ClanQuest.class.getSimpleName();
    private final Point cq_button_color_pos = new Point(72,21);
    private final Point cq_button_click_pos = new Point(80,26);

    private final Point cq_clanchat_button_color_pos = new Point(80,736);
    private final Point cq_clanchat_button_click_pos = new Point(103,725);

    private final Point cq_fight_button_click_pos = new Point(319,735);
    private final Point cq_fight_timer_color_pos = new Point(83,50);
    private final Point cq_fight_timer_color_pos2 = new Point(84,50);
    private final int cq_fight_timer_color = Color.argb(255,164,164,164);

    private final Point cq_close_button_click_pos = new Point(415,47);
    private final int cq_close_button_click_color = Color.argb(255,69,56,48);
    private long lastPrestigeCheck;

    public ClanQuest(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init() {

    }

    @Override
    boolean checkIfRdyToExecute() {
        if(botSettings.autoClanQuest && System.currentTimeMillis() - lastPrestigeCheck > 1000 && !Menu.MenuOpen.get()) {
            lastPrestigeCheck = System.currentTimeMillis();
            WaitLock.checkForErrorAndWait();
            int color = bot.getScreeCapture().getColor(cq_button_color_pos);
            if (Color.red(color) > 120 && Color.red(color) < 140 && !WaitLock.sceneTransition.get()) {
                bot.executeTask(ClanQuestTask.class);
            }
        }
        return false;
    }

    public void doCQ(ExecuterTask executerTask) throws InterruptedException {
        //tap on cq and open clanchat
        Log.d(TAG,"Open ClanChat");
        while (!isClanChatOpen()) {
            WaitLock.checkForErrorAndWait();
            doLongerSingelTap(cq_button_click_pos);
            Thread.sleep(2000);
            WaitLock.checkForErrorAndWait();
        }
        if (isClanChatOpen())
        {
            Log.d(TAG,"ClanChat Open, open ClanQuest");
            //open cq window
            while (!isFightButton()) {
                WaitLock.checkForErrorAndWait();
                doLongerSingelTap(cq_clanchat_button_click_pos);
                Thread.sleep(2000);
                WaitLock.checkForErrorAndWait();
            }
            Log.d(TAG,"ClanQuest Open, click on Fight");
            //start finaly clanquest
            while (isFightButton())
            {
                WaitLock.checkForErrorAndWait();
                doLongerSingelTap(cq_fight_button_click_pos);
                Thread.sleep(1000);
                WaitLock.checkForErrorAndWait();
            }

            //wait for battle to start
            while (!isClanFightTimer() && !isClanFightTimerStart())
            {
                Thread.sleep(100);
            }
            Log.d(TAG,"Fight is starting");
            //fight boss
            int sleep = 1000/ (600 / 30);// = 20 = 50

            TapThread tap  =new TapThread();
            tap.start();
            while (isClanFightTimer() && !executerTask.cancelTask)
            {
                Thread.sleep(100);
            }
            tap.abort();
            Log.d(TAG,"Fight is over, wait for ClanQuest Close");
            //wait till we are back to cq window
            while (!isCloseWindow())
            {
                doLongerSingelTap(new Point(bot.getRandomX(),bot.getRandomY()));
                Thread.sleep(200);
            }
            //close cq and chat
            Log.d(TAG,"Close Both Windows");
            while (isCloseWindow())
            {
                Log.d(TAG,"Close Windows");
                WaitLock.checkForErrorAndWait();
                doLongerSingelTap(cq_close_button_click_pos);
                Thread.sleep(2000);
            }
            Log.d(TAG,"Back to normal state");
        }
    }

    private class TapThread extends Thread
    {
        private AtomicBoolean work = new AtomicBoolean();

        public void abort()
        {
            synchronized (TapThread.class) {
                work.set(false);
            }
        }
        @Override
        public void run() {
            work.set(true);
            Log.d(TAG,"CreateRandomTaps prepare for FIght");
            List<Point> randomTaps = new ArrayList();
            for (int i = 0; i< 40; i++)
            {
                randomTaps.add(new Point(bot.getRandomX(),bot.getRandomY()));
            }
            while (work.get())
            {
                synchronized (TapThread.class) {
                    try {
                        for (int i = 0; i < randomTaps.size(); i++) {
                            if (!TapThread.this.work.get())
                                return;
                            touchInput.tap(randomTaps.get(i), 30);
                            if (TapThread.this.work.get())
                                Thread.sleep(30);
                        }
                        if (TapThread.this.work.get())
                            Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean isClanChatOpen()
    {
        int color = bot.getScreeCapture().getColor(cq_clanchat_button_color_pos);
        return Color.red(color)> 230;
    }

    private boolean isClanFightTimer()
    {
        int color = bot.getScreeCapture().getColor(cq_fight_timer_color_pos);
        return color == cq_fight_timer_color;
    }

    private boolean isClanFightTimerStart()
    {
        int color = bot.getScreeCapture().getColor(cq_fight_timer_color_pos2);
        return color != cq_fight_timer_color;
    }

    private boolean isFightButton()
    {
        int color = bot.getScreeCapture().getColor(cq_fight_button_click_pos);
        return Color.red(color) == 255 && Color.blue(color) == 255 && Color.green(color) == 255;
    }

    private boolean isCloseWindow()
    {
        int color = bot.getScreeCapture().getColor(cq_close_button_click_pos);
        return color == cq_close_button_click_color;
    }
}