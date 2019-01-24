package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class SkillLevelParser {

    private final String TAG = SkillLevelParser.class.getSimpleName();
    private final int width = 16;
    private final int height = 11;

    int x_cut = 95;

    private final int BACKGROUND_COLOR = Color.argb(255,48,48,48);

    private final int[] array0 = new int[]
            {
                    /*0,0,0,0,0,0,0,0,
                    0,0,0,1,1,1,0,0,
                    0,0,1,1,0,1,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,1,1,1,1,0,
                    0,0,0,0,1,0,0,0,
                    0,0,0,0,0,0,0,0,*/
                    0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,0,1,1,1,1,0,
                    0,0,1,1,0,1,1,0,
                    0,0,1,1,0,0,1,0,
                    0,0,1,1,0,0,1,1,
                    0,0,1,1,0,0,1,1,
                    0,0,1,1,0,0,1,0,
                    0,0,1,1,0,1,1,0,
                    0,0,1,1,1,1,1,0,
                    0,0,0,0,0,0,0,0,
            };
    
    private final int[] array1 = new int[]
            {
                    /*0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,0,0,1,1,0,0,
                    0,0,1,1,1,1,0,0,
                    0,0,1,0,0,1,0,0,
                    0,0,0,0,0,1,0,0,
                    0,0,0,0,0,1,0,0,
                    0,0,0,0,0,1,0,0,
                    0,0,0,0,0,1,0,0,
                    0,0,0,0,0,1,0,0,
                    0,0,0,0,0,0,0,0,*/
                          0,0,0,0,0,0,0,0,
                          0,0,0,0,0,0,0,0,
                          0,0,0,1,1,1,0,0,
                          0,0,1,1,1,1,0,0,
                          0,0,1,0,1,1,0,0,
                          0,0,0,0,1,1,0,0,
                          0,0,0,0,1,1,0,0,
                          0,0,0,0,1,1,0,0,
                          0,0,0,0,1,1,0,0,
                          0,0,0,0,1,1,0,0,
                          0,0,0,0,0,0,0,0,
            };
    private final int[] array2 = new int[]
            {
                    0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,0,0,0,0,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,1,1,0,0,
                    0,0,0,1,1,0,0,0,
                    0,0,1,1,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,0,0,0,0,0,0,
            };
    private final int[] array3 = new int[]
            {
                   /* 0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,1,1,0,0,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,1,1,1,1,0,0,
                    0,0,0,0,0,0,0,0,*/
                           0, 0, 0, 0, 0, 0, 0, 0,
                           0, 0, 1, 1, 1, 1, 1, 0,
                           0, 0, 1, 1, 0, 1, 1, 0,
                           0, 0, 0, 0, 0, 1, 1, 0,
                           0, 0, 0, 0, 1, 1, 0, 0,
                           0, 0, 0, 1, 1, 1, 0, 0,
                           0, 0, 0, 0, 0, 1, 1, 0,
                           0, 0, 0, 0, 0, 1, 1, 0,
                           0, 0, 1, 1, 1, 1, 1, 0,
                           0, 0, 1, 1, 1, 0, 0, 0,
                           0, 0, 0, 0, 0, 0, 0, 0,
            }; 
    
    private final int[] array4 = new int[]
            {
                   /* 0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,0,0,1,1,1,0,
                    0,0,0,0,1,1,1,0,
                    0,0,0,1,0,1,1,0,
                    0,0,1,1,0,1,1,0,
                    0,0,1,0,0,1,1,0,
                    0,1,1,1,1,1,1,1,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,0,0,0,0,*/
                          0,0,0,0,0,0,0,0,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,1,1,1,0,
                          0,0,0,1,1,1,1,0,
                          0,0,0,1,0,1,1,0,
                          0,0,1,0,0,1,1,0,
                          0,1,1,0,0,1,1,0,
                          0,1,1,1,1,1,1,1,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,0,0,0,0,
            };

    private final int[] array5 = new int[]
            {
                    /*0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,1,1,0,0,0,0,
                    0,0,1,1,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,0,0,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,1,1,1,1,0,0,
                    0,0,0,0,0,0,0,0,*/
                          0,0,0,0,0,0,0,0,
                          0,0,0,0,0,0,0,0,
                          0,0,1,1,1,1,1,0,
                          0,0,1,1,0,0,0,0,
                          0,0,1,1,0,0,0,0,
                          0,0,1,1,1,1,1,0,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,0,1,1,0,
                          0,0,1,1,1,1,1,0,
                          0,0,0,0,0,0,0,0,
            };
    
    private final int[] array6 = new int[]
            {
                    /*0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,0,1,1,1,1,0,
                    0,0,1,1,0,0,0,0,
                    0,0,1,0,0,0,0,0,
                    0,0,1,0,1,1,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,1,0,1,1,0,
                    0,0,0,1,1,1,0,0,
                    0,0,0,0,0,0,0,0,*/
                          0,0,0,0,0,0,0,0,
                          0,0,0,0,1,1,1,0,
                          0,0,0,1,1,1,1,0,
                          0,0,1,1,0,0,0,0,
                          0,0,1,0,1,1,0,0,
                          0,0,1,1,1,1,1,0,
                          0,0,1,1,0,0,1,1,
                          0,0,1,1,0,0,1,1,
                          0,0,1,1,1,1,1,0,
                          0,0,0,1,1,1,0,0,
                          0,0,0,0,0,0,0,0,
            }; 
    private final int[] array7 = new int[]
            {
                    /*0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,0,0,0,0,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,0,0,0,1,0,0,
                    0,0,0,0,1,1,0,0,
                    0,0,0,0,1,0,0,0,
                    0,0,0,1,1,0,0,0,
                    0,0,0,1,1,0,0,0,
                    0,0,0,0,0,0,0,0,*/
                          0,0,0,0,0,0,0,0,
                          0,0,0,0,0,0,0,0,
                          0,0,1,1,1,1,1,1,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,0,1,1,0,
                          0,0,0,0,1,1,0,0,
                          0,0,0,0,1,1,0,0,
                          0,0,0,1,1,0,0,0,
                          0,0,0,1,1,0,0,0,
                          0,0,0,0,0,0,0,0,
            };  
    private final int[] array8 = new int[]
            {
                    /*0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,1,0,1,1,0,
                    0,0,0,1,1,1,0,0,
                    0,0,1,1,0,1,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,0,1,1,1,0,0,
                    0,0,0,0,0,0,0,0,*/
                          0,0,0,0,0,0,0,0,
                          0,0,0,0,0,0,0,0,
                          0,0,1,1,1,1,1,0,
                          0,0,1,1,0,1,1,0,
                          0,0,1,1,0,1,1,0,
                          0,0,0,1,1,1,0,0,
                          0,0,1,1,0,1,1,0,
                          0,0,1,0,0,0,1,1,
                          0,0,1,1,0,0,1,1,
                          0,0,1,1,1,1,1,0,
                          0,0,0,0,0,0,0,0,
            }; 
    
    private final int[] array9 = new int[]
            {
                    0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,
                    0,0,1,1,1,1,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,0,1,0,
                    0,0,1,0,0,1,1,0,
                    0,0,1,1,1,0,1,0,
                    0,0,0,0,0,0,1,0,
                    0,0,0,0,0,1,1,0,
                    0,0,1,1,1,1,0,0,
                    0,0,0,0,0,0,0,0,
            };


    private TT2Bot bot;
    private int pixels[] = new int[width/2 * height];
    private Patch[] patches;

    public SkillLevelParser(TT2Bot bot)
    {
        this.bot = bot;
        patches = new Patch[10];
        patches[0] = new Patch(array0,"0");
        patches[1] = new Patch(array1,"1");
        patches[2] = new Patch(array2,"2");
        patches[3] = new Patch(array3,"3");
        patches[4] = new Patch(array4,"4");
        patches[5] = new Patch(array5,"5");
        patches[6] = new Patch(array6,"6");
        patches[7] = new Patch(array7,"7");
        patches[8] = new Patch(array8,"8");
        patches[9] = new Patch(array9,"9");
    }

    public void parseAllSkillLevels(Skill hs, Skill ds, Skill hom,Skill fs, Skill wc,Skill sc)
    {
        Bitmap map = bot.getScreeCapture().getBitmap().copy(bot.getScreeCapture().getBitmap().getConfig(),false);
        int hslvl = getLevel(228,map);
        hs.setSkillLvl(hslvl);
        int dslvl = getLevel(303,map);
        ds.setSkillLvl(dslvl);
        int homlvl = getLevel(379,map);
        hom.setSkillLvl(homlvl);
        int fslvl = getLevel(454,map);
        fs.setSkillLvl(fslvl);
        int wclvl = getLevel(530,map);
        wc.setSkillLvl(wclvl);
        int sclvl = getLevel(605,map);
        sc.setSkillLvl(sclvl);
        map.recycle();
        Log.d(TAG,"hs:" + hslvl +" ds:"+dslvl+" hom:"+ homlvl +" fs:" +fslvl +" wc:"+wclvl + " sc:"+sclvl);
    }

    private int getLevel(int y,Bitmap map)
    {
        String end = getLevelPart(map,x_cut,y,width/2,height) + getLevelPart(map,x_cut+width/2, y, width/2,height);
        try {
            return Integer.parseInt(end);
        }
        catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }

    private String getLevelPart(Bitmap map,int x, int y, int width, int height)
    {
        for (int t = 0; t < patches.length; t++) {
            patches[t].reset();
        }
        int i = 0;
        int color;
        float[] hsv = new float[3];
        //prepare the input data and turn every pixel to 0 with a hs V < 0.74
        //all other brighter pixel get set to 1.
        for (int y1 = y; y1 < y +height; y1++)
        {
            for (int x1 = x; x1 <  x+width; x1++ )
            {
                color = map.getPixel(x1,y1);
                Color.RGBToHSV(Color.red(color),Color.green(color), Color.blue(color), hsv);
                if (hsv[2] < 0.74)
                    pixels[i++] = 0;
                else
                    pixels[i++] = 1;
            }
        }
        printarrs(pixels);
        //check the patches if one match
        for (int p = 0; p < pixels.length; p++) {
            for (int t = 0; t < patches.length; t++) {
                patches[t].checkIfFailed(pixels, p);
            }
        }
        //get the string value that match
        for (int t = 0; t < patches.length; t++) {
            if(patches[t].doMatch())
                return patches[t].getRet();
        }
        return "";
    }


    private boolean doPatchMatch(int[] bitmap, int[] patch)
    {
        printarrs(bitmap);
        int fails = 0;
        for (int i = 0; i < patch.length; i++)
        {
            if (patch[i] == 1 && bitmap[i] != patch[i])
                fails++;
        }
        if (fails < 5)
            return true;
        return false;
    }

    private void printarrs(int[] bitmap)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" \nbitmap                patch0                patch1                patch2                patch3                patch4                "+
                "patch5                patch6                patch7                patch8                patch9\n");
        for (int y = 0; y < 11; y++)
        {


            for (int x = 0; x < 8; x++)
            {
                stringBuilder.append(bitmap[(width/2)*y+x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array0[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array1[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array2[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array3[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array4[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array5[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array6[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array7[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array8[(width/2)*y + x] + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(array9[(width/2)*y + x] + ",");
            }
            stringBuilder.append("\n");
        }
        Log.d(TAG, stringBuilder.toString());
    }

    private class Patch{
        private int[] patch;
        int fails;
        String ret;

        public Patch(int[] patch, String ret)
        {
            this.patch = patch;
            this.ret = ret;
        }

        public void reset()
        {
            fails = 0;
        }

        public boolean doMatch()
        {
            return fails < 5;
        }

        public String getRet()
        {
            return ret;
        }

        public void checkIfFailed(int[] bitmap, int i)
        {
            if (patch[i] == 1 && bitmap[i] != patch[i])
                fails++;
        }
    }
}
