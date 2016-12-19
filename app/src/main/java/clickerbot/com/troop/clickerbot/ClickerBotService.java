package clickerbot.com.troop.clickerbot;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import java.util.ArrayList;
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
    private volatile boolean working = false;
    final String cmd = "/system/bin/input tap 700 700\n";
    private int workerCount = 6;
    private int sleepTimeBetweenWorker = 400;
    Random r = new Random();

    @Override public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        startStopButton = new Button(this);
        startStopButton.setBackgroundResource(R.drawable.play);

        startStopButton.setText("");
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!working)
                {
                    working = true;
                    startStopButton.setBackgroundResource(R.drawable.stop);
                    doWork();
                }
                else {
                    working = false;
                    startStopButton.setBackgroundResource(R.drawable.play);
                    stopWork();
                }
            }
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                convertDpiToPixel(50),
                convertDpiToPixel(50),
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 300;

        windowManager.addView(startStopButton, params);

        closeButton = new Button(this);
        closeButton.setText("");
        closeButton.setBackgroundResource(R.drawable.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });

        params = new WindowManager.LayoutParams(
                convertDpiToPixel(50),
                convertDpiToPixel(50),
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 500;

        windowManager.addView(closeButton, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        working = false;
        //events.Release();
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

        return r.nextInt(sleepTimeBetweenWorker - 100) + 100;
    }


    private void doWork()
    {
        Log.d(TAG,"SendTouch");
        createthread();

    }

    private void stopWork()
    {
    }



    private void createthread()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RootTouch> rootTouches = new ArrayList<>();
                for (int i = 0; i< workerCount; i++){
                    rootTouches.add(new RootTouch(i));
                }
                while (ClickerBotService.this.working)
                {
                    for (RootTouch rt : rootTouches)
                    {
                        if (ClickerBotService.this.working) {
                            rt.SendCMD(cmd);
                            try {
                                Thread.sleep(getRandomSleep());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
                for (RootTouch rt : rootTouches)
                    rt.Close();
                rootTouches.clear();
            }
        }).start();
    }
}
