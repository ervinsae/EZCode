package com.ervin.litepal.Ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ervin.litepal.R;


/**
 * Created by Ervin on 2015/12/18.
 */
public class HomeFragments extends Fragment {

    private TextView tvTitle;
    public HomeFragments(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView  = inflater.inflate(R.layout.fragment_home,container,false);
        initView(rootView);
        initData();
        return rootView;
    }

    private void initData() {
    }

    private void initView(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.toolbar_center_title);
        tvTitle.setText(R.string.menu_home);

    }


}
