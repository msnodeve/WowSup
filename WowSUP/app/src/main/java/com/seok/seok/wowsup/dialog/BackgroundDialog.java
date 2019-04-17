package com.seok.seok.wowsup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seok.seok.wowsup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BackgroundDialog extends Dialog {
    @BindView(R.id.dialog_background)
    ImageView imgBackground;

    private Context context;
    private String imgURL;
    public BackgroundDialog(Context context, String imgURL) {
        super(context);
        this.context = context;
        this.imgURL = imgURL;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_background);
        ButterKnife.bind(this);
        Glide.with(context.getApplicationContext()).load(imgURL).into(imgBackground);
    }

}
