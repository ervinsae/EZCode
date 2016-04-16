package com.ervin.litepal.ui

import android.os.Bundle
import com.ervin.litepal.R
import kotlinx.android.synthetic.main.actvity_kotlin.*

/**
 * Created by Ervin on 2016/4/13.
 */
class KotlinActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_kotlin);
        message.text = "Hello kotlin";
    }
}
