package clickerbot.com.troop.clickerbot.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import clickerbot.com.troop.clickerbot.R;

public class SettingPrestigeFragment extends BaseSettingSubFragment {

    Switch autoPrestige;
    EditText prestigeTime;
    EditText prestigeAfterBossFails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.settings_prestige, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        autoPrestige = view.findViewById(R.id.autoprestige);
        setPreferenceToSwitch(autoPrestige,getResources().getString(R.string.auto_prestige));
        autoPrestige.setOnCheckedChangeListener(this);

        prestigeTime = view.findViewById(R.id.editText_presstigeTime);
        prestigeTime.setText(getStringFromIntPref(R.string.prestigetime,60));
        prestigeTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setIntToPref(R.string.prestigetime, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        prestigeAfterBossFails = view.findViewById(R.id.editText_presstigeBossFail);
        prestigeAfterBossFails.setText(getStringFromIntPref(R.string.prestigeafterbossfail,3));
        prestigeAfterBossFails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setIntToPref(R.string.prestigeafterbossfail,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == autoPrestige)
            setSwitchToPreference(isChecked,getResources().getString(R.string.auto_prestige));
    }
}
