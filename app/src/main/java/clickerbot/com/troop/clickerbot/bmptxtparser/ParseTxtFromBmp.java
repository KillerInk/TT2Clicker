package clickerbot.com.troop.clickerbot.bmptxtparser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import clickerbot.com.troop.clickerbot.R;
import clickerbot.com.troop.clickerbot.XmlElement;
import clickerbot.com.troop.clickerbot.tt2.SkillLevelParser;

public class ParseTxtFromBmp {

    private final String TAG = ParseTxtFromBmp.class.getSimpleName();
    private boolean[] pixels;
    private Patch[] patches;
    private int width;
    private int height;
    private Context context;

    public ParseTxtFromBmp(Context context, int width, int height)
    {
        this.width = width;
        this.height =height;
        this.context = context;
        pixels = new boolean[width/2 * height];
        try {
            loadPatches();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIntTxtFromBmp(Bitmap map, int x, int y, int width, int height)
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
                    pixels[i++] = false;
                else
                    pixels[i++] = true;
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


    private String getChar(boolean b)
    {
        if (b)
            return "#";
        return "-";
    }

    private void printarrs(boolean[] bitmap)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" \ninput\n");
        for (int y = 0; y < 11; y++) {
            for (int x = 0; x < 8; x++) {
                stringBuilder.append(getChar(bitmap[(width / 2) * y + x]) + ",");
            }
            stringBuilder.append("\n");
        }
        Log.d(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" \npatch0                patch1                patch2                patch3                patch4                "+
                "patch5                patch6                patch7                patch8                patch9\n");
        for (int y = 0; y < 11; y++)
        {
            for (int x = 0; x < 8; x++)
            {
                stringBuilder.append(getChar(bitmap[(width/2)*y+x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[0].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[1].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[2].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[3].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[4].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[5].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[6].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[7].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[8].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("      ");
            for (int x = 0; x < width/2; x++)
            {
                stringBuilder.append(getChar(patches[9].getFirstPatch()[(width/2)*y + x]) + ",");
            }
            stringBuilder.append("\n");
        }
        Log.d(TAG, stringBuilder.toString());
    }



    private void loadPatches() throws IOException {
        patches = new Patch[10];

        String xmlsource = getString(context.getResources().openRawResource(R.raw.patches));
        XmlElement rootElement = XmlElement.parse(xmlsource);
        if (rootElement.getTagName().equals("patches"))
        {
            patches[0] = new Patch(getPatch("patch0", rootElement),"0");
            patches[4] = new Patch(getPatch("patch1", rootElement),"1");
            patches[2] = new Patch(getPatch("patch2", rootElement),"2");
            patches[8] = new Patch(getPatch("patch3", rootElement),"3");
            patches[1] = new Patch(getPatch("patch4", rootElement),"4");
            patches[5] = new Patch(getPatch("patch5", rootElement),"5");
            patches[6] = new Patch(getPatch("patch6", rootElement),"6");
            patches[7] = new Patch(getPatch("patch7", rootElement),"7");
            patches[3] = new Patch(getPatch("patch8", rootElement),"8");
            patches[9] = new Patch(getPatch("patch9", rootElement),"9");
        }
    }

    private List<boolean[]> getPatch(String patchname, XmlElement rootElement)
    {
        XmlElement patch = rootElement.findChild(patchname);
        List<XmlElement> patchs = patch.findChildren("item");
        List<boolean[]> ret = new ArrayList<>();
        for (XmlElement p: patchs)
        {
            String s[] = p.getValue().split(",");
            boolean[] b = new boolean[s.length];
            for (int i=0; i< s.length; i++)
            {
                b[i] = s[i].equals("#");
            }
            ret.add(b);
        }
        return ret;
    }

    private String getString(InputStream inputStream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while(result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }
        return buf.toString();
    }
}
