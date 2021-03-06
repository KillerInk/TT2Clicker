package clickerbot.com.troop.clickerbot.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import clickerbot.com.troop.clickerbot.R;

public class LongEditText extends LinearLayout{
    private final String TAG = LongEditText.class.getSimpleName();

    private SharedPreferences preferences;
    private String idString;
    private EditText editText;

    public LongEditText(Context context) {
        super(context);
        Log.d(TAG, "LongEditText(Context context)");
    }

    public LongEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttrs(context, attrs);
        Log.d(TAG, "LongEditText(Context context, AttributeSet attrs)");
    }

    private void processAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.long_edit_text, this);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LongEditText,
                0, 0);
        String mShowText;
        int defaultval;
        try {
            mShowText = a.getString(R.styleable.LongEditText_header_txt);
            idString = a.getString(R.styleable.LongEditText_settingkey_id);
            defaultval = a.getInteger(R.styleable.LongEditText_setting_default, 0);
        } finally {
            a.recycle();
        }
        init(context,mShowText,defaultval);
    }

    public LongEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        processAttrs(context, attrs);
        Log.d(TAG, "LongEditText(Context context, AttributeSet attrs, int defStyleAttr)");
    }



    private void init(Context context,String header, int defaultVal)
    {

        TextView textView = findViewById(R.id.textView_long_edit_text);
        textView.setText(header);

        editText = findViewById(R.id.editText_long_edit_text);
        editText.removeTextChangedListener(this.textWatcher);
        editText.setText(getStringFromLongPref(idString,defaultVal));
        Log.d(idString, "init " +editText.getText().toString());
        editText.addTextChangedListener(this.textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(idString, "beforeTextChanged " +s.toString());
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(idString, "onTextChanged " +s.toString());
            setLongToPref(idString, s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(idString, "afterTextChanged " +s.toString());
        }
    };

    protected void setLongToPref(String id, String in)
    {
        try {
            preferences.edit().putLong(id,Long.parseLong(in)).commit();
        }catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }
    }

    protected String getStringFromLongPref(String id, long defaultValue)
    {
        return preferences.getLong(id, defaultValue) +"";
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        editText.removeTextChangedListener(this.textWatcher);
    }
}
