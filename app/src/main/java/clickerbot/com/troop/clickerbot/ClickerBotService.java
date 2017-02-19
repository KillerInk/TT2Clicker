package clickerbot.com.troop.clickerbot;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by troop on 15.12.2016.
 */

public class ClickerBotService extends Service
{

    private final String TAG = ClickerBotService.class.getSimpleName();

    private WindowManager windowManager;
    private Button startStopButton;
    private Button closeButton;
    private int workerCount = 15;
    private int sleepTimeBetweenWorker = 20;
    private int sleepAfterOneRound = 0;
    private int cmdsleep;
    private ClickerThread clickerThread;

    private long lastClick;

    List<RootShell> rootShells;
    private TouchPoint touchePoints[];

    public class TouchPoint
    {

        public TouchPoint(String x, String y)
        {
            this.x = Integer.parseInt(x);
            this.y = Integer.parseInt(y);
        }
        int x = 0;
        int y = 0;
    }



    @Override public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        workerCount = preferences.getInt(ClickerBotActivity.PREFERENCES_WORKERCOUNT,15);
        sleepTimeBetweenWorker = preferences.getInt(ClickerBotActivity.PREFERENCES_SLEEPTIME_BETWEEN_WORKERS,20);
        sleepTimeBetweenWorker = preferences.getInt(ClickerBotActivity.PREFERENCES_CMDSLEEP,10);

        String tmp[] = preferences.getString(ClickerBotActivity.PREFERENCES_TAPX,"").split(";");
        touchePoints = new TouchPoint[tmp.length];
        int c = 0;
        for (String s:tmp)
        {
            String t[] = s.split(",");
            touchePoints[c++] = new TouchPoint(t[0],t[1]);
        }

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        startStopButton = new Button(this);
        startStopButton.setBackgroundResource(R.drawable.play);


        startStopButton.setText("");
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                long currentTime = new Date().getTime();
                if (currentTime - lastClick > 1000) {
                    lastClick = currentTime;
                    if (clickerThread == null) {
                        clickerThread = new ClickerThread();
                        clickerThread.Start();
                        startStopButton.setBackgroundResource(R.drawable.stop);
                    } else {
                        startStopButton.setBackgroundResource(R.drawable.play);
                        killThread();
                    }
                }
            }
        });

        int iconsize = convertDpiToPixel(45);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                iconsize,
                iconsize,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP|Gravity.LEFT;
        params.x = (metrics.widthPixels/2) - (iconsize + 5);
        params.y = 0;

        windowManager.addView(startStopButton, params);

        closeButton = new Button(this);
        closeButton.setText("");
        closeButton.setBackgroundResource(R.drawable.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ClickerBotService.this, ClickerBotActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                stopSelf();
            }
        });

        params = new WindowManager.LayoutParams(
                iconsize,
                iconsize,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP| Gravity.LEFT ;
        params.x = (metrics.widthPixels/2) +(iconsize+5);
        params.y = 10;

        windowManager.addView(closeButton, params);

        closeButton.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN )
                    killThread();
                return false;
            }
        });

        rootShells = new ArrayList<>();
        startStopButton.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int to =0;
                for (int i = 0; i< workerCount; i++){
                    rootShells.add(new RootShell(i,cmdsleep,touchePoints[to].x,touchePoints[to].y));
                    to++;
                    if (to == touchePoints.length)
                        to = 0;

                }
                startStopButton.post(new Runnable() {
                    @Override
                    public void run() {
                        startStopButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        killThread();
        for (RootShell rt : rootShells)
            rt.Close();
        rootShells.clear();
        rootShells = null;
        if (startStopButton != null) windowManager.removeView(startStopButton);
        if (closeButton != null) windowManager.removeView(closeButton);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int convertDpiToPixel(int dpi)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, getResources().getDisplayMetrics());
    }

    private int getRandomSleep()
    {
        return sleepTimeBetweenWorker;
        //return r.nextInt(sleepTimeBetweenWorker - sleepTimeBetweenWorker/4) + sleepTimeBetweenWorker/4;
    }


    private void killThread() {
        if (clickerThread != null) {
            clickerThread.Stop();
            while (clickerThread.isWorking) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            clickerThread = null;
        }
    }

    private class ClickerThread  extends Thread
    {
        private volatile boolean isWorking = false;
        private volatile boolean doWork = false;

        public void Start()
        {
            doWork = true;
            start();
        }

        public void Stop()
        {
            doWork = false;
        }

        @Override
        public void run() {
            isWorking = true;

            while (doWork)
            {
                for (RootShell rt : rootShells)
                {
                    if (doWork) {

                        rt.SendCMD();
                        try {
                            Thread.sleep(getRandomSleep());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Thread.sleep(sleepAfterOneRound);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isWorking = false;
        }
    }


}
