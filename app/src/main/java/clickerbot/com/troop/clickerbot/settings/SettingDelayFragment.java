package clickerbot.com.troop.clickerbot.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import clickerbot.com.troop.clickerbot.R;

public class SettingDelayFragment extends BaseSettingSubFragment {
    EditText mainlooperSleep;
    EditText captureFrameSleep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.settings_delays, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainlooperSleep = view.findViewById(R.id.editText_mainLooperSleep);
        mainlooperSleep.setText(getStringFromLongPref(R.string.mainLooperSleep,500));
        mainlooperSleep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setLongToPref(R.string.mainLooperSleep, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        captureFrameSleep = view.findViewById(R.id.editText_screenCaptureSleep);
        captureFrameSleep.setText(getStringFromLongPref(R.string.captureFrameSleep,800));
        captureFrameSleep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setLongToPref(R.string.captureFrameSleep, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
