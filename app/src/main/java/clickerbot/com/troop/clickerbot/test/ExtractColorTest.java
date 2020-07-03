package clickerbot.com.troop.clickerbot.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import clickerbot.com.troop.clickerbot.R;

public class ExtractColorTest extends AppCompatActivity {

    private final String TAG = ExtractColorTest.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Integer> arr = new ArrayList();
        //readHeroLvlButtonColors(arr);
        Bitmap map = BitmapFactory.decodeResource(getResources(),R.drawable.hero_button_gray);
        dumpColors(map,arr);
        printList(arr);
    }

    private void readHeroLvlButtonColors(ArrayList<Integer> arr) {
        Bitmap map = BitmapFactory.decodeResource(getResources(),R.drawable.hero_button_skill_unlock1);
        dumpColors(map,arr);

        map = BitmapFactory.decodeResource(getResources(),R.drawable.hero_button_skill_unlock2);
        dumpColors(map,arr);

        map = BitmapFactory.decodeResource(getResources(),R.drawable.hero_button_level_hero);
        dumpColors(map,arr);

        map = BitmapFactory.decodeResource(getResources(),R.drawable.hero_button_skill_unlock1_flash);
        dumpColors(map,arr);
    }

    private void dumpColors(Bitmap map, ArrayList<Integer> arrayList)
    {
        for (int i = 0; i< map.getHeight(); i++)
        {
            int color = map.getPixel(0,i);
            if (!arrayList.contains(color))
                arrayList.add(color);
        }
    }

    private void printList(ArrayList<Integer> arrayList)
    {
        String s = "ArrayList<Integer> arr = new ArrayList();\n";
        for (int i =0; i< arrayList.size(); i++)
        {
            s += "arr.add(" +arrayList.get(i) +");\n";
        }
        Log.d(TAG, s);
    }
}
