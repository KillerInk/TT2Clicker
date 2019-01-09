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

    private void sendCommand(String command) throws IOException {

            try {
                os.writeChars(command);
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void doTap(Point p) throws IOException {
        sendCommand(CmdBuilder.gettap(p.x,p.y));
    }

    public void doSwipe(Point p, Point p2) throws IOException {
        doSwipe(p,p2,200);
    }

    public void doSwipe(Point p, Point p2,int dur) throws IOException {
        sendCommand(CmdBuilder.getswipe(p.x,p.y, p2.x,p2.y, dur));
    }




    public synchronized void captureScreen() throws IOException {
        if (os != null) {
            //os.writeChars("screencap -p >/sdcard/screen.png\n");
            os.writeChars("screencap -p\n");
            os.flush();
        }
        else
            Log.d(TAG, "captureScreen outputstream is null");
    }

    public void stopProcess()
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

    }

    public void Close()
    {
        stopProcess();
        Log.d(TAG,"closed ID:" + id);
    }
}
