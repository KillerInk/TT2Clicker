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
import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class ArtifactsColorExtractor extends Menu {

    private final String TAG = ArtifactsColorExtractor.class.getSimpleName();

    private final int x_start = 9;
    private final int x_end = 66;

    private final int colortofind_x_lookup = 469;

    private Bitmap artifactImgs[];
    private Artifacts[] artifacts;
    private List<Artifacts> artifactsleveld;

    private final int click_X = 424;

    public ArtifactsColorExtractor(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        artifacts = Artifacts.values();
        artifactsleveld = new ArrayList<>();

        BitmapFactory.Options bitopt=new BitmapFactory.Options();
        bitopt.inScaled = false;
        Artifacts[] values = Artifacts.values();
        artifactImgs = new Bitmap[values.length-1];
        for (int i = 0; i < values.length-1;i++)
        {
            artifactImgs[i] = getArtifactImage(ibot, values[i].image_id, bitopt);
        }
    }

    private Bitmap getArtifactImage(TT2Bot ibot, int id, BitmapFactory.Options bitopt)
    {
        return BitmapFactory.decodeResource(ibot.getContext().getResources(), id,bitopt);
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
                Log.d(TAG,"WxH:" + width +"x"+height);
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
                    Log.d(TAG,artifactsProcessed + " Found " + artifact + " Size:" +artifactsleveld.size());
                    //saveBitmap(artifactImg, "/sdcard/Pictures/" + artifactsProcessed + "_" +artifact + ".png");

                    artifactsProcessed++;
                    if (artifact == botSettings.artifactsListToLvl.get(botSettings.artifactsListToLvl.size()-1)){
                        lastArtifactReached = true;
                        Log.d(TAG,"Last artifact reached");
                    }
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
                    Log.d(TAG,artifactsProcessed + " Found " + artifact +  " Size:" +artifactsleveld.size());
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
        double[] matches = new double[artifactImgs.length];
        for (int i =0; i< artifactImgs.length; i++)
        {
            matches[i] = match(input, artifactImgs[i]);
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
        if (bestmatch < 80)
            bestmatchpos = artifacts.length-1;
        return artifacts[bestmatchpos];
    }

    private double match(Bitmap input, Bitmap imagetomatch)
    {
        double max_match = 0;
        for (int i = -4; i < 5; i ++)
        {
            double match = match(input,imagetomatch,i);
            if (match > max_match)
                max_match = match;
        }
        return max_match;
    }

    private double match(Bitmap input, Bitmap imagetomatch, int shiftY)
    {
        int matchCount = 0;
        int pixinput;
        int pixmatch;
        for (int x = 7; x < input.getWidth() - 7;x++) {
            for (int y = 7; y < input.getHeight() - 7; y++) {
                if (y >= imagetomatch.getHeight())
                    return 0;
                pixinput = input.getPixel(x,y+shiftY);
                pixmatch = imagetomatch.getPixel(x,y);
                if (pixIsInRange(pixinput,pixmatch))
                    matchCount++;
            }
        }
        return matchCount;
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

    private boolean pixIsInRange(int input, int dif)
    {
        return ColorUtils.colorIsInRange(input,dif,3);
        //return ColorUtils.colorIsInRange(input,red-range, red+range,green-range,green+range,blue-range,blue+range);

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
                //Log.d(TAG, "X "+ t +" Start: t-1:" + ColorUtils.logColor(colors[t-1]) + " t:" + ColorUtils.logColor(colors[t]));
                waitforstart = false;
                start = t;
            }
            //else if (!waitforstart && !lastColorWasGray && isColorInRange(colors[t]) && !isColorInRange(colors[t-1]))
            else if (!waitforstart&& isPurple(colors[t]) && !isPurple(colors[t+1]))
            {
                //Log.d(TAG,  "X "+ t +" End: t1:" + ColorUtils.logColor(colors[t]) + " t+1:" + ColorUtils.logColor(colors[t+1]));
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

    private final int purple = Color.argb(255,130,130, 160);
    private boolean isPurple(int color)
    {
        boolean inrange = ColorUtils.colorIsInRange(color,purple,40);//ColorUtils.colorIsInRange(color, 108,168,92,168,117,207);
        //Log.v(TAG, "isPurple: " + inrange + " " +ColorUtils.logColor(color));
        return inrange;
    }




}
