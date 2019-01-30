package clickerbot.com.troop.clickerbot.test;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.R;
import clickerbot.com.troop.clickerbot.touch.NativeTouchHandler;
import clickerbot.com.troop.clickerbot.touch.ShellInputEventHandler;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class ClickTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.click_test_activity);
        Button startTestButton = findViewById(R.id.button_start_test);

        startTestButton.setOnClickListener(v -> {

            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    TouchInterface nativeTouchHandler = new NativeTouchHandler("/dev/input/event6");
                    runTouchTest(nativeTouchHandler);
                    /*Thread.sleep(2000);
                    nativeTouchHandler = new ShellInputEventHandler(1);
                    runTouchTest(nativeTouchHandler);*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private void runTouchTest(TouchInterface nativeTouchHandler) throws InterruptedException, IOException {
        nativeTouchHandler.start();
        nativeTouchHandler.tap(new Point(240,71));
        Thread.sleep(1000);
        nativeTouchHandler.swipeVertical(new Point(200,782), new Point(200,546));
        Thread.sleep(1000);
        nativeTouchHandler.swipeVertical(new Point(200,546), new Point(200,782));
        nativeTouchHandler.stop();
        nativeTouchHandler.close();
    }
}
