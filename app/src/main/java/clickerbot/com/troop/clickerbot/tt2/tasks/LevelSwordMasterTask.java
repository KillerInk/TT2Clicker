package clickerbot.com.troop.clickerbot.tt2.tasks;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Skills;

public class LevelSwordMasterTask extends ExecuterTask {

    private final Skills skills;

    public LevelSwordMasterTask(Skills skils)
    {
        this.skills = skils;
    }

    @Override
    public void doTask() {
        skills.lvlSwordMaster();
    }
}
