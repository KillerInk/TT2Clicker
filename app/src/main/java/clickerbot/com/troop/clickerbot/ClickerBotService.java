package clickerbot.com.troop.clickerbot;

import android.app.Instrumentation;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by troop on 15.12.2016.
 */

public class ClickerBotService extends Service
{

    private WindowManager windowManager;
    private Button startStopButton;
    private boolean working = false;
    private int pozx = 700, pozy = 700;
    IBinder wmbinder;
    IWindowManager m_WndManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        /*wmbinder = ServiceManager.getService( "window" );
        m_WndManager = IWindowManager.Stub.asInterface( wmbinder );*/

        startStopButton = new Button(this);
        startStopButton.setText("Start");
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!working)
                {
                    working = true;
                    startStopButton.setText("Stop");
                    doWork();
                }
                else {
                    working = false;
                    startStopButton.setText("Start");
                }
            }
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 200;

        windowManager.addView(startStopButton, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (startStopButton != null) windowManager.removeView(startStopButton);
    }


    private void doWork()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                fakeTouch();
            }
        }).start();
    }

    private void fakeTouch()
    {
        Log.d(ClickerBotService.class.getSimpleName(), "fakeTouch");

        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            String cmd = "/system/bin/input tap 500 500\n";
            boolean first =true;
            while (working) {
                os.write(cmd.getBytes());
                os.flush();
                Log.d(ClickerBotService.class.getSimpleName(), "sendfakeTouch");
                Thread.sleep(200);
            }
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /*Instrumentation m_Instrumentation = new Instrumentation();
        m_Instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),MotionEvent.ACTION_DOWN,pozx, pozy, 0));
        m_Instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),MotionEvent.ACTION_UP,pozx, pozy, 0));*/

        /*m_WndManager.injectPointerEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),MotionEvent.ACTION_DOWN,pozx, pozy, 0),false);
        m_WndManager.injectPointerEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),MotionEvent.ACTION_UP,pozx, pozy, 0),false);*/
        /*// key down
        m_WndManager.injectKeyEvent( new KeyEvent( KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_A ),false );
        // key up
        m_WndManager.injectKeyEvent( new KeyEvent( KeyEvent.ACTION_UP, KeyEvent.KEYCODE_A ),false );*/
    }
}
