package clickerbot.com.troop.clickerbot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BotServiceView extends LinearLayout {
    private Button closeButton;
    private ClickerBotService clickerBotService;
    private TextView textView;
    private ImageView surfaceView;


    public void setClickerBotService(ClickerBotService clickerBotService)
    {
        this.clickerBotService = clickerBotService;
    }

    public BotServiceView(Context context) {
        super(context);
        init(context);
    }

    public BotServiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BotServiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.service_view_layout, this);
        closeButton = findViewById(R.id._button_stop);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(clickerBotService, ClickerBotActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                clickerBotService.stopSelf();
            }
        });
        surfaceView = findViewById(R.id.dump_screen_view);
        textView = findViewById(R.id.logview);

    }

    public void setTextViewText(String txt)
    {
        textView.setText(txt);
        textView.invalidate();
    }

    public void setBitmap(Bitmap bitmap)
    {
        surfaceView.setImageBitmap(bitmap);
        //this.imageView.setImageBitmap(bitmap);
    }

    public TextureView getSurfaceView()
    {
        return null;
    }
}
