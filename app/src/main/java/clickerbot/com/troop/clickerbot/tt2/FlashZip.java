package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Color;
import android.graphics.Point;

import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class FlashZip extends Menu {

    private final int flashZipColor;

    private final Point FLASH_ZIP_Areas[];

    public FlashZip(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
        flashZipColor = Color.argb(255,254,254,254);

        FLASH_ZIP_Areas = new Point[]
                {
                        new Point(353,175),
                        new Point(77,257),
                        new Point(70,229),
                        new Point(74,271),
                        new Point(92,301),
                        new Point(359,239),
                        new Point(412,251),
                        new Point(368,361),
                        new Point(60,176),
                        new Point(76,303),
                        new Point(82,198),
                        new Point(405,332),
                        new Point(362,377),
                        new Point(109,306),
                        new Point(126,332),
                        new Point(417,331),
                        new Point(415,353),
                        new Point(383,213),
                        new Point(379,216),
                        new Point(92,384),
                        new Point(80,283),
                        new Point(355,327),
                        new Point(98,245),
                        new Point(84,237),
                        new Point(345,203),
                        new Point(416,216),
                        new Point(115,261),
                        new Point(127,280),
                        new Point(422,284),
                        new Point(354,318),
                        new Point(87,348),
                        new Point(365,397),
                        new Point(395,315),
                        new Point(346,251),
                        new Point(100,183),
                        new Point(91,229),
                        new Point(373,296),
                        new Point(362,275),
                        new Point(129,246),
                        new Point(351,191),
                        new Point(397,394),
                        new Point(413,368),
                        new Point(71,201),
                        new Point(403,384),
                        new Point(131,174),
                        new Point(384,339),
                        new Point(147,340),
                        new Point(399,383),
                        new Point(406,165),
                        new Point(387,245),
                        new Point(375,318),
                        new Point(131,184),
                        new Point(377,232),
                        new Point(114,231),
                        new Point(127,268),
                        new Point(363,339),
                        new Point(411,265),
                        new Point(76,292),
                        new Point(371,246),
                        new Point(71,183),
                        new Point(112,185),
                        new Point(402,278),
                        new Point(367,176),
                        new Point(400,343),
                        new Point(399,362),
                        new Point(69,338),
                        new Point(107,321),
                        new Point(359,287),
                        new Point(398,414),
                        new Point(114,208),
                        new Point(110,250),
                        new Point(100,340),
                        new Point(406,186),
                        new Point(398,203),
                        new Point(107,284),
                        new Point(78,215),
                        new Point(345,366),
                        new Point(335,179),
                        new Point(239,157),//center
                };
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
        if (Menu.MenuOpen.get())
            return;
        for (int i =0; i< FLASH_ZIP_Areas.length; i++)
        {
            if (bot.getScreeCapture().getColor(FLASH_ZIP_Areas[i]) == flashZipColor && !Menu.MenuOpen.get())
                doSingelTap(FLASH_ZIP_Areas[i]);
        }
    }
}
