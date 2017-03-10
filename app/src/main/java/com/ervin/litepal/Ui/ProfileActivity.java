package com.ervin.litepal.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ervin.litepal.R;
import com.ervin.litepal.databinding.ActivityProfileBinding;
import com.ervin.litepal.model.User;

/**
 * Created by Ervin on 2016/11/1.
 */

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        User user = new User(0,"Ervin zhang");
        binding.setUser(user);

    }
}
