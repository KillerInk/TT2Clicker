package clickerbot.com.troop.clickerbot.touch;

import android.graphics.Point;
import android.util.Log;

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
import static clickerbot.com.troop.clickerbot.CmdBuilder.fourth;

public class NativeTouchHandler implements TouchInterface {
    private final  String TAG = NativeTouchHandler.class.getSimpleName();

    private NativeTouch nativeTouch;
    private final String inputDevice;
    private final boolean isMouseInput;
    private boolean log = false;

    private final int noInputID=0;
    private final int clickID = 1;
    private final int swipeID = 2;

    private Object touchLock = new Object();
    private  Point lastPoint;

    public void doLog(String msg)
    {
        if (log)
            Log.d(TAG,msg);
    }

    public NativeTouchHandler(String inputPath, boolean mouse)
    {
        this.isMouseInput = mouse;
        inputDevice = inputPath;
        lastPoint = new Point(240,400);
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

        synchronized (touchLock) {
            touchDown(pos, clickID);
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            touchUp(pos,clickID);
        }
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
        synchronized (touchLock) {
            Thread.sleep(2);
            updatePosition(from,swipeID);
            Thread.sleep(2);
            touchDown(from,swipeID);
            Thread.sleep(200);
            int distance = from.y - to.y;
            if (distance >= 0)
            {
                for(int i = 1 ; i < distance ; i++)
                {
                    updatePosition(new Point(from.x, from.y - i),swipeID);
                    Thread.sleep(2);
                }
            }
            else
            {
                for(int i = distance ; i < 0 ; i++)
                {
                    updatePosition(new Point(from.x, from.y + i),swipeID);
                    Thread.sleep(2);
                }
            }

            Thread.sleep(200);
            touchUp(to,swipeID);
            Thread.sleep(100);
            lastPoint = to;
        }

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
    private void touchDown(Point pos, int id) throws InterruptedException {
        doLog("touchDown");
        if (isMouseInput)
            touchDownMouse(pos, true, DOWN);
        else
            touchDownTouch(pos, id);

    }

    private void touchUp(Point pos, int id)
    {
        doLog("touchUp");
        if (isMouseInput)
            touchDownMouse(pos, false, UP);
        else
            touchUpTouch(pos);
        lastPoint = pos;
    }

    private void updatePosition(Point pos, int id)
    {
        doLog("updatePosition");
        if (isMouseInput)
            updatePositionMouse(pos);
        else
            updatePositionTouch(pos,id);
        lastPoint = pos;
    }

    private void touchDownMouse(Point pos, boolean b, int down) {
        sendMTpressure = b;
        nativeTouch.sendEvent(EV_KEY, BTN_TOUCH, down);
        updatePosition(pos,0);
    }


    private void sendXY(Point pos)
    {
        if (lastPoint.x != pos.x)
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_X,pos.x);
        if (lastPoint.y != pos.y)
            nativeTouch.sendEvent(EV_ABS, ABS_MT_POSITION_Y,pos.y);
    }

    private void updatePositionMouse(Point pos) {
        if (sendMTpressure)
            nativeTouch.sendEvent(EV_ABS, ABS_MT_PRESSURE,1);
        sendXY(pos);
        nativeTouch.sendEvent(EV_SYN, SYN_MT_REPORT,0);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
    }

    private void touchDownTouch(Point pos, int id)
    {
        sendMTpressure = true;
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, CmdBuilder.maxint);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_SLOT,id);
        sendXY(pos);
        nativeTouch.sendEvent(EV_ABS , ABS_MT_TOUCH_MAJOR  , 6);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, id);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
    }

    private void touchUpTouch(Point pos)
    {
        sendMTpressure = false;
        nativeTouch.sendEvent(EV_ABS , ABS_MT_TOUCH_MAJOR  , 0);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, CmdBuilder.maxint);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_SLOT,noInputID);
        sendXY(pos);
        nativeTouch.sendEvent(EV_ABS, ABS_MT_TRACKING_ID, noInputID);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
    }

    private void updatePositionTouch(Point pos, int id)
    {
        sendXY(pos);
        if (sendMTpressure)
            nativeTouch.sendEvent(EV_ABS , ABS_MT_TOUCH_MAJOR  , 6);
        nativeTouch.sendEvent(EV_SYN, SYN_REPORT,0);
    }
}
