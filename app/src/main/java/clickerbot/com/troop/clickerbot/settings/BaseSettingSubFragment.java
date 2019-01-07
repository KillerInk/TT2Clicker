package clickerbot.com.troop.clickerbot.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class BaseSettingSubFragment extends Fragment implements  CompoundButton.OnCheckedChangeListener {

    protected SharedPreferences preferences;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
    }

    protected void setIntToPref(int id, String in)
    {
        try {
            preferences.edit().putInt(getResources().getString(id),Integer.parseInt(in)).commit();
        }catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }
    }

    protected String getStringFromIntPref(int id, int defaultValue)
    {
        return preferences.getInt(getResources().getString(id), defaultValue) +"";
    }

    protected void setLongToPref(int id, String in)
    {
        try {
            preferences.edit().putLong(getResources().getString(id),Long.parseLong(in)).commit();
        }catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }
    }

    protected String getStringFromLongPref(int id, long defaultValue)
    {
        return preferences.getLong(getResources().getString(id), defaultValue) +"";
    }

    protected void setPreferenceToSwitch(Switch s, String pref)
    {
        s.setChecked(preferences.getBoolean(pref,false));
    }

    protected void setSwitchToPreference(boolean b, String pref)
    {
        preferences.edit().putBoolean(pref,b).commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
