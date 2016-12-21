package clickerbot.com.troop.clickerbot;

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


    public RootShell(int id)
    {
        this.id =id;
        //if ((id % 2) == 0 )
            cmdDown = CmdBuilder.getTouchDown(700,900,id);
        //else
        //    cmdDown = CmdBuilder.getTouchDown(600,800,id);
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
        Log.d(TAG,"sendCMD ID:" + id);

    }

    private void sendTouchDownUp() throws IOException, InterruptedException {
        os.writeChars(CmdBuilder.getTouchDown(700,900,id));
        os.flush();
        Thread.sleep(10);
        os.writeChars(CmdBuilder.getTouchUp());
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
