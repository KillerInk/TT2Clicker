package clickerbot.com.troop.clickerbot;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;

import clickerbot.com.troop.clickerbot.screencapture.MediaProjectionScreenCapture;
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
    private TT2Bot tt2Bot;
    private static String botRunningSettingKey = "botRunning";
    private BotServiceView serviceView;
    private MediaProjectionScreenCapture mediaProjectionScreenCapture;


    private int mScreenDensity;

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override public void onCreate() {
        super.onCreate();
        startForeground(1, new Notification());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        Log.d(TAG,"Register ACTION_MEDIA_BUTTON");
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(broadcastReceiver,filter);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        serviceView =new BotServiceView(getApplicationContext());
        serviceView.setClickerBotService(this);

        WindowManager.LayoutParams paramsPrestigeView = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsPrestigeView.gravity = Gravity.TOP|Gravity.LEFT;
        paramsPrestigeView.x = (metrics.widthPixels/2) - (convertDpiToPixel(150) + 5);
        paramsPrestigeView.y = 0;
        //paramsPrestigeView.width = convertDpiToPixel(110);
        windowManager.addView(serviceView, paramsPrestigeView);
        serviceView.bringToFront();

        mediaProjectionScreenCapture = new MediaProjectionScreenCapture(getApplicationContext()
                ,ClickerBotActivity.mResultData
                ,ClickerBotActivity.mResultCode
                ,serviceView.getSurfaceView()
                ,mScreenDensity);

        BotSettings botSettings = new BotSettings(preferences, getApplicationContext());
        tt2Bot = new TT2Bot(getApplicationContext(),botSettings,mediaProjectionScreenCapture);
        tt2Bot.setUpdateUiCallBack(new AbstractBot.UpdateUi() {
            @Override
            public void updatePrestigeTime(String time) {
                serviceView.post(() -> serviceView.setTextViewText(time));
            }

            @Override
            public void updateImage(Bitmap bitmap) {
                serviceView.post(() -> serviceView.setBitmap(bitmap));
            }
        });

        if (preferences.getBoolean(botRunningSettingKey,false))
            tt2Bot.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        preferences.edit().putBoolean(botRunningSettingKey,tt2Bot.getIsRunning());
        if (tt2Bot.getIsRunning()) {
            tt2Bot.stop();
        }
        tt2Bot.destroy();
        mediaProjectionScreenCapture.close();
        if (serviceView != null) windowManager.removeView(serviceView);
        unregisterReceiver(broadcastReceiver);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int convertDpiToPixel(int dpi)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, getResources().getDisplayMetrics());
    }



}
