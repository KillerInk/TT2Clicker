package clickerbot.com.troop.clickerbot.touch;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.NativeTouch;
import clickerbot.com.troop.clickerbot.RootShell;

import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_POSITION_X;
import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_POSITION_Y;
import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_PRESSURE;
import static clickerbot.com.troop.clickerbot.CmdBuilder.BTN_TOUCH;
import static clickerbot.com.troop.clickerbot.CmdBuilder.DOWN;
import static clickerbot.com.troop.clickerbot.CmdBuilder.EV_ABS;
import static clickerbot.com.troop.clickerbot.CmdBuilder.EV_KEY;
import static clickerbot.com.troop.clickerbot.CmdBuilder.EV_SYN;
import static clickerbot.com.troop.clickerbot.CmdBuilder.SYN_MT_REPORT;
import static clickerbot.com.troop.clickerbot.CmdBuilder.SYN_REPORT;
import static clickerbot.com.troop.clickerbot.CmdBuilder.UP;

public class NativeTouchHandler implements TouchInterface {

    private NativeTouch nativeTouch;
    private String inputDevice = "/dev/input/event6";

    public NativeTouchHandler()
    {
        try {
            RootShell rootShell = new RootShell(0);
            rootShell.startProcess();
            rootShell.chmodFile(inputDevice);
            rootShell.Close();
            rootShell = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        nativeTouch = new NativeTouch();
        nativeTouch.open(inputDevice);
    }

    @Override
    public void tap(Point pos) throws InterruptedException {

        touchDown(pos);
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        touchUp(pos);
    }

    /**
     * touchDown 0 280 650
     * 		sleep 200
     * 		touchMove 0 280 600
     * 		sleep 50
     * 		touchMove 0 280 700
     * 		sleep 50
     * 		touchMove 0 280 750
     * 		sleep 50
     * 		touchMove 0 280 780
     * 		touchUp 0
     * @param from
     * @param to
     * @throws InterruptedException
     */
    @Override
    public void swipeVertical(Point from, Point to) throws InterruptedException {
        int dif = from.y - to.y;

        int sleep = dif / 4;
        if (sleep < 0 )
            sleep *= -1;
        touchDown(from);
        updatePosition(new Point(from.x,from.y));
        Thread.sleep(sleep);

        for (int i = 1; i< 5; i++)
        {
            if (to.y < from.y)
                updatePosition(new Point(from.x,from.y - (sleep*i)));
            else
                updatePosition(new Point(from.x,from.y + (sleep*i)));
            Thread.sleep(sleep);
        }
        updatePosition(to);
        touchUp(to);
    }

    @Override
    public void close() {
        nativeTouch = null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    private void touchDown(Point pos) throws InterruptedException {
        nativeTouch.sendEvent(EV_KEY, BTN_TOUCH,DOWN);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_PRESSURE,1);
        updatePosition(pos);

    }

    private void touchUp(Point pos)
    {
        nativeTouch.sendEvent(EV_KEY, BTN_TOUCH,UP);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_PRESSURE,1);
        updatePosition(pos);
    }

    private void updatePosition(Point pos)
    {
        nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_X,pos.x);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_Y,pos.y);
        nativeTouch.sendEvent(EV_SYN, SYN_MT_REPORT,0);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
    }
}
