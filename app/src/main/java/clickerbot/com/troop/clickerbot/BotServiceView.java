package clickerbot.com.troop.clickerbot;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class BotServiceView extends View {
    private Button closeButton;

    public BotServiceView(Context context) {
        super(context);
    }

    public BotServiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BotServiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init()
    {
        //inflate(getContext())
    }
}
