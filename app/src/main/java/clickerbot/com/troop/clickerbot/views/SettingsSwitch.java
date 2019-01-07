package clickerbot.com.troop.clickerbot.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.Switch;

import clickerbot.com.troop.clickerbot.R;

public class SettingsSwitch extends Switch implements  CompoundButton.OnCheckedChangeListener {
    private SharedPreferences preferences;
    private String settingsId;
    private boolean defaultVal;
    public SettingsSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SettingsSwitch,
                0, 0);
        try {
            settingsId = a.getString(R.styleable.SettingsSwitch_settingkey_ids);
            defaultVal = a.getBoolean(R.styleable.SettingsSwitch_setting_defaults, false);
        } finally {
            a.recycle();
        }
        init(context);
    }

    private void init(Context context)
    {
        this.setOnCheckedChangeListener(this::onCheckedChanged);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setChecked(preferences.getBoolean(settingsId,defaultVal));
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        preferences.edit().putBoolean(settingsId, isChecked).commit();
    }
}
