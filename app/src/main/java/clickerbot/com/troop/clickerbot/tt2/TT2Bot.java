package clickerbot.com.troop.clickerbot.tt2;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.OCR;
import clickerbot.com.troop.clickerbot.RootShell;

public class TT2Bot extends AbstractBot
{

    private static final String TAG = TT2Bot.class.getSimpleName();
    private final int CLICK_DELAY = 2;//ms


    private final static int ROOTSHELLS_CLICKERCOUNT = 4;

    private RootShell rootShellClick[] = new RootShell[ROOTSHELLS_CLICKERCOUNT];
    private Skills skills;
    private OCR ocr;

    private final int runSkillsActivator = 30000;//ms
    private long lastSkillsActivated = 0;


    public TT2Bot(Context context)
    {
        skills = new Skills();
        ocr = new OCR(context,"eng");
        Log.d(TAG,"TT2Bot()");
    }

    public void destroy()
    {
        Log.d(TAG,"destroy");
        ocr.destroy();
    }

    @Override
    public OCR getOcr() {
        return ocr;
    }


    public void start()
    {
        Log.d(TAG,"start");
        skills.setIBot(this);

        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            rootShellClick[i] = new RootShell(i,80);
        }
        skills.setRootShellClick(rootShellClick);
        super.start();


    }

    public void stop()
    {
        Log.d(TAG,"stop");
        super.stop();
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            rootShellClick[i].Close();
        }

    }

    @Override
    void onTick(long tickCounter) {
        if (tickCounter == 1) {
            init();
        }
        else {
            if (System.currentTimeMillis() - lastSkillsActivated > runSkillsActivator) {
                lastSkillsActivated = System.currentTimeMillis();
                try {
                    Log.d(TAG,"activateSkills");
                    skills.activateAllSkills();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        //doTap();
    }

    private int randomTapCount =0;



    private void init()
    {
        try {
            Log.d(TAG,"init");
            skills.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void doTap()
    {
        for (int i = 0; i < ROOTSHELLS_CLICKERCOUNT; i++)
        {
            try {
                Point p = Coordinates.AAW_Areas[randomTapCount++];
                Log.d(TAG,"doTap x:" +p.x + " y:"+p.y);
                rootShellClick[i].doTap(p);
                if (randomTapCount == Coordinates.AAW_Areas.length)
                    randomTapCount = 0;
                Thread.sleep(CLICK_DELAY);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }








}
