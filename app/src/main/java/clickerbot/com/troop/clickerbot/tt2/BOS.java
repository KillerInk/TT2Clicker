package clickerbot.com.troop.clickerbot.tt2;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.touch.TouchInterface;

public class BOS extends Menu {
    private final Point bos_click_pos = new Point(400,652);
    public BOS(TT2Bot ibot, BotSettings botSettings, TouchInterface rootShell) {
        super(ibot, botSettings, rootShell);
    }

    @Override
    void init(ExecuterTask task) {

    }

    @Override
    boolean checkIfRdyToExecute() {
        return false;
    }

    public void levelBos(ExecuterTask task) throws IOException, InterruptedException {
        openArtifactMenu();
        gotToTop(task);
        Thread.sleep(400);
        doLongerSingelTap(bos_click_pos);
        Thread.sleep(200);
        closeArtifactMenu();
    }
}
