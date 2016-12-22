package clickerbot.com.troop.clickerbot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by troop on 15.12.2016.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ClickerBotActivity extends Activity {


    public static String PREFERENCES_WORKERCOUNT = "workercount";
    public static String PREFERENCES_SLEEPTIME_BETWEEN_WORKERS = "sleeptimebetweenworkers";
    public static String PREFERENCES_CMDSLEEP = "cmdsleep";
    public final static int REQUEST_CODE = -1010101;
    private EditText workercount;
    private EditText sleeptimebetweenworker;
    private Button startbot;
    private SharedPreferences preferences;
    private EditText cmdsleeptime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clickerbotactivity);

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
        if(checkDrawOverlayPermission()) {
            Intent intent = new Intent(this, ClickerBotService.class);
            startService(intent);
            finish();
        }
    }

}
