package clickerbot.com.troop.clickerbot.settings;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class BaseSettingFragment extends Fragment {
    protected int layoutID;

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