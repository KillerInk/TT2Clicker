package clickerbot.com.troop.clickerbot;

import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class MyEditTextPreference extends EditTextPreference {

    public MyEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditTextPreference(Context context) {
        super(context);
    }

    @Override
    public CharSequence getSummary() {
        return getText(); //super.getSummary();
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        setSummary(text);
    }
}
