package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;

import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class FlashZip extends Menu {

    private int flashZipColor;

    public FlashZip(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        flashZipColor = Color.argb(255,254,254,254);
    }

    @Override
    void init() {

    }

    @Override
    boolean checkIfRdyToExecute() {
        return false;
    }

    public void checkFlashZipAreasAndTap()
    {
        if (Menu.MenuOpen)
            return;
        for (int i =0; i< Coordinates.FLASH_ZIP_Areas.length; i++)
        {
            if (bot.getScreeCapture().getColor(Coordinates.FLASH_ZIP_Areas[i]) == flashZipColor && !Menu.MenuOpen)
                doSingelTap(Coordinates.FLASH_ZIP_Areas[i]);
        }
    }
}
