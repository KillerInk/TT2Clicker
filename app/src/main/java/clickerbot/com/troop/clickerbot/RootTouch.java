package clickerbot.com.troop.clickerbot;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by troop on 17.12.2016.
 */

public class RootTouch
{
    private final String TAG = RootTouch.class.getSimpleName();
    private Process process;
    private DataOutputStream os;
    private int id;
    private Handler handler;
    private String cmd;
    boolean started = false;
    public RootTouch(int id)
    {
        this.id =id;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler = new Handler(Looper.getMainLooper());
        Log.d(TAG,"created ID:" + id);

    }

    public void SendCMDContinouse(String cmd)
    {
        this.cmd = cmd;
        started = true;
        handler.post(sendCmdRunner);
    }

    public void Stop()
    {
        started = false;
        handler.post(null);
        Close();
    }

    private Runnable sendCmdRunner = new Runnable() {
        @Override
        public void run() {
            SendCMD(cmd);
            if (started)
                handler.postDelayed(sendCmdRunner,500);
        }
    };

    public void SendCMD(String cmd)
    {
        try {
            os.writeChars(cmd);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"sendCMD ID:" + id);

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
