package clickerbot.com.troop.clickerbot.screencapture;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import clickerbot.com.troop.clickerbot.tt2.BotSettings;

public class ScreenCapture {

    private final String TAG = ScreenCapture.class.getSimpleName();
    private AbstractScreenCapture screenCapture;
    private Bitmap inputbmp;

    public ScreenCapture(AbstractScreenCapture screenCapture)
    {
        this.screenCapture = screenCapture;
        inputbmp = Bitmap.createBitmap(480,800,Bitmap.Config.ARGB_8888);
    }

    public void stop()
    {
        screenCapture.stop();
    }

    public void close()
    {
        screenCapture.close();
    }

    public Bitmap getBitmap()
    {
        inputbmp = screenCapture.getBitmapFromQueue(inputbmp);
        if (inputbmp != null)
            Log.v(TAG,"getBitmap w:" + inputbmp.getWidth() +" h:" + inputbmp.getHeight());
        return inputbmp;
    }

    public int getColor(Point p)
    {
        int color =0;
        inputbmp = screenCapture.getBitmapFromQueue(inputbmp);
        if (inputbmp != null && inputbmp.getWidth() > 0 && inputbmp.getHeight() > 0)
            color = inputbmp.getPixel(p.x, p.y);
        //if this case happen something went wrong while copy the buffer to the bitmap
        //or it got never filled
        if (color == 0)
        {
            Log.d(TAG ,"Requested COLOR = 0 wait()");
            //color = inputbmp.getPixel(p.x, p.y);
        }

        return color;
    }

    public Bitmap getBitmapFromPos(int x, int y, int width, int height) {
        Bitmap retBit = null;
        inputbmp = screenCapture.getBitmapFromQueue(inputbmp);
        if (inputbmp != null && inputbmp.getWidth() > 0 && inputbmp.getHeight() > 0) {
            retBit = Bitmap.createBitmap(inputbmp,x,y,width,height);
        }
        return retBit;
    }


    public int[] getColorFromOneHorizontalLine(Bitmap map,int y, int start_x, int end_x)
    {
        int[] arr = null;
        if (map != null && map.getWidth() > 0 && map.getHeight() > 0){
            arr = new int[(end_x - start_x)];
            int i = 0;
            for (int x = start_x; x < end_x; x++)
            {
                arr[i++] = map.getPixel(x, y);
            }
        }
        return arr;
    }

    public int[] getColorFromOneVerticalLine(int x, int start_y, int end_y)
    {
        int[] arr = null;
        inputbmp = screenCapture.getBitmapFromQueue(inputbmp);
        if (inputbmp != null && inputbmp.getWidth() > 0 && inputbmp.getHeight() > 0){
            arr = new int[(end_y - start_y)];
            int i = 0;
            for (int y = start_y; y < end_y; y++)
            {
                arr[i++] = inputbmp.getPixel(x, y);
            }
        }
        return arr;
    }
}
