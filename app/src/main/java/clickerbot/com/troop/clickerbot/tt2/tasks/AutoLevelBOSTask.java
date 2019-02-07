package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.BOS;

public class AutoLevelBOSTask extends ExecuterTask {
    private BOS bos;
    public AutoLevelBOSTask(BOS bos)
    {
        this.bos = bos;
    }

    @Override
    public void doTask() {
        try {
            bos.levelBos(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
