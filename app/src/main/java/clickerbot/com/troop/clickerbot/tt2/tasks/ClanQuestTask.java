package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.ClanQuest;

public class ClanQuestTask extends ExecuterTask {

    private final ClanQuest clanquest;

    public ClanQuestTask(ClanQuest clanQuest)
    {
        this.clanquest = clanQuest;
    }

    @Override
    public void doTask() {
        try {
            clanquest.doCQ(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
