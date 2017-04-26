package com.ervin.litepal.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ervin.litepal.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabTwoFragment extends Fragment {

    public TabTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TabOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabTwoFragment newInstance() {
        TabTwoFragment fragment = new TabTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_one, container, false);
    }

}
