package clickerbot.com.troop.clickerbot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by troop on 15.12.2016.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ClickerBotActivity extends Activity {


    public static String PREFERENCES_WORKERCOUNT = "workercount";
    public static String PREFERENCES_SLEEPTIME_BETWEEN_WORKERS = "sleeptimebetweenworkers";
    public static String PREFERENCES_CMDSLEEP = "cmdsleep";
    public static String PREFERENCES_TAPX = "tapx";
    public static String PREFERENCES_TAPY = "tapy";
    public final static int REQUEST_CODE = -1010101;
    private EditText workercount;
    private EditText sleeptimebetweenworker;
    private Button startbot;
    private SharedPreferences preferences;
    private EditText cmdsleeptime;
    private Button tapareaselect;
    private ImageView taparea;
    private ArrayList<String> touchAreas;
    private Button closeImageViewButton;

    private final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.clickerbotactivity);
        HIDENAVBAR();
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        sleeptimebetweenworker = (EditText) findViewById(R.id.editText_sleeptimebetweenworkers);
        int t= preferences.getInt(PREFERENCES_SLEEPTIME_BETWEEN_WORKERS, 20);
        sleeptimebetweenworker.setText(t +"");
        sleeptimebetweenworker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tmp = sleeptimebetweenworker.getText().toString();
                int t = 0;
                if (!tmp.equals(""))
                    t = Integer.parseInt(tmp);

                preferences.edit().putInt(PREFERENCES_SLEEPTIME_BETWEEN_WORKERS,t).commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        workercount = (EditText) findViewById(R.id.editText_worker);
        workercount.setText(preferences.getInt(PREFERENCES_WORKERCOUNT, 15)+"");
        workercount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tmp = workercount.getText().toString();
                int t = 0;
                if (!tmp.equals(""))
                    t = Integer.parseInt(tmp);
                preferences.edit().putInt(PREFERENCES_WORKERCOUNT,t).commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        cmdsleeptime = (EditText) findViewById(R.id.editText_commandsleeptime);
        cmdsleeptime.setText(preferences.getInt(PREFERENCES_CMDSLEEP, 10)+"");
        cmdsleeptime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String tmp = cmdsleeptime.getText().toString();
                int t = 0;
                if (!tmp.equals(""))
                    t = Integer.parseInt(tmp);
                preferences.edit().putInt(PREFERENCES_CMDSLEEP,t).commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        startbot = (Button)findViewById(R.id.button_startbot);
        startbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startClickerBackgroundService();
            }
        });


        tapareaselect = (Button)findViewById(R.id.button_taparea);
        tapareaselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touchAreas = new ArrayList<String>();
                taparea.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.taptitans));
                taparea.setVisibility(View.VISIBLE);
                closeImageViewButton.setVisibility(View.VISIBLE);
            }
        });

        closeImageViewButton =(Button)findViewById(R.id.button_closeImageView);
        closeImageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taparea.setVisibility(View.GONE);
                closeImageViewButton.setVisibility(View.GONE);
                String save = "";
                for (String s : touchAreas)
                {
                    save += s+";";
                }
                preferences.edit().putString(PREFERENCES_TAPX, save).commit();
            }
        });
        closeImageViewButton.setVisibility(View.GONE);

        taparea = (ImageView)findViewById(R.id.imageView_taparea);
        taparea.setVisibility(View.GONE);
        taparea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                    {
                        touchAreas.add((int)event.getX()+","+(int)event.getY());
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
            HIDENAVBAR();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HIDENAVBAR();
    }

    private boolean checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (!Settings.canDrawOverlays(getApplicationContext())) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
            return false;
        }
        else
            return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if (requestCode == REQUEST_CODE) {
            /** if so check once again if we have permission */
            if (Settings.canDrawOverlays(this)) {
                // continue here - permission was granted
                Intent intent = new Intent(this, ClickerBotService.class);
                startService(intent);
                finish();
            }
        }
    }

    private void startClickerBackgroundService()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP || checkDrawOverlayPermission()) {
            Intent intent = new Intent(this, ClickerBotService.class);
            startService(intent);
            finish();
        }
    }


    private void HIDENAVBAR()
    {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else
        {
            //HIDE nav and action bar
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if (visibility > 0) {
                        if (Build.VERSION.SDK_INT >= 16)
                            getWindow().getDecorView().setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }
}
