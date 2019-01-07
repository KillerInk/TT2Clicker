package clickerbot.com.troop.clickerbot.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import clickerbot.com.troop.clickerbot.R;

public class SettingSkillFragment extends BaseSettingSubFragment
{

    Switch autoLevelSkills;
    Switch useHs;
    Switch useDs;
    Switch useHom;
    Switch useFs;
    Switch useWc;
    Switch useSc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.settings_skills, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        autoLevelSkills = view.findViewById(R.id.autolevelskills);
        useHs = view.findViewById(R.id.usehs);
        useDs = view.findViewById(R.id.useds);
        useHom = view.findViewById(R.id.usehom);
        useFs = view.findViewById(R.id.usefs);
        useWc = view.findViewById(R.id.usewc);
        useSc = view.findViewById(R.id.usesc);

        if (autoLevelSkills != null) {
            setPreferenceToSwitch(autoLevelSkills, getResources().getString(R.string.autolvlskills));
            setPreferenceToSwitch(useHs, getResources().getString(R.string.useHs));
            setPreferenceToSwitch(useDs, getResources().getString(R.string.useDS));
            setPreferenceToSwitch(useHom, getResources().getString(R.string.useHom));
            setPreferenceToSwitch(useFs, getResources().getString(R.string.useFS));
            setPreferenceToSwitch(useWc, getResources().getString(R.string.useWC));
            setPreferenceToSwitch(useSc, getResources().getString(R.string.useSC));

            autoLevelSkills.setOnCheckedChangeListener(this);
            useHs.setOnCheckedChangeListener(this);
            useDs.setOnCheckedChangeListener(this);
            useHom.setOnCheckedChangeListener(this);
            useFs.setOnCheckedChangeListener(this);
            useWc.setOnCheckedChangeListener(this);
            useSc.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == autoLevelSkills)
            setSwitchToPreference(isChecked,getResources().getString(R.string.autolvlskills));
        else if (buttonView == useHs)
            setSwitchToPreference(isChecked,getResources().getString(R.string.useHs));
        else if (buttonView == useDs)
            setSwitchToPreference(isChecked,getResources().getString(R.string.useDS));
        else if (buttonView == useHom)
            setSwitchToPreference(isChecked,getResources().getString(R.string.useHom));
        else if (buttonView == useFs)
            setSwitchToPreference(isChecked,getResources().getString(R.string.useFS));
        else if (buttonView == useWc)
            setSwitchToPreference(isChecked,getResources().getString(R.string.useWC));
        else if (buttonView == useSc)
            setSwitchToPreference(isChecked,getResources().getString(R.string.useSC));
    }
}
