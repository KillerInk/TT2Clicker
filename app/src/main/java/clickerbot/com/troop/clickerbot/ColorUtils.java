package clickerbot.com.troop.clickerbot;

import android.graphics.Color;

public class ColorUtils {

    public static boolean blueIsInRange(int color,int min, int max)
    {
        int blue = Color.blue(color);
        if (blue >= min && blue <= max)
            return true;
        return false;
    }

    public static boolean redIsInRange(int color,int min, int max)
    {
        int red = Color.red(color);
        if (red >= min && red <= max)
            return true;
        return false;
    }

    public static boolean greenIsInRange(int color,int min, int max)
    {
        int green = Color.green(color);
        if (green >= min && green <= max)
            return true;
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
}
