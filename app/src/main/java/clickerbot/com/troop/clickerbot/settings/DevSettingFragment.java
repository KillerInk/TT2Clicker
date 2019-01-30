package clickerbot.com.troop.clickerbot.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import clickerbot.com.troop.clickerbot.R;
import clickerbot.com.troop.clickerbot.RootShell;

public class DevSettingFragment extends BaseSettingFragment {

    public static DevSettingFragment getFragment(int layoutID)
    {
        DevSettingFragment bsf = new DevSettingFragment();
        bsf.layoutID = layoutID;
        return bsf;
    }

    private SharedPreferences preferences;
    private TextView textView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        EditText inputPath = view.findViewById(R.id.editText_inputpath);
        inputPath.removeTextChangedListener(this.textWatcher);
        inputPath.setText(preferences.getString(getResources().getString(R.string.inputpath), "/dev/input/event6"));
        inputPath.addTextChangedListener(this.textWatcher);

        textView = view.findViewById(R.id.textView_log);


        new Thread(() -> {
            RootShell rootShell = new RootShell(0);
            rootShell.startProcess();
            rootShell.dumpInput();
            DataInputStream inputStream = new DataInputStream(rootShell.getInputStream());
            StringBuilder txt = new StringBuilder();
            try {
                String line = "";
                while ((line = inputStream.readLine()) != null) {
                    txt.append(line).append("\n");
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            rootShell.stopProcess();
            rootShell.Close();
            textView.post(() -> textView.setText(txt.toString()));
        }).start();




    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            preferences.edit().putString(getResources().getString(R.string.inputpath), s.toString()).commit();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
