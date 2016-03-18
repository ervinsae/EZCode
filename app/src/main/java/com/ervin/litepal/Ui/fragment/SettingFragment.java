package com.ervin.litepal.Ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ervin.litepal.R;

/**
 * Created by Ervin on 2016/3/18.
 */
public class SettingFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private static final String[] CONTENT = new String[] { "Recent", "Artists", "Albums"};
    private static int imageIcon[] = {R.mipmap.ic_action_filter,R.mipmap.ic_action_map,R.mipmap.ic_action_search};
    private static String text[] = {"过滤","地图","查找"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        GoogleMusicAdapter pagerAdapter =
                new GoogleMusicAdapter(getActivity().getSupportFragmentManager(),getActivity());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setCurrentItem(1);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("ervin",tab.getPosition() + "");
                viewPager.setCurrentItem(tab.getPosition());
                setSelectedTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setUnselectedTab(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public void setSelectedTab(TabLayout.Tab tab){
        View view = tab.getCustomView();
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setTextColor(getResources().getColor(R.color.app_primary_accent));
    }

    public void setUnselectedTab(TabLayout.Tab tab){
        View view = tab.getCustomView();
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setTextColor(getResources().getColor(R.color.all_track_color));
    }


    class GoogleMusicAdapter extends FragmentPagerAdapter {
        private Context context;
        public GoogleMusicAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.layout_tab_item, null);
            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(text[position]);
            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            img.setImageResource(imageIcon[position]);
            return v;
        }
    }
}
