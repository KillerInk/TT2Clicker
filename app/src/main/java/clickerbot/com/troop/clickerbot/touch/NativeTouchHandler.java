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

    @Override
    public void swipeVertical(Point from, Point to) throws InterruptedException {
        touchDown(from);
        updatePosition(from);
        Thread.sleep(300);
        int y = from.y - (from.y - to.y)/2;
        updatePosition(new Point(from.x,y));
        Thread.sleep(300);
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
