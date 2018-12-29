package clickerbot.com.troop.clickerbot;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OCR
{
    private final String TAG = OCR.class.getSimpleName();
    private final TessBaseAPI ocr;

    public OCR(Context context, String language) {
        ocr = new TessBaseAPI();
        File lang = new File(context.getFilesDir() + "/tessdata/eng.traineddata");
        if (!lang.exists())
        {
            lang.getParentFile().mkdirs();
            try {
                InputStream input = context.getAssets().open("tessdata/eng.traineddata");
                OutputStream out = new FileOutputStream(lang);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;

                while ((len = input.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                input.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "path to load " +context.getFilesDir() + "/tessdata/");
        String datapath = context.getFilesDir().getAbsolutePath();
        ocr.init(datapath, language);
    }

    public String getOCRResult(Bitmap bitmap) {
        Log.d(TAG,"getOCRResult");
        ocr.setImage(bitmap);
        return ocr.getUTF8Text();
    }

    public void destroy() {
        Log.d(TAG,"destroy");
        if (ocr != null) ocr.end();
    }
}
