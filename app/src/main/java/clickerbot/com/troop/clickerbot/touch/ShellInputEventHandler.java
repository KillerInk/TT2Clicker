package clickerbot.com.troop.clickerbot.touch;

import android.graphics.Point;

import java.io.IOException;

import clickerbot.com.troop.clickerbot.RootShell;

public class ShellInputEventHandler implements TouchInterface {

    /**
     * Hold the root shells that get used to send commands
     */
    private RootShell rootShells[];
    private int lastShell;

    public ShellInputEventHandler(int shellcount)
    {
        rootShells = new RootShell[shellcount];
        for (int i = 0; i < rootShells.length; i++)
        {
            rootShells[i] = new RootShell(i);
        }
    }

    @Override
    public void tap(Point pos, int duration) throws InterruptedException {
        RootShell shell = getShell();
        shell.touchdownUp(pos);
    }

    @Override
    public void swipeVertical(Point from, Point to, boolean releaseTouch) throws InterruptedException, IOException {
        RootShell shell = getShell();
        shell.swipeFromTo(from.x,from.y,to.x,to.y);
    }

    @Override
    public void releaseTouch() {
    }

    @Override
    public void close() {
        for (int i = 0; i < rootShells.length; i++)
        {
            rootShells[i].Close();
        }
    }

    @Override
    public void start() {
        for (int i = 0; i < rootShells.length; i++)
        {
            rootShells[i].startProcess();
        }
    }

    @Override
    public void stop() {
        for (int i = 0; i < rootShells.length; i++)
        {
            rootShells[i].stopProcess();
        }
    }

    @Override
    public void backButton() {

    }

    private RootShell getShell()
    {
        RootShell shell = rootShells[lastShell++];
        if (lastShell == rootShells.length)
            lastShell = 0;
        return shell;
    }
}
