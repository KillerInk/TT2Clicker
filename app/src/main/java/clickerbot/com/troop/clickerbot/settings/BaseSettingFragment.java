package clickerbot.com.troop.clickerbot.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import clickerbot.com.troop.clickerbot.R;

public class BaseSettingFragment extends Fragment {
    private int layoutID;

    public static BaseSettingFragment getFragment(int layoutID)
    {
        BaseSettingFragment bsf = new BaseSettingFragment();
        bsf.layoutID = layoutID;
        return bsf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(layoutID, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}