package clickerbot.com.troop.clickerbot.touch;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.CmdBuilder;
import clickerbot.com.troop.clickerbot.NativeTouch;
import clickerbot.com.troop.clickerbot.RootShell;

import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_POSITION_X;
import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_POSITION_Y;
import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_PRESSURE;
import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_SLOT;
import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_TOUCH_MAJOR;
import static clickerbot.com.troop.clickerbot.CmdBuilder.ABS_MT_TRACKING_ID;
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
    private final String inputDevice;
    private final boolean isMouseInput;

    private Point lastPoint = new Point(0,0);

    public NativeTouchHandler(String inputPath, boolean mouse)
    {
        this.isMouseInput = mouse;
        inputDevice = inputPath;
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
    public void tap(Point pos,int duration) throws InterruptedException {

        touchDown(pos);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        touchUp(pos);
    }

    private final int movesleep = 50;
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

        int distance = from.y - to.y;
        boolean negativ = true;
        if (to.y > from.y) {
            negativ = false;
            distance = to.y - from.y;
        }
        distance -=1;
        touchDown(from);
        Thread.sleep(200);
        /*updatePosition(from);
        Thread.sleep(100);*/
        if (negativ)
            updatePosition(new Point(from.x, from.y - distance / 4));
        else
            updatePosition(new Point(from.x, from.y + distance / 4));
        Thread.sleep(movesleep);
        if (negativ)
            updatePosition(new Point(from.x, from.y - distance / 4 *2));
        else
            updatePosition(new Point(from.x, from.y + distance / 4 *2));
        Thread.sleep(movesleep);
        if (negativ)
            updatePosition(new Point(from.x, from.y - distance/4*3));
        else
            updatePosition(new Point(from.x, from.y + distance/4*3));
        Thread.sleep(movesleep);
        if (negativ)
            updatePosition(new Point(from.x, from.y - (distance -1)));
        else
            updatePosition(new Point(from.x, from.y + (distance -1)));
        Thread.sleep(movesleep);
        updatePosition(to);
       /* for (int i =0; i< distance; i++)
        {
            if (negativ)
                updatePosition(new Point(from.x, from.y- i));
            else
                updatePosition(new Point(from.x, from.y + i));
            Thread.sleep(1);
        }*/
        Thread.sleep(200);
        touchUp(to);
        Thread.sleep(100);

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

    private boolean sendMTpressure = false;
    private void touchDown(Point pos) throws InterruptedException {
        if (isMouseInput)
            touchDownMouse(pos, true, DOWN);
        else
            touchDownTouch(pos);
        lastPoint = pos;
    }

    private void touchUp(Point pos)
    {
        if (isMouseInput)
            touchDownMouse(pos, false, UP);
        else {
            try {
                touchUpTouch(pos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lastPoint = pos;
    }

    private void updatePosition(Point pos)
    {
        if (isMouseInput)
            updatePositionMouse(pos);
        else {
            try {
                updatePositionTouch(pos);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lastPoint = pos;
    }

    private void touchDownMouse(Point pos, boolean b, int down) {
        sendMTpressure = b;
        nativeTouch.sendEvent(EV_KEY, BTN_TOUCH, down);
        updatePosition(pos);
    }



    private void updatePositionMouse(Point pos) {
        if (sendMTpressure)
            nativeTouch.sendEvent(EV_ABS, ABS_MT_PRESSURE,1);
        if (lastPoint.x != pos.x)
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_X,pos.x);
        if (lastPoint.y != pos.y)
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_Y,pos.y);
        nativeTouch.sendEvent(EV_SYN, SYN_MT_REPORT,0);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
    }

    private void touchDownTouch(Point pos) throws InterruptedException {
        sendMTpressure = true;
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, CmdBuilder.maxint);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_SLOT,0);
        Thread.sleep(0,100);
        if (lastPoint.x != pos.x) {
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_X, pos.x);
            Thread.sleep(0,100);
        }
        if (lastPoint.y != pos.y) {
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_Y, pos.y);
            Thread.sleep(0,100);
        }
        nativeTouch.sendEvent(EV_ABS , ABS_MT_TOUCH_MAJOR  , 12);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, 0);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
        Thread.sleep(0,100);
    }

    private void touchUpTouch(Point pos) throws InterruptedException {
        sendMTpressure = false;
        nativeTouch.sendEvent(EV_ABS , ABS_MT_TOUCH_MAJOR  , 0);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, CmdBuilder.maxint);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_SLOT,12);
        Thread.sleep(0,100);
        if (lastPoint.x != pos.x) {
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_X, pos.x);
            Thread.sleep(0,100);
        }
        if (lastPoint.y != pos.y) {
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_Y, pos.y);
            Thread.sleep(0,100);
        }
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, 12);
        Thread.sleep(0,100);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
        Thread.sleep(0,100);
    }

    private void updatePositionTouch(Point pos) throws InterruptedException {
        if (lastPoint.x != pos.x) {
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_X, pos.x);
            Thread.sleep(0,100);
        }
        if (lastPoint.y != pos.y) {
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_Y, pos.y);
            Thread.sleep(0,100);
        }
        if (sendMTpressure) {
            nativeTouch.sendEvent(EV_ABS, ABS_MT_TOUCH_MAJOR, 12);
            Thread.sleep(0,100);
        }
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
        Thread.sleep(0,100);
    }
}
