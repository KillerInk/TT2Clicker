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

public class SettingOtherFragment extends BaseSettingSubFragment {

    Switch autoLevelHeros;
    Switch autoTap;
    Switch clickOnFairys;
    Switch acceptFairyAdds;
    Switch autoLvlSwordMaster;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.settings_other, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        autoLevelHeros = view.findViewById(R.id.autolevelheros);

        autoTap = view.findViewById(R.id.autotap);


        clickOnFairys = view.findViewById(R.id.clickOnFairys);
        acceptFairyAdds = view.findViewById(R.id.clickOnAcceptFairyAdds);
        autoLvlSwordMaster = view.findViewById(R.id.autoLvlSwordMaster);

        if (autoLevelHeros != null)
            setPreferenceToSwitch(autoLevelHeros,getResources().getString(R.string.autolvlheros));

        if (autoTap != null)
            setPreferenceToSwitch(autoTap,getResources().getString(R.string.autoTap));

        if (clickOnFairys != null)
            setPreferenceToSwitch(clickOnFairys,getResources().getString(R.string.clickonfairyadds));
        if (acceptFairyAdds != null)
            setPreferenceToSwitch(acceptFairyAdds,getResources().getString(R.string.acceptfairyadds));
        if (autoLevelHeros != null)
            setPreferenceToSwitch(autoLvlSwordMaster,getResources().getString(R.string.autolvlswordmaster));

        if (autoLevelHeros != null) {
            autoLevelHeros.setOnCheckedChangeListener(this);
            autoTap.setOnCheckedChangeListener(this);
            clickOnFairys.setOnCheckedChangeListener(this);
            acceptFairyAdds.setOnCheckedChangeListener(this);
            autoLvlSwordMaster.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == autoLevelHeros)
            setSwitchToPreference(isChecked,getResources().getString(R.string.autolvlheros));

        else if (buttonView == autoTap)
            setSwitchToPreference(isChecked,getResources().getString(R.string.autoTap));

        else if (buttonView == clickOnFairys)
            setSwitchToPreference(isChecked,getResources().getString(R.string.clickonfairyadds));
        else if (buttonView == acceptFairyAdds)
            setSwitchToPreference(isChecked,getResources().getString(R.string.acceptfairyadds));
        else if (buttonView == autoLvlSwordMaster)
            setSwitchToPreference(isChecked,getResources().getString(R.string.autolvlswordmaster));
    }
}
