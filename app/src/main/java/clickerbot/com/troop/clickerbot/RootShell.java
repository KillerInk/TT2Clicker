package clickerbot.com.troop.clickerbot;

import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
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
    private HandlerThread handlerThread;
    private Handler handler;



    public RootShell(int id)
    {
        this.id =id;
        handlerThread = new HandlerThread("Rooshell" + id);
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"created ID:" + id);
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
        sendCommand(CmdBuilder.getswipe(p.x,p.y, p2.x,p2.y, 200));
    }




    public synchronized void captureScreen() throws IOException {
        if (os != null) {
            os.writeChars("screencap -p >/sdcard/screen.png\n");
            os.flush();
        }
        else
            Log.d(TAG, "captureScreen outputstream is null");
    }

    public void Close()
    {
        try {
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                handlerThread.quitSafely();
            }
            else
                handlerThread.quit();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"closed ID:" + id);
    }
}
