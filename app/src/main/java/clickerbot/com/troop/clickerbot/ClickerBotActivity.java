package clickerbot.com.troop.clickerbot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by troop on 15.12.2016.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ClickerBotActivity extends AppCompatActivity {

    private final String TAG = ClickerBotActivity.class.getSimpleName();

    public final static int REQUEST_CODE = -1010101;

    private Button startbot;
    private SharedPreferences preferences;
    private SettingsFragment fragment;

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

        startbot = (Button)findViewById(R.id.button_startbot);
        startbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startClickerBackgroundService();
            }
        });

        /*getFragmentManager().beginTransaction()
                .replace(R.id.setting_fragment, new PrefsFragment())
                .commit();*/

    }

    @Override
    protected void onPause() {

        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        fragment = null;
        super.onPause();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
            HIDENAVBAR();
    }

    @Override
    protected void onResume() {
        fragment = new SettingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_fragment_container, fragment)
                .commit();
        super.onResume();
        HIDENAVBAR();
    }

    private boolean checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(getApplicationContext())) {
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
            onBackPressed();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG,"onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

}
