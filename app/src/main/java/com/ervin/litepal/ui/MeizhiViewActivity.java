package com.ervin.litepal.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.ervin.litepal.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2016/4/14.
 */
public class MeizhiViewActivity extends BaseActivity {

    @Bind(R.id.iv_meizhi_picture)
    ImageView mPicture;

    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizhi_view);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("url");
        Picasso.with(this).load(url).into(mPicture);
    }
}
