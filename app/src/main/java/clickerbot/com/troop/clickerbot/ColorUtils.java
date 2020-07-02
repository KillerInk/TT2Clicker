package clickerbot.com.troop.clickerbot;

import android.graphics.Color;
import android.util.Log;

public class ColorUtils {

    private final static String TAG = ColorUtils.class.getSimpleName();
    private static boolean doLog = false;

    public static boolean colorIsInRange(int color, int red_min, int red_max, int green_min, int green_max, int blue_min, int blue_max)
    {
        return  redIsInRange(color, red_min,red_max) && greenIsInRange(color,green_min,green_max) && blueIsInRange(color, blue_min,blue_max);
    }

    public static boolean colorIsIn1ProcentRange(int baseColor, int inputcolor)
    {
        int b_red = Color.red(baseColor);
        int b_green = Color.green(baseColor);
        int b_blue = Color.blue(baseColor);

        int i_red = Color.red(inputcolor);
        int i_green = Color.green(inputcolor);
        int i_blue = Color.blue(inputcolor);

        if (colorsin1procentRange(b_red,i_red)
        && colorsin1procentRange(b_green,i_green)
        && colorsin1procentRange(b_blue,i_blue))
            return true;
        return false;
    }

    private static boolean colorsin1procentRange(int baseColor, int inputcolor)
    {
        int b_red_min = baseColor - 3;
        int b_red_max = baseColor + 3;
        if (inputcolor >= b_red_min && inputcolor <= b_red_max)
            return true;
        return false;
    }

    private static void log(String t)
    {
        Log.d(TAG, t);
    }

    public static boolean blueIsInRange(int color,int min, int max)
    {
        int blue = Color.blue(color);
        if (blue >= min && blue <= max)
            return true;
        if (doLog)
            log("Blue: " + blue + " Range:" +min +"/"+max);
        return false;
    }

    public static boolean redIsInRange(int color,int min, int max)
    {
        int red = Color.red(color);
        if (red >= min && red <= max)
            return true;
        if (doLog)
            log("Red: " + red + " Range:" +min +"/"+max);
        return false;
    }

    public static boolean greenIsInRange(int color,int min, int max)
    {
        int green = Color.green(color);
        if (green >= min && green <= max)
            return true;
        if (doLog)
            log("Green: " + green + " Range:" +min +"/"+max);
        return false;
    }

    public static boolean redEquals(int color, int val)
    {
        if (Color.red(color) == val)
            return true;
        return false;
    }

    public static boolean blueEquals(int color, int val)
    {
        if (Color.blue(color) == val)
            return true;
        return false;
    }

    public static boolean greenEquals(int color, int val)
    {
        if (Color.green(color) == val)
            return true;
        return false;
    }

    public static String getColorString(int color)
    {
        return "color " + color + " r:" +Color.red(color) + " g:"+Color.green(color) + " b:" +Color.blue(color);
    }

    public static boolean isGray(int color)
    {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        if (r == g && r == b)
            return true;
        if (r +10 > g && r -10 < g && r+10 > b && r-10 < b)
            return true;
        return  false;
    }

    public static String logColor(int color)
    {
       return  "R:" + Color.red(color) + " G:" + Color.green(color) + " B:"+Color.blue(color);
    }
}
