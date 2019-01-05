package clickerbot.com.troop.clickerbot;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.TextView;

import java.util.Date;

import clickerbot.com.troop.clickerbot.tt2.AbstractBot;
import clickerbot.com.troop.clickerbot.tt2.BotSettings;
import clickerbot.com.troop.clickerbot.tt2.TT2Bot;

/**
 * Created by troop on 15.12.2016.
 */

public class ClickerBotService extends Service
{

    private final String TAG = ClickerBotService.class.getSimpleName();

    private WindowManager windowManager;
    private Button startStopButton;
    private Button closeButton;
    private TT2Bot tt2Bot;
    private TextView prestigeCounterView;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        private int callCount = 0;
        @Override
        public void onReceive(Context context, Intent intent) {
            callCount++;
            Log.d(TAG, "on Recieve ");
            if (callCount == 2)
            {
                if (tt2Bot.getIsRunning())
                {
                    tt2Bot.stop();
                    while (tt2Bot.getIsRunning()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                    tt2Bot.start();
                callCount = 0;
            }
        }
    };


    private long lastClick;

    @Override public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        Log.d(TAG,"Register ACTION_MEDIA_BUTTON");
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(broadcastReceiver,filter);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        prestigeCounterView =new TextView(this);
        WindowManager.LayoutParams paramsPrestigeView = new WindowManager.LayoutParams(
                convertDpiToPixel(140),
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsPrestigeView.gravity = Gravity.TOP|Gravity.LEFT;
        paramsPrestigeView.x = (metrics.widthPixels/2) - (convertDpiToPixel(140) + 5);
        paramsPrestigeView.y = 0;
        prestigeCounterView.setBackgroundColor(Color.argb(180,0,0,0));
        prestigeCounterView.setSingleLine(false);



        windowManager.addView(prestigeCounterView, paramsPrestigeView);
        prestigeCounterView.bringToFront();

        startStopButton = new Button(this);
        startStopButton.setBackgroundResource(R.drawable.play);
        BotSettings botSettings = new BotSettings(preferences, getApplicationContext());
        tt2Bot = new TT2Bot(getApplicationContext(),botSettings);
        tt2Bot.setUpdateUiCallBack(new AbstractBot.UpdateUi() {
            @Override
            public void updatePrestigeTime(String time) {
                prestigeCounterView.post(new Runnable() {
                    @Override
                    public void run() {
                        prestigeCounterView.setText(time);
                        prestigeCounterView.invalidate();
                    }
                });
            }
        });
        startStopButton.setText("");
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                long currentTime = new Date().getTime();
                if (currentTime - lastClick > 1000) {
                    lastClick = currentTime;
                    if (!tt2Bot.getIsRunning()) {
                        Log.d(TAG,"startBot");
                        tt2Bot.start();
                        startStopButton.setBackgroundResource(R.drawable.stop);
                    } else {
                        Log.d(TAG,"stopBot");
                        startStopButton.setBackgroundResource(R.drawable.play);
                        tt2Bot.stop();
                        while (tt2Bot.getIsRunning())
                        {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d(TAG,"bot stopped");
                    }
                }
            }
        });

        int iconsize = convertDpiToPixel(45);


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
        startStopButton.bringToFront();

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

                Log.d(TAG, "onKey " + event.getCharacters() );
                if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN )
                {
                    if (tt2Bot.getIsRunning()) {
                        tt2Bot.stop();
                        while (tt2Bot.getIsRunning()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                        tt2Bot.start();
                }
                return false;
            }
        });


        startStopButton.setVisibility(View.GONE);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tt2Bot.getIsRunning())
            tt2Bot.stop();
        tt2Bot.destroy();
        if (startStopButton != null) windowManager.removeView(startStopButton);
        if (closeButton != null) windowManager.removeView(closeButton);
        unregisterReceiver(broadcastReceiver);
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

}
