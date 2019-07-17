package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
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
    private final int colortofind_x_lookup = 469;

    private Bitmap artifactImgs[];
    private Artifacts[] artifacts;
    private List<Artifacts> artifactsleveld;

    private final int click_X = 424;

    public ArtifactsColorExtractor(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        artifacts = Artifacts.values();
        artifactsleveld = new ArrayList<>();
        artifactImgs = new Bitmap[58];
        BitmapFactory.Options bitopt=new BitmapFactory.Options();
        bitopt.inScaled = false;
        artifactImgs[0] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._0,bitopt);
        artifactImgs[1] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._1,bitopt);
        artifactImgs[2] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._2,bitopt);
        artifactImgs[3] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._3,bitopt);
        artifactImgs[4] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._4,bitopt);
        artifactImgs[5] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._5,bitopt);
        artifactImgs[6] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._6,bitopt);
        artifactImgs[7] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._7,bitopt);
        artifactImgs[8] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._8,bitopt);
        artifactImgs[9] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._9,bitopt);
        artifactImgs[10] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._10,bitopt);
        artifactImgs[11] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._11,bitopt);
        artifactImgs[12] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._12,bitopt);
        artifactImgs[13] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._13,bitopt);
        artifactImgs[14] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._14,bitopt);
        artifactImgs[15] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._15,bitopt);
        artifactImgs[16] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._16,bitopt);
        artifactImgs[17] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._17,bitopt);
        artifactImgs[18] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._18,bitopt);
        artifactImgs[19] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._19,bitopt);
        artifactImgs[20] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._20,bitopt);
        artifactImgs[21] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._21,bitopt);
        artifactImgs[22] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._22,bitopt);
        artifactImgs[23] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._23,bitopt);
        artifactImgs[24] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._24,bitopt);
        artifactImgs[25] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._25,bitopt);
        artifactImgs[26] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._26,bitopt);
        artifactImgs[27] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._27,bitopt);
        artifactImgs[28] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._28,bitopt);
        artifactImgs[29] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._29,bitopt);
        artifactImgs[30] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._30,bitopt);
        artifactImgs[31] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._31,bitopt);
        artifactImgs[32] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._32,bitopt);
        artifactImgs[33] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._33,bitopt);
        artifactImgs[34] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._34,bitopt);
        artifactImgs[35] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._35,bitopt);
        artifactImgs[36] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._36,bitopt);
        artifactImgs[37] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._37,bitopt);
        artifactImgs[38] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._38,bitopt);
        artifactImgs[39] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._39,bitopt);
        artifactImgs[40] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._40,bitopt);
        artifactImgs[41] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._41,bitopt);
        artifactImgs[42] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._42,bitopt);
        artifactImgs[43] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._43,bitopt);
        artifactImgs[44] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._44,bitopt);
        artifactImgs[45] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._45,bitopt);
        artifactImgs[46] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._46,bitopt);
        artifactImgs[47] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._47,bitopt);
        artifactImgs[48] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._48,bitopt);
        artifactImgs[49] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._49,bitopt);
        artifactImgs[50] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._50,bitopt);
        artifactImgs[51] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._51,bitopt);
        artifactImgs[52] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._52,bitopt);
        artifactImgs[53] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._53,bitopt);
        artifactImgs[54] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._54,bitopt);
        artifactImgs[55] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._55,bitopt);
        artifactImgs[56] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._56,bitopt);
        artifactImgs[57] = BitmapFactory.decodeResource(ibot.getContext().getResources(), R.drawable._57,bitopt);

    }

    @Override
    void init(ExecuterTask task) {

    }

    @Override
    boolean checkIfRdyToExecute() {
        return false;
    }


    public void extractArtifactImages(ExecuterTask task) throws IOException, InterruptedException {
        openArtifactMenu(task);
        if (!isMenuMaximized())
            maximiseMenu(task);
        gotToTopMaximised(task);
        Thread.sleep(400);
        List<Point> artifcatsYPosList = getArtifactPositionsFromTop();
        Bitmap artifactImg;
        int artifactsProcessed = 0;
        Artifacts artifact;
        //scroll from top to bottom and level artifacts
        while (artifcatsYPosList.size() > 0 && !task.cancelTask) {
            for (int i = 0; i < artifcatsYPosList.size(); i++) {
                int width = 50;
                int height = artifcatsYPosList.get(i).y - artifcatsYPosList.get(i).x;
                //Log.d(TAG,"WxH:" + width +"x"+height);
                if (height >= 57) {
                    artifactImg = bot.getScreeCapture().getBitmapFromPos(x_start, artifcatsYPosList.get(i).x+3, width, 50);

                    artifact = findArtifact(artifactImg, artifactsProcessed);
                    Log.d(TAG, artifactsProcessed + " Found " + artifact);
                    saveBitmap(artifactImg, "/sdcard/Pictures/" + artifactsProcessed + "_" +artifact + ".png");

                    artifactsProcessed++;
                }
            }
            swipeDownMaximised();
            Thread.sleep(500);
            artifcatsYPosList = getArtifactPositionsFromTop();
        }
    }

    public void extractColors(ExecuterTask task) throws IOException, InterruptedException {
        openArtifactMenu(task);
        if (!isMenuMaximized())
            maximiseMenu(task);
        gotToTopMaximised(task);
        Thread.sleep(400);
        List<Point> artifcatsYPosList = getArtifactPositionsFromTop();
        Bitmap artifactImg;
        int artifactsProcessed = 0;
        Artifacts artifact;
        boolean lastArtifactReached = false;
        //scroll from top to bottom and level artifacts
        while (artifcatsYPosList.size() > 0 && !task.cancelTask)
        {
            for (int i = 0; i < artifcatsYPosList.size(); i++)
            {
                int width = 50;
                int height = artifcatsYPosList.get(i).y -artifcatsYPosList.get(i).x;
                //Log.d(TAG,"WxH:" + width +"x"+height);
                if (height >= 57) {
                    artifactImg = bot.getScreeCapture().getBitmapFromPos(x_start, artifcatsYPosList.get(i).x+3, width, 50);

                    artifact = findArtifact(artifactImg,artifactsProcessed);
                    levelArtifact(artifcatsYPosList, artifact, i, height);
                    Log.d(TAG,artifactsProcessed + " Found " + artifact);
                    //saveBitmap(artifactImg, "/sdcard/Pictures/" + artifactsProcessed + "_" +artifact + ".png");

                    artifactsProcessed++;
                    if (artifact == botSettings.artifactsListToLvl.get(botSettings.artifactsListToLvl.size()-1))
                        lastArtifactReached = true;
                }
            }
            if (!lastArtifactReached) {
                swipeDownMaximised();
                Thread.sleep(500);
                artifcatsYPosList = getArtifactPositionsFromTop();
            }
            else
                artifcatsYPosList.clear();
        }


        artifactsleveld.clear();
        Thread.sleep(500);
        lastArtifactReached =false;
        artifcatsYPosList = getArtifactPositionsFromTop();
        //scroll from bottom to top
        while (artifcatsYPosList.size() > 0 && !task.cancelTask)
        {
            for (int i = artifcatsYPosList.size()-1; i >= 0; i--)
            {
                int width = 57;
                int height = artifcatsYPosList.get(i).y -artifcatsYPosList.get(i).x;
                //Log.d(TAG,"WxH:" + width +"x"+height);
                if (height >= 57)
                {
                    artifactImg = bot.getScreeCapture().getBitmapFromPos(x_start, artifcatsYPosList.get(i).x, width, 56);

                    artifact = findArtifact(artifactImg,artifactsProcessed);
                    if (artifact != Artifacts.Unkown)
                        levelArtifact(artifcatsYPosList, artifact, i, height);
                    Log.d(TAG,artifactsProcessed + " Found " + artifact);
                    //saveBitmap(artifactImg, "/sdcard/Pictures/" + artifactsProcessed + "_" +artifact + ".png");

                    artifactsProcessed++;
                    if (artifact == botSettings.artifactsListToLvl.get(0))
                        lastArtifactReached = true;
                }
            }
            if (!lastArtifactReached) {
                swipeUpMaximised();
                Thread.sleep(600);
                artifcatsYPosList = getArtifactPositionsFromTop();
            }
            else
                artifcatsYPosList.clear();
        }
        closeArtifactMenu(task);
        Thread.sleep(500);
    }

    public void levelArtifact(List<Point> artifcatsYPosList, Artifacts artifact, int i, int height) throws InterruptedException {
        if (!artifactsleveld.contains(artifact) && botSettings.artifactsListToLvl.contains(artifact)) {
            Point tapPoint = new Point(click_X, artifcatsYPosList.get(i).x + height / 2);
            switch (artifact.tier) {
                case SS:
                    for (int tap = 0; tap < botSettings.tapOnBooksOfShadowsCount; tap++) {
                        doLongerSingelTap(tapPoint, TAG);
                        Thread.sleep(100);
                    }
                    break;
                case S:
                    for (int tap = 0; tap < botSettings.tapSTierCount; tap++) {
                        doLongerSingelTap(tapPoint, TAG);
                        Thread.sleep(100);
                    }
                    break;
                case A:
                case F:
                    for (int tap = 0; tap < botSettings.tapATierCount; tap++) {
                        doLongerSingelTap(tapPoint, TAG);
                        Thread.sleep(100);
                    }
                    Thread.sleep(100);
                    break;
            }
            artifactsleveld.add(artifact);
        }
    }

    private Artifacts findArtifact(Bitmap input, int filetodif)
    {
        double[] matches = new double[58];
        for (int i =0; i< artifactImgs.length; i++)
        {
            matches[i] = findMatch(input, i);
        }
        int bestmatchpos = 0;
        double bestmatch = 0;
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
        if (bestmatch < 20)
            bestmatchpos = artifacts.length-1;
        return artifacts[bestmatchpos];
    }

    private int reduceRangeToLookUp = 0;
    private double findMatch(Bitmap input, int imgtodiff)
    {
        int matchcount = 0;
        int matchcount_1 = 0;
        int matchcount1 = 0;
        int matchcount_2 = 0;
        int matchcount2 = 0;
        int width = artifactImgs[imgtodiff].getWidth() -reduceRangeToLookUp;
        int height = artifactImgs[imgtodiff].getHeight() -reduceRangeToLookUp;
        Bitmap lookupMap = artifactImgs[imgtodiff];
        int y_center = height/2;
        int[] pixelYCenterLine = bot.getScreeCapture().getColorFromOneHorizontalLine(input,y_center, reduceRangeToLookUp,width);
        int[] pixelYCenterLineMinus1 = bot.getScreeCapture().getColorFromOneHorizontalLine(input,y_center -1, reduceRangeToLookUp,width);
        int[] pixelYCenterLinePlus1 = bot.getScreeCapture().getColorFromOneHorizontalLine(input,y_center +1, reduceRangeToLookUp,width);
        int[] pixelYCenterLineMinus2 = bot.getScreeCapture().getColorFromOneHorizontalLine(input,y_center -2, reduceRangeToLookUp,width);
        int[] pixelYCenterLinePlus2 = bot.getScreeCapture().getColorFromOneHorizontalLine(input,y_center +2, reduceRangeToLookUp,width);
        int[] lookupYCenterLine = bot.getScreeCapture().getColorFromOneHorizontalLine(lookupMap,y_center,reduceRangeToLookUp,width);

        for (int x = 0; x < width;x++)
        {
            if (pixIsInRange(lookupYCenterLine[x] , pixelYCenterLine[x]))
                matchcount++;
            if (pixIsInRange(lookupYCenterLine[x] ,pixelYCenterLineMinus1[x]))
                matchcount1++;
            if (pixIsInRange(lookupYCenterLine[x] , pixelYCenterLinePlus1[x]))
                matchcount_1++;
            if (pixIsInRange(lookupYCenterLine[x] ,pixelYCenterLineMinus2[x]))
                matchcount2++;
            if (pixIsInRange(lookupYCenterLine[x] , pixelYCenterLinePlus2[x]))
                matchcount_2++;
        }

        int max = 0;
        if (matchcount > max)
            max = matchcount;
        if (matchcount1 > max)
            max = matchcount1;
        if (matchcount_1 > max)
            max = matchcount_1;
        if (matchcount2 > max)
            max = matchcount2;
        if (matchcount_2 > max)
            max = matchcount_2;
        return max;
    }

    private void saveHorizontalLine(int[] pixels, int img, int part)
    {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap map = Bitmap.createBitmap(pixels,pixels.length,1,config);
        saveBitmap(map, "/sdcard/Pictures/" + img + "_" +part + ".png");
    }

    private void logPixel(int[] match)
    {
        Log.d(TAG, match[0] + " " + match[1] + " " + match[2] + " " + match[3] + " " + match[4]);
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

        return ColorUtils.colorIsInRange(input,red-range, red+range,green-range,green+range,blue-range,blue+range);

    }

    public static Bitmap scale(Bitmap realImage,int newwidth, int newheight) {

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, newwidth,
                newheight, true);
        return newBitmap;
    }

    private List<Point> getArtifactPositionsFromTop()
    {
        boolean waitforstart = true;
        int start = 0;
        List<Point> list =new ArrayList<>();
        int[] colors = bot.getScreeCapture().getColorFromOneVerticalLine(colortofind_x_lookup,50,760);
        int t = 1;
        while(t < colors.length-1)
        {
            //if (waitforstart && isColorInRange(colors[t-1]) && !isColorInRange(colors[t]) && lastColorWasGray)
            if (waitforstart && !isPurple(colors[t-1])  && isPurple(colors[t]))
            {
                Log.d(TAG, "X "+ t +" Start: t-1:" + ColorUtils.logColor(colors[t-1]) + " t:" + ColorUtils.logColor(colors[t]));
                waitforstart = false;
                start = t;
            }
            //else if (!waitforstart && !lastColorWasGray && isColorInRange(colors[t]) && !isColorInRange(colors[t-1]))
            else if (!waitforstart&& isPurple(colors[t]) && !isPurple(colors[t+1]))
            {
                Log.d(TAG,  "X "+ t +" End: t1:" + ColorUtils.logColor(colors[t]) + " t+1:" + ColorUtils.logColor(colors[t+1]));
                waitforstart = true;
                list.add(new Point(start+51, t+51));
            }
            else if (!waitforstart && !isPurple(colors[t-1])  && isPurple(colors[t]) )
            {
                start = t;
            }
            t++;
        }
        return list;
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

    private boolean isPurple(int color)
    {
        boolean inrange = ColorUtils.colorIsInRange(color, 147,156,117,128,197,216);
        //Log.v(TAG, "isPurple: " + inrange + " " +ColorUtils.logColor(color));
        return inrange;
    }




}
