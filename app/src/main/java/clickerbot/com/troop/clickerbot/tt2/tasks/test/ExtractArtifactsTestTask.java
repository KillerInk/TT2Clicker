package clickerbot.com.troop.clickerbot.tt2.tasks.test;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.executer.ExecuterTask;
import clickerbot.com.troop.clickerbot.tt2.ArtifactsColorExtractor;

public class ExtractArtifactsTestTask  extends ExecuterTask {
    private ArtifactsColorExtractor artifactsColorExtractor;

    public ExtractArtifactsTestTask(ArtifactsColorExtractor artifactsColorExtractor)
    {
        this.artifactsColorExtractor = artifactsColorExtractor;
    }

    @Override
    public void doTask() {
        try {
            artifactsColorExtractor.extractArtifactImages(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

