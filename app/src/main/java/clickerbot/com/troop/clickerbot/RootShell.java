package clickerbot.com.troop.clickerbot;

import android.graphics.Point;
import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by troop on 17.12.2016.
 */

public class RootShell
{
    private final String TAG = RootShell.class.getSimpleName();
    private Process process;
    private DataOutputStream os;
    private int id;
    private String cmdDown;
    private String cmdUp;
    private int cmdsleep;


    public RootShell(int id, int cmdsleep, int x, int y)
    {
        this.id =id;
        this.cmdsleep = cmdsleep;
        //if ((id % 2) == 0 )
            cmdDown = CmdBuilder.getTouchDown(x,y,id);
        //else
        //   cmdDown = CmdBuilder.getTouchDown(x,y-200,id);
        cmdUp = CmdBuilder.getTouchUp();
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"created ID:" + id);

    }

    public RootShell(int id, int cmdsleep)
    {
        this.id =id;
        this.cmdsleep = cmdsleep;

        cmdUp = CmdBuilder.getTouchUp();
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"created ID:" + id);

    }

    public void SendCMD()
    {
        try {

            sendTouchDownUp();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Log.d(TAG,"sendCMD ID:" + id);

    }

    public void sendCommand(String command) throws IOException {
        os.writeChars(command);
        os.flush();
    }

    public void doTap(Point p) throws IOException {
        sendCommand(CmdBuilder.gettap(p.x,p.y));
    }

    public void doSwipe(Point p, Point p2) throws IOException {
        sendCommand(CmdBuilder.getswipe(p.x,p.y, p2.x,p2.y, 200));
    }

    private void sendTouchDownUp() throws IOException, InterruptedException {
        os.writeChars(cmdDown);
        os.flush();
        Thread.sleep(cmdsleep);
        os.writeChars(cmdUp);
        os.flush();
    }

    public void sendTouchDownUp(int x, int y) throws IOException, InterruptedException {
        os.writeChars(CmdBuilder.getTouchDown(x,y,id));
        os.flush();
        Thread.sleep(cmdsleep);
        os.writeChars(cmdUp);
        os.flush();
    }

    public void captureScreen() throws IOException {
        os.writeChars("screencap -p >/sdcard/screen.png\n");
        os.flush();
    }

    public void Close()
    {
        try {
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"closed ID:" + id);
    }
}
