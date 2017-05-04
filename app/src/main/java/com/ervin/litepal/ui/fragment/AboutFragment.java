package com.ervin.litepal.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ervin.litepal.R;
import com.ervin.litepal.ui.CategoryActivity;
import com.ervin.litepal.ui.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2016/3/18.
 */
public class AboutFragment extends Fragment {

    @Bind(R.id.fab)
    FloatingActionButton fab;

    float windowX;
    @Bind(R.id.icon)
    CircleImageView icon;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.rl_head)
    RelativeLayout header;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_about, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        //listView.setAdapter(new AboutAdapter());
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CategoryActivity.class);
            startActivity(intent);
        });

        windowX = getActivity().getWindowManager().getDefaultDisplay().getWidth();

        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            Log.d("AboutFragment", "scroll :" + verticalOffset);
            Log.d("AboutFragment", "header height :" + -header.getHeight());
            if (verticalOffset <= -header.getHeight()) {
                //mCollapsingToolbarLayout.setTitle("Ervin");
                tvTitle.setText("Ervin");
            } else {
                //mCollapsingToolbarLayout.setTitle("About");
                tvTitle.setText("About");
            }
        });

        pager.setAdapter(new TabPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(pager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class TabPagerAdapter extends FragmentStatePagerAdapter {

        String[] title = {"全国", "省市", "地区"};

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TabOneFragment();
                case 1:
                    return new TabTwoFragment();
                case 2:
                    return new TabThreeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
    /*class AboutAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 18;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.layout_about_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv.setTextColor(getResources().getColor(R.color.colorPrimary));

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(viewHolder.tv, "translationX", 0.0f, windowX - viewHolder.tv.getWidth(), 0.0f);
            objectAnimator.setInterpolator(new EasingInterpolator(Ease.BACK_IN_OUT));
            objectAnimator.setEvaluator(new FloatEvaluator());
            objectAnimator.setDuration(2500).setStartDelay(100 * position);
            objectAnimator.start();

            *//*ObjectAnimator objectAnimator1 = objectAnimator.ofInt(viewHolder.tv,"textSize",15,20);
            AnimatorSet set = new AnimatorSet();
            set.play(objectAnimator).with(objectAnimator1);
            set.start();*//*

            *//*ViewPropertyAnimator animator = viewHolder.tv.animate();
            animator.translationX(0).setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(2500).setStartDelay(100 * position).start();*//*

            return convertView;
        }

        class ViewHolder {
            TextView tv;

            ViewHolder(View view) {
                tv = (TextView) view.findViewById(R.id.about_tv);
            }
        }
    }*/
}
