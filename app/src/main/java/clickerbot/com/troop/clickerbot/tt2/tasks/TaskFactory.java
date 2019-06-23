package clickerbot.com.troop.clickerbot.tt2.tasks;

import java.util.HashMap;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.ArtifactsColorExtractor;
import clickerbot.com.troop.clickerbot.tt2.BOS;
import clickerbot.com.troop.clickerbot.tt2.Boss;
import clickerbot.com.troop.clickerbot.tt2.ClanQuest;
import clickerbot.com.troop.clickerbot.tt2.CollectDailyReward;
import clickerbot.com.troop.clickerbot.tt2.CollectInBoxRewward;
import clickerbot.com.troop.clickerbot.tt2.Fairy;
import clickerbot.com.troop.clickerbot.tt2.Heros;
import clickerbot.com.troop.clickerbot.tt2.Prestige;
import clickerbot.com.troop.clickerbot.tt2.RandomTaps;
import clickerbot.com.troop.clickerbot.tt2.Skills;
import clickerbot.com.troop.clickerbot.tt2.TT2Bot;
import clickerbot.com.troop.clickerbot.tt2.tasks.test.ExtractArtifactsImageTask;

public class TaskFactory {

    public HashMap<Class, ExecuterTask> getTasksmap(TT2Bot bot,
                                                    Heros heros,
                                                    Skills skills,
                                                    Prestige prestige,
                                                    Fairy fairy,
                                                    Boss boss,
                                                    BOS bos,
                                                    RandomTaps randomTaps,
                                                    CollectDailyReward reward,
                                                    CollectInBoxRewward inBoxRewward,
                                                    ArtifactsColorExtractor artifactsColorExtractor,
                                                    ClanQuest clanQuest)
    {
        HashMap<Class, ExecuterTask> tasksmap = new HashMap<>();
        tasksmap.put(InitTask.class,new InitTask(bot));
        tasksmap.put(CrazyTapTask.class,new CrazyTapTask(randomTaps));
        tasksmap.put(LevelAllHerosTask.class,new LevelAllHerosTask(heros));
        tasksmap.put(LevelSwordMasterTask.class,new LevelSwordMasterTask(skills));
        tasksmap.put(LevelTopHerosTask.class,new LevelTopHerosTask(heros));
        tasksmap.put(PrestigeTask.class,new PrestigeTask(prestige));
        tasksmap.put(RandomTapTask.class,new RandomTapTask(randomTaps));
        tasksmap.put(TapOnFairysTask.class,new TapOnFairysTask(fairy));
        tasksmap.put(MenuCloseTask.class,new MenuCloseTask(heros));
        tasksmap.put(ClickOnBossFightTask.class,new ClickOnBossFightTask(boss));
        tasksmap.put(ClanQuestTask.class,new ClanQuestTask(clanQuest));
        tasksmap.put(AutoLevelBOSTask.class,new AutoLevelBOSTask(bos));
        tasksmap.put(CollectDailyTask.class, new CollectDailyTask(reward));
        tasksmap.put(CollectInboxRewardTask.class, new CollectInboxRewardTask(inBoxRewward));
        tasksmap.put(TapOnFairyVipWindowTask.class, new TapOnFairyVipWindowTask(fairy));
        tasksmap.put(ExtractArtifactsImageTask.class, new ExtractArtifactsImageTask(artifactsColorExtractor));
        return tasksmap;
    }

}
