package clickerbot.com.troop.clickerbot.tt2.tasks.test;

import clickerbot.com.troop.clickerbot.tt2.Heros;
import clickerbot.com.troop.clickerbot.tt2.Skills;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelAllHerosTask;
import clickerbot.com.troop.clickerbot.tt2.tasks.LevelSwordMasterTask;

public class ParseSkilllvlTest extends LevelSwordMasterTask {

    public ParseSkilllvlTest(Skills skils) {
        super(skils);
    }

    @Override
    public void doTask() {
        skills.testSkillLvlDetection(this);
    }
}
