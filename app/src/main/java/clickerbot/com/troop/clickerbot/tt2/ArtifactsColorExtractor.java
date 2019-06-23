package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import clickerbot.com.troop.clickerbot.ColorUtils;
import clickerbot.com.troop.clickerbot.R;
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class ArtifactsColorExtractor extends Menu {

    private final String TAG = ArtifactsColorExtractor.class.getSimpleName();

    private final int x_start = 9;
    private final int x_end = 66;

    private final int colortofind = -8227733; //Color.argb(255,130,160,107);
    private final int colortofind_x_lookup = 11;

    private Bitmap artifactImgs[];
    private Artifacts[] artifacts;
    FileOutputStream stringWriter;

    public ArtifactsColorExtractor(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        artifacts = Artifacts.values();
        artifactImgs = new Bitmap[58];
        artifactImgs[0] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._0);
        artifactImgs[1] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._1);
        artifactImgs[2] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._2);
        artifactImgs[3] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._3);
        artifactImgs[4] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._4);
        artifactImgs[5] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._5);
        artifactImgs[6] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._6);
        artifactImgs[7] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._7);
        artifactImgs[8] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._8);
        artifactImgs[9] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._9);
        artifactImgs[10] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._10);
        artifactImgs[11] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._11);
        artifactImgs[12] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._12);
        artifactImgs[13] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._13);
        artifactImgs[14] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._14);
        artifactImgs[15] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._15);
        artifactImgs[16] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._16);
        artifactImgs[17] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._17);
        artifactImgs[18] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._18);
        artifactImgs[19] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._19);
        artifactImgs[20] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._20);
        artifactImgs[21] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._21);
        artifactImgs[22] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._22);
        artifactImgs[23] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._23);
        artifactImgs[24] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._24);
        artifactImgs[25] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._25);
        artifactImgs[26] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._26);
        artifactImgs[27] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._27);
        artifactImgs[28] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._28);
        artifactImgs[29] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._29);
        artifactImgs[30] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._30);
        artifactImgs[31] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._31);
        artifactImgs[32] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._32);
        artifactImgs[33] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._33);
        artifactImgs[34] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._34);
        artifactImgs[35] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._35);
        artifactImgs[36] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._36);
        artifactImgs[37] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._37);
        artifactImgs[38] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._38);
        artifactImgs[39] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._39);
        artifactImgs[40] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._40);
        artifactImgs[41] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._41);
        artifactImgs[42] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._42);
        artifactImgs[43] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._43);
        artifactImgs[44] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._44);
        artifactImgs[45] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._45);
        artifactImgs[46] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._46);
        artifactImgs[47] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._47);
        artifactImgs[48] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._48);
        artifactImgs[49] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._49);
        artifactImgs[50] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._50);
        artifactImgs[51] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._51);
        artifactImgs[52] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._52);
        artifactImgs[53] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._53);
        artifactImgs[54] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._54);
        artifactImgs[55] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._55);
        artifactImgs[56] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._56);
        artifactImgs[57] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._57);

    }

    @Override
    void init(ExecuterTask task) {

    }

    @Override
    boolean checkIfRdyToExecute() {
        return false;
    }

    public void extractColors(ExecuterTask task) throws IOException, InterruptedException {
        openArtifactMenu(task);
        if (!isMenuMaximized())
            maximiseMenu(task);
        gotToTopMaximised(task);
        Thread.sleep(400);
       List<Point> artifcatsYPosList = getArtifactPositions();
       Bitmap artifactImg;
       int artifactsProcessed = 0;
       List<Long> sumsList = new ArrayList<>();

       try {
           stringWriter = new FileOutputStream("/sdcard/Pictures/match.txt");


           while (artifcatsYPosList.size() > 0)
           {
               for (int i = 0; i < artifcatsYPosList.size(); i++)
               {
                   int width = 57;
                   int height = artifcatsYPosList.get(i).y -artifcatsYPosList.get(i).x;
                    //Log.d(TAG,"WxH:" + width +"x"+height);
                    if (height >= 57) {
                        artifactImg = bot.getScreeCapture().getBitmapFromPos(x_start, artifcatsYPosList.get(i).x, width, 56);

                        Artifacts artifact = findArtifact(artifactImg,artifactsProcessed);
                        Log.d(TAG,artifactsProcessed + " Found " + artifact);
                        saveBitmap(artifactImg, "/sdcard/Pictures/" + artifactsProcessed + "_" +artifact + ".png");

                        /*long colorsum = getColorSum(blur(artifactImg,bot.getContext()));
                        if (!sumsList.contains(colorsum)) {
                            saveBitmap(artifactImg, "/sdcard/Pictures/" + artifactsProcessed + ".png");
                            Log.d(TAG, "save img from arti:" + artifactsProcessed + " Colorsum =" + colorsum);
                            sumsList.add(colorsum);
                        else
                        {
                            Log.d(TAG, "Have this already added:" + artifactsProcessed + " Colorsum =" + colorsum);
                        }*/
                        artifactsProcessed++;
                    }
               }
               swipeDownMaximised();
               Thread.sleep(500);
               artifcatsYPosList = getArtifactPositions();
           }
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
       finally {
           stringWriter.close();
       }

    }



    private long getColorSum(Bitmap bitmap)
    {
        long sum =0;
        for (int y =0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                //sum += getGray(bitmap.getPixel(x,y)) ;
                sum += bitmap.getPixel(x,y);
                //sum += (bitmap.getPixel(x,y)+bitmap.getPixel(x+1,y)+bitmap.getPixel(x+1,y+1)+bitmap.getPixel(x,y+1))/4;
            }
        }
        double retsum = sum / 1000000 ;
        return Math.round(retsum);

    }

    private Artifacts findArtifact(Bitmap input, int filetodif)
    {
        int[] matches = new int[58];
        for (int i =0; i< artifactImgs.length; i++)
        {
            matches[i] = findMatch(input, i);
        }
        writeMatch(matches,filetodif);
        int bestmatchpos = 0;
        int bestmatch = 0;
        for (int i =0; i< matches.length; i++)
        {
            if (bestmatch < matches[i]) {
                bestmatch = matches[i];
                bestmatchpos = i;
            }
            else if (matches[i] == bestmatch)
                bestmatchpos = i;
        }
        Log.d(TAG,"BestMatch/Pos:" + bestmatch+"/"+bestmatchpos);
        return artifacts[bestmatchpos];
    }

    private final int reduceRangeToLookUp = 1;
    private int findMatch(Bitmap input, int imgtodiff)
    {
        int matchcount = 0;
        int matchcount_1 = 0;
        int matchcount1 = 0;
        int width = artifactImgs[imgtodiff].getWidth() -reduceRangeToLookUp;
        int height = artifactImgs[imgtodiff].getHeight() -reduceRangeToLookUp;
        Bitmap lookupMap = artifactImgs[imgtodiff];

       /* Bitmap lookupscale = scale(lookupMap,lookupMap.getWidth()/2,lookupMap.getHeight()/2);
        Bitmap inputscale = scale(input,lookupMap.getWidth()/2,lookupMap.getHeight()/2);

        for(int y = 1; y < inputscale.getHeight()-1;y++)
        {
            for (int x = 0; x < inputscale.getWidth();x++)
            {
                if (inputscale.getPixel(x,y) ==  lookupscale.getPixel(x,y))
                    matchcount++;
                if(inputscale.getPixel(x,y-1) ==  lookupscale.getPixel(x,y))
                    matchcount_1++;
                if(inputscale.getPixel(x,y+1) ==  lookupscale.getPixel(x,y))
                    matchcount1++;
            }
        }
*/
        for(int y = reduceRangeToLookUp; y < height;y++)
        {
            for (int x = reduceRangeToLookUp; x < width;x++)
            {
                if(pixIsInRange(input.getPixel(x,y), lookupMap.getPixel(x,y)))
                    matchcount++;
                /*if(pixIsInRange(input.getPixel(x,y-1), lookupMap.getPixel(x,y)))
                    matchcount_1++;
                if(pixIsInRange(input.getPixel(x,y+1), lookupMap.getPixel(x,y)))
                    matchcount1++;*/
            }
        }
        //Log.d(TAG,"matches:" + matchcount+" / " + matchcount1 +" / " +matchcount_1);
        int max = 0;
        if (matchcount > max)
            max = matchcount;
        if (matchcount1 > max)
            max = matchcount1;
        if (matchcount_1 > max)
            max = matchcount_1;
        return max;

    }

    private boolean check9x9PixelInRange(int x, int y, int y2, Bitmap input, Bitmap lookupMap)
    {
        return      pixIsInRange(input.getPixel(x,y), lookupMap.getPixel(x,y2))
                &&  pixIsInRange(input.getPixel(x-1,y), lookupMap.getPixel(x-1,y2))
                &&  pixIsInRange(input.getPixel(x+1,y), lookupMap.getPixel(x+1,y2))
                &&  pixIsInRange(input.getPixel(x,y+1), lookupMap.getPixel(x,y2+1))
                &&  pixIsInRange(input.getPixel(x-1,y+1), lookupMap.getPixel(x-1,y2+1))
                &&  pixIsInRange(input.getPixel(x+1,y+1), lookupMap.getPixel(x+1,y2+1))
                &&  pixIsInRange(input.getPixel(x,y-1), lookupMap.getPixel(x,y2-1))
                &&  pixIsInRange(input.getPixel(x-1,y-1), lookupMap.getPixel(x-1,y2-1))
                &&  pixIsInRange(input.getPixel(x+1,y-1), lookupMap.getPixel(x+1,y2-1));
    }

    private final int range = 5;

    private int red,green,blue;
    private boolean pixIsInRange(int input, int dif)
    {
        red = Color.red(dif);
        green = Color.green(dif);
        blue = Color.blue(dif);

       if (red < 20 && blue < 20 && green < 20)
            return false;
        return ColorUtils.colorIsInRange(input,red-range, red+range,green-range,green+range,blue-range,blue+range);

    }

    public static Bitmap scale(Bitmap realImage,int newwidth, int newheight) {

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, newwidth,
                newheight, true);
        return newBitmap;
    }

    private int getGray(int color)
    {
        return (Color.red(color) + Color.green(color) + Color.blue(color)) /3 ;
    }

    private Bitmap blur(Bitmap image, Context context) {
        final float BITMAP_SCALE = 0.1f;
        final float BLUR_RADIUS = 3f;
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    private List<Point> getArtifactPositions()
    {
        boolean waitforstart = true;
        boolean lastColorWasGray = true;
        int start = 0;
        List<Point> list =new ArrayList<>();
        int[] colors = bot.getScreeCapture().getColorFromOneVerticalLine(colortofind_x_lookup,50,760);
        int t = 1;
        while(t < colors.length)
        {
            //if (waitforstart && isColorInRange(colors[t-1]) && !isColorInRange(colors[t]) && lastColorWasGray)
            if (waitforstart && colors[t-1] == colortofind && colors[t] != colortofind && lastColorWasGray)
            {
                //Log.d(TAG, "Start: t-1:" + ColorUtils.logColor(colors[t-1]) + " t:" + ColorUtils.logColor(colors[t]) + " color to find:" + ColorUtils.logColor(colortofind));
                waitforstart = false;
                lastColorWasGray = false;
                start = t;
            }
            //else if (!waitforstart && !lastColorWasGray && isColorInRange(colors[t]) && !isColorInRange(colors[t-1]))
            else if (!waitforstart && !lastColorWasGray && colors[t] == colortofind && colors[t-1] != colortofind)
            {
                //Log.d(TAG, "End: t-1:" + ColorUtils.logColor(colors[t-1]) + " t:" + ColorUtils.logColor(colors[t]) + " color to find:" + ColorUtils.logColor(colortofind));
                waitforstart = true;
                lastColorWasGray = true;
                list.add(new Point(start+51, t+51));
            }
            t++;
        }
        return list;
    }

    private boolean isColorInRange(int color)
    {
        return ColorUtils.colorIsInRange(color,54,130,46,116,40,107);
    }

    private void saveBitmap(Bitmap bitmap,String path){
        if(bitmap!=null){
            try {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(path);

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMatch(int[] matcharray, int filetodif)
    {
        try {
            if (filetodif < artifacts.length)
                stringWriter.write((filetodif +"######" + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i =0; i< matcharray.length; i++)
        {
            try {
                stringWriter.write((i +" " +artifacts[i] + " " + matcharray[i] + "\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            stringWriter.write(("\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
