package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.util.HashMap;

import clickerbot.com.troop.clickerbot.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.Boss;
import clickerbot.com.troop.clickerbot.tt2.Fairy;
import clickerbot.com.troop.clickerbot.tt2.Heros;
import clickerbot.com.troop.clickerbot.tt2.Prestige;
import clickerbot.com.troop.clickerbot.tt2.Skills;
import clickerbot.com.troop.clickerbot.tt2.TT2Bot;

public class TaskFactory<T> {

    public HashMap<Class, ExecuterTask> getTasksmap(TT2Bot bot, Heros heros, Skills skills, Prestige prestige, Fairy fairy,Boss boss)
    {
        HashMap<Class, ExecuterTask> tasksmap = new HashMap<>();
        tasksmap.put(InitTask.class,new InitTask(bot));
        tasksmap.put(CrazyTapTask.class,new CrazyTapTask(bot));
        tasksmap.put(LevelAllHerosTask.class,new LevelAllHerosTask(heros));
        tasksmap.put(LevelSwordMasterTask.class,new LevelSwordMasterTask(skills));
        tasksmap.put(LevelTop6HerosTask.class,new LevelTop6HerosTask(heros));
        tasksmap.put(PrestigeTask.class,new PrestigeTask(prestige));
        tasksmap.put(RandomTapTask.class,new RandomTapTask(bot));
        tasksmap.put(TapOnFairysTask.class,new TapOnFairysTask(fairy));
        tasksmap.put(TapOnFairyVipWindowTask.class,new TapOnFairyVipWindowTask(fairy));
        tasksmap.put(MenuCloseTask.class,new MenuCloseTask(heros));
        tasksmap.put(ClickOnBossFightTask.class,new ClickOnBossFightTask(boss));
        return tasksmap;
    }

}
