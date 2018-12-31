package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import clickerbot.com.troop.clickerbot.R;

public class BotSettings {

    private final String TAG = BotSettings.class.getSimpleName();

    public final boolean useHS;
    public final boolean useDS;
    public final boolean useHOM;
    public final boolean useFS;
    public final boolean useWC;
    public final boolean useSC;

    public final long timeToPrestige;

    public final int bossFailedCount;

    public final boolean doAutoTap;
    public final boolean autoLvlHeros;
    public final boolean autoLvlSkills;
    public final boolean clickOnFairys;
    public final boolean acceptFairyAdds;
    public final boolean autoPrestige;
    public final boolean autoLvlSwordMaster;

    public BotSettings(SharedPreferences sharedPreferences,Context context)
    {
        useHS = sharedPreferences.getBoolean(context.getString(R.string.useHs),false);
        useDS = sharedPreferences.getBoolean(context.getString(R.string.useDS),false);
        useHOM = sharedPreferences.getBoolean(context.getString(R.string.useHom),false);
        useFS = sharedPreferences.getBoolean(context.getString(R.string.useFS),false);
        useWC = sharedPreferences.getBoolean(context.getString(R.string.useWC),false);
        useSC = sharedPreferences.getBoolean(context.getString(R.string.useSC),false);
        doAutoTap = sharedPreferences.getBoolean(context.getString(R.string.autoTap),false);
        autoLvlHeros = sharedPreferences.getBoolean(context.getString(R.string.autolvlheros),false);
        autoLvlSkills = sharedPreferences.getBoolean(context.getString(R.string.autolvlskills),false);
        clickOnFairys = sharedPreferences.getBoolean(context.getString(R.string.clickonfairyadds),false);
        acceptFairyAdds = sharedPreferences.getBoolean(context.getString(R.string.acceptfairyadds),false);
        autoPrestige = sharedPreferences.getBoolean(context.getString(R.string.auto_prestige),false);
        autoLvlSwordMaster = sharedPreferences.getBoolean(context.getString(R.string.autolvlswordmaster),false);
        String presTime = sharedPreferences.getString(context.getString(R.string.prestige_after_time),"00:60:00");
        String split[] = presTime.split(":");

        if (split.length == 3)
        {
            int h = Integer.parseInt(split[0]);
            int m = Integer.parseInt(split[1]);
            int s = Integer.parseInt(split[2]);
            timeToPrestige = (((h * 60 +m)*60)+s)*1000;
        }
        else if (split.length == 2)
        {
            int m = Integer.parseInt(split[0]);
            int s = Integer.parseInt(split[1]);
            timeToPrestige =((m*60)+s)*1000;
        }
        else
        {
            int h = Integer.parseInt(split[0]);
            timeToPrestige = (h * 60 *60 *1000);
        }

        bossFailedCount = Integer.parseInt(sharedPreferences.getString(context.getString(R.string.after_boss_failed),"3"));
        Log.d(TAG, "Skills used HS:" + useHS + " DS:" + useDS+" Hom:"+useHOM+" FS:" + useFS + " WC:"+useWC +" SC: "+useSC);
        Log.d(TAG,"auto lvl heros:" + autoLvlHeros + " auto Tap:" + doAutoTap + " auto lvl skills"+ autoLvlSkills);
        Log.d(TAG, "time to prestige:" + presTime + " ms:" + timeToPrestige + " bossfailed:" + bossFailedCount);

    }


}
