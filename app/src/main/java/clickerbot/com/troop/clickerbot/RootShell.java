package clickerbot.com.troop.clickerbot;

import android.graphics.Point;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by troop on 17.12.2016.
 */

public class RootShell
{
    private final String TAG = RootShell.class.getSimpleName();
    private Process process;
    private DataOutputStream os;
    private int id;



    public RootShell(int id)
    {
        this.id =id;
        Log.i(TAG,"created ID:" + id);
    }

    public InputStream getInputStream()
    {
        return process.getInputStream();
    }

    public void startProcess()
    {
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void sendCommand(String command) throws IOException {

            try {
                os.writeChars(command);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void chmodFile(String file) throws IOException {
        sendCommand("chmod 666 "+file+"\n");
    }
/*
    public void chmodFB0() throws IOException {
        sendCommand("chmod 666 /dev/fb0\n");
    }



    public void doTap(Point p) throws IOException {
        sendCommand(CmdBuilder.gettap(p.x,p.y));
    }

    public void doSwipe(Point p, Point p2) throws IOException {
        doSwipe(p,p2,200);
    }

    public void doSwipe(Point p, Point p2,int dur) throws IOException {
        sendCommand(CmdBuilder.getswipe(p.x,p.y, p2.x,p2.y, dur));
    }*/

    public void touchdownUp(int x, int y)
    {
        try {
            updatePosition(x,y);
            Thread.sleep(1);
            touchDown(x,y);
            Thread.sleep(1);
            touchUp(x,y);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void touchDown(int x, int y) throws IOException {
        sendCommand(CmdBuilder.getTouchDown(x,y,id));
    }

    private void touchUp(int x, int y) throws IOException {
        sendCommand(CmdBuilder.getTouchUP(x,y,id));
    }

    private void updatePosition(int x, int y) throws IOException {
        sendCommand(CmdBuilder.getUpdatePosition(x,y,id));
    }

    public void swipeFromTo(int x,int y, int x1, int y2) throws IOException, InterruptedException {
        touchDown(x,y);
        Thread.sleep(30);
        updatePosition(x,y);
        Thread.sleep(300);
        updatePosition(x,y-(y-y2)/3);
        Thread.sleep(300);
        updatePosition(x,y-(y-y2)/2);
        Thread.sleep(300);
        updatePosition(x,y2);
        touchUp(x,y2);
    }




   /* private synchronized void captureScreen() throws IOException {
        if (os != null) {
            //os.writeChars("screencap -p >/sdcard/screen.png\n");
            os.writeChars("screencap -p\n");
            os.flush();
        }
        else
            Log.d(TAG, "captureScreen outputstream is null");
    }*/

    public void stopProcess()
    {

        try {
            os.writeChars("exit\n");
            os.flush();
            Thread.sleep(100);
            os.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void Close()
    {
        stopProcess();
        Log.d(TAG,"closed ID:" + id);
    }

    public void touchdownUp(Point prestigeMenuButton) {
        touchdownUp(prestigeMenuButton.x,prestigeMenuButton.y);
    }
}
