package clickerbot.com.troop.clickerbot;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsFragment extends Fragment {

    private final String TAG = SettingsFragment.class.getSimpleName();

    Switch autoLevelHeros;
    Switch autoLevelSkills;
    Switch autoTap;
    Switch useHs;
    Switch useDs;
    Switch useHom;
    Switch useFs;
    Switch useWc;
    Switch useSc;
    Switch autoPrestige;
    Switch clickOnFairys;
    Switch acceptFairyAdds;
    Switch autoLvlSwordMaster;

    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        autoLevelHeros = view.findViewById(R.id.autolevelheros);
        autoLevelSkills = view.findViewById(R.id.autolevelskills);
        autoTap = view.findViewById(R.id.autotap);
        useHs = view.findViewById(R.id.usehs);
        useDs = view.findViewById(R.id.useds);
        useHom = view.findViewById(R.id.usehom);
        useFs = view.findViewById(R.id.usefs);
        useWc = view.findViewById(R.id.usewc);
        useSc = view.findViewById(R.id.usesc);
        autoPrestige = view.findViewById(R.id.autoprestige);
        clickOnFairys = view.findViewById(R.id.clickOnFairys);
        acceptFairyAdds = view.findViewById(R.id.clickOnAcceptFairyAdds);
        autoLvlSwordMaster = view.findViewById(R.id.autoLvlSwordMaster);

        setPreferenceToSwitch(autoLevelHeros,getResources().getString(R.string.autolvlheros));
        setPreferenceToSwitch(autoLevelSkills,getResources().getString(R.string.autolvlskills));
        setPreferenceToSwitch(autoTap,getResources().getString(R.string.autoTap));
        setPreferenceToSwitch(useHs,getResources().getString(R.string.useHs));
        setPreferenceToSwitch(useDs,getResources().getString(R.string.useDS));
        setPreferenceToSwitch(useHom,getResources().getString(R.string.useHom));
        setPreferenceToSwitch(useFs,getResources().getString(R.string.useFS));
        setPreferenceToSwitch(useWc,getResources().getString(R.string.useWC));
        setPreferenceToSwitch(useSc,getResources().getString(R.string.useSC));
        setPreferenceToSwitch(autoPrestige,getResources().getString(R.string.auto_prestige));
        setPreferenceToSwitch(clickOnFairys,getResources().getString(R.string.clickonfairyadds));
        setPreferenceToSwitch(acceptFairyAdds,getResources().getString(R.string.acceptfairyadds));
        setPreferenceToSwitch(autoLvlSwordMaster,getResources().getString(R.string.autolvlswordmaster));

        autoLevelHeros.setOnCheckedChangeListener(checkedChangeListener);
        autoLevelSkills.setOnCheckedChangeListener(checkedChangeListener);
        autoTap.setOnCheckedChangeListener(checkedChangeListener);
        useHs.setOnCheckedChangeListener(checkedChangeListener);
        useDs.setOnCheckedChangeListener(checkedChangeListener);
        useHom.setOnCheckedChangeListener(checkedChangeListener);
        useFs.setOnCheckedChangeListener(checkedChangeListener);
        useWc.setOnCheckedChangeListener(checkedChangeListener);
        useSc.setOnCheckedChangeListener(checkedChangeListener);
        autoPrestige.setOnCheckedChangeListener(checkedChangeListener);
        clickOnFairys.setOnCheckedChangeListener(checkedChangeListener);
        acceptFairyAdds.setOnCheckedChangeListener(checkedChangeListener);
        autoLvlSwordMaster.setOnCheckedChangeListener(checkedChangeListener);

    }

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.d(TAG, "onCheckedChanged:" + isChecked);
            if (buttonView == autoLevelHeros)
                setSwitchToPreference(isChecked,getResources().getString(R.string.autolvlheros));
            else if (buttonView == autoLevelSkills)
                setSwitchToPreference(isChecked,getResources().getString(R.string.autolvlskills));
            else if (buttonView == autoTap)
                setSwitchToPreference(isChecked,getResources().getString(R.string.autoTap));
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
            else if (buttonView == autoPrestige)
                setSwitchToPreference(isChecked,getResources().getString(R.string.auto_prestige));
            else if (buttonView == clickOnFairys)
                setSwitchToPreference(isChecked,getResources().getString(R.string.clickonfairyadds));
            else if (buttonView == acceptFairyAdds)
                setSwitchToPreference(isChecked,getResources().getString(R.string.acceptfairyadds));
            else if (buttonView == autoLvlSwordMaster)
                setSwitchToPreference(isChecked,getResources().getString(R.string.autolvlswordmaster));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setPreferenceToSwitch(Switch s, String pref)
    {
        s.setChecked(preferences.getBoolean(pref,false));
    }

    private void setSwitchToPreference(boolean b, String pref)
    {
        preferences.edit().putBoolean(pref,b).commit();
    }
}
