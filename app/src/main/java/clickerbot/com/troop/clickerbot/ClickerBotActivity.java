package clickerbot.com.troop.clickerbot;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

/**
 * Created by troop on 15.12.2016.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class ClickerBotActivity extends Activity {


    public final static int REQUEST_CODE = -1010101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ClickerBotService.class);
        if(checkDrawOverlayPermission()) {
            startService(intent);
            finish();
        }
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

}
