package clickerbot.com.troop.clickerbot.bmptxtparser;

import java.util.List;

public class Patch{
    private List<boolean[]> patch;
    String ret;
    private int[] failslist;

    public Patch(List<boolean[]> patch, String ret)
    {
        this.patch = patch;
        this.ret = ret;
        failslist = new int[patch.size()];
    }

    public boolean[] getFirstPatch()
    {
        return  patch.get(0);
    }

    public void reset()
    {
        for (int i = 0; i< failslist.length;i++)
            failslist[i] = 0;
    }

    public boolean doMatch()
    {
        boolean found = false;
        for (int i = 0; i< failslist.length;i++)
        {
            if (failslist[i] < 3)
                found = true;
        }
        return found;
    }

    public String getRet()
    {
        return ret;
    }

    public void checkIfFailed(boolean[] bitmap, int i)
    {
        for (int t =0; t < patch.size(); t++) {
            if (patch.get(t)[i] && bitmap[i] != patch.get(t)[i])
                failslist[t]++;
        }
    }
}
