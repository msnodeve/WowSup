package com.seok.seok.wowsup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.WebViewActivity;
import com.seok.seok.wowsup.utilities.GlobalWowSup;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeveloperDialog extends Dialog {
    private static int LAYOUT;
    private Context context;
    private ImageView iBtnWeb;
    private LinearLayout layout;

    public DeveloperDialog(Context context) {
        super(context);
        LAYOUT = R.layout.dialog_developer_info;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        init();
        iBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context.getApplicationContext(), WebViewActivity.class));
            }
        });
    }

    public void init() {
        layout = findViewById(R.id.dialog_layout_devel);
        iBtnWeb = findViewById(R.id.devel_ibtn_web);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(layout.getLayoutParams());
        layoutParams.width = (int)(GlobalWowSup.getInstance().getUserWidth() / 1.3);
        layoutParams.height = (int)(GlobalWowSup.getInstance().getUserHeight() / 1.7);
        layout.setLayoutParams(layoutParams);

    }
}
