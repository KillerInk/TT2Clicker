package clickerbot.com.troop.clickerbot.tt2;
import android.graphics.Bitmap;
import android.util.Log;
import clickerbot.com.troop.clickerbot.bmptxtparser.ParseTxtFromBmp;

public class SkillLevelParser {


    private final String TAG = SkillLevelParser.class.getSimpleName();
    private final int width = 16;
    private final int height = 11;

    int x_cut = 95;

    private TT2Bot bot;
    private ParseTxtFromBmp parseTxtFromBmp;

    public SkillLevelParser(TT2Bot bot)
    {
        this.bot = bot;
        parseTxtFromBmp =new ParseTxtFromBmp(bot.getContext(),width,height);
    }

    public void parseAllSkillLevels(Skill hs, Skill ds, Skill hom,Skill fs, Skill wc,Skill sc)
    {
        Bitmap map = bot.getScreeCapture().getBitmap().copy(bot.getScreeCapture().getBitmap().getConfig(),false);
        Log.d(TAG,"HS");
        int hslvl = getLevel(334,map);
        hs.setSkillLvl(hslvl);
        Log.d(TAG,"DS");
        int dslvl = getLevel(409,map);
        ds.setSkillLvl(dslvl);
        Log.d(TAG,"HOM");
        int homlvl = getLevel(485,map);
        hom.setSkillLvl(homlvl);
        Log.d(TAG,"FS");
        int fslvl = getLevel(561,map);
        fs.setSkillLvl(fslvl);
        Log.d(TAG,"WC");
        int wclvl = getLevel(636,map);
        wc.setSkillLvl(wclvl);
        Log.d(TAG,"SC");
        int sclvl = getLevel(712,map);
        sc.setSkillLvl(sclvl);
        map.recycle();
        Log.d(TAG,"hs:" + hslvl +" ds:"+dslvl+" hom:"+ homlvl +" fs:" +fslvl +" wc:"+wclvl + " sc:"+sclvl);
    }

    private int getLevel(int y,Bitmap map)
    {
        String end = parseTxtFromBmp.getIntTxtFromBmp(map,x_cut,y,width/2,height) + parseTxtFromBmp.getIntTxtFromBmp(map,x_cut+width/2, y, width/2,height);
        try {
            return Integer.parseInt(end);
        }
        catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }


}
