package com.ervin.litepal.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ervin.litepal.R;
import com.ervin.litepal.ui.fragment.AboutFragment;
import com.ervin.litepal.ui.fragment.FragmentCallback;
import com.ervin.litepal.ui.fragment.HomeFragment;
import com.ervin.litepal.ui.fragment.MeizhiFragment;
import com.ervin.litepal.ui.fragment.ModeFragment;
import com.ervin.litepal.ui.fragment.RetrofitFragment;
import com.ervin.litepal.ui.fragment.SettingFragment;
import com.ervin.litepal.ui.widget.CircleImageView;

/**
 * Created by Ervin on 2015/12/19.
 *
 * 3.0
 * Use android.app.Fragment with Activity. Use android.support.v4.app.Fragment with FragmentActivity.
 * Don't add the support package Fragment to an Activity as it will cause an Exception to be thrown.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener,ListView.OnItemClickListener,FragmentCallback{

    private DrawerLayout drawerLayout;
    private View drawer;
    private ListView menuList;

    private FragmentManager fragmentManager;
    private Fragment mContent;
    private HomeFragment homeFragment;
    private ModeFragment modeFragment;
    private AboutFragment aboutFragment;
    private SettingFragment settingFragment;
    private RetrofitFragment retrofitFragment;
    private MeizhiFragment meizhiFragment;

    private CircleImageView ivAavatar;

    private int imageIcon[] = {R.mipmap.ic_navview_explore,R.mipmap.ic_navview_map,R.mipmap.ic_navview_my_schedule,
    R.mipmap.ic_navview_play_circle_fill,R.mipmap.ic_navview_social,R.mipmap.ic_navview_settings,R.mipmap.ic_navview_video_library};

    private int imageStr[] = {R.string.menu_home,R.string.menu_Mode,R.string.menu_retrofit,
    R.string.menu_profile,R.string.menu_setting,R.string.menu_about,R.string.menu_meizhi};

    public HomeActivity getActivity(){
        return this;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);

        initView();
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        modeFragment = new ModeFragment();
        aboutFragment = new AboutFragment();
        settingFragment = new SettingFragment();
        retrofitFragment = new RetrofitFragment();
        meizhiFragment = new MeizhiFragment();

        mContent = homeFragment; // 默认Fragment
        fragmentManager.beginTransaction().replace(R.id.content_main, mContent).commit();
        menuList.setAdapter(new MenuAdapter());
    }

    private void initView() {
        ivAavatar = (CircleImageView) findViewById(R.id.iv_avatar);
        drawer =  findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        menuList = (ListView) findViewById(R.id.menu_list);
        menuList.setOnItemClickListener(this);

        ivAavatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_drawer:
                menuSetting();
                break;
            case R.id.iv_avatar:
                Intent intent = new Intent(this,ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void menuSetting() {
        if(drawerLayout.isDrawerOpen(drawer)){
            drawerLayout.closeDrawers();
        }else{
            drawerLayout.openDrawer(drawer);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                switchFragment(position,homeFragment);
                break;
            case 1:
                switchFragment(position,modeFragment);
                break;
            case 2:
                //Login();
                switchFragment(position,retrofitFragment);
                break;
            case 3:
               /* FrameLayout ff = (FrameLayout) getActivity().findViewById(R.id.content_main);
                LinearLayout ll =*/
                Intent intent = new Intent(HomeActivity.this,CategoryActivity.class);
                startActivity(intent);
                break;
            case 4:
                /*Intent intent2  = new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(intent2);*/
                switchFragment(position,settingFragment);
                break;
            case 5:
                /*Intent intent3 = new Intent(HomeActivity.this,AboutActivity.class);
                startActivity(intent3);*/
                switchFragment(position,aboutFragment);
                break;
            case 6:
                switchFragment(position,meizhiFragment);
                break;
        }
    }

    @Override
    public void callbackMenuSetting(Bundle args) {
        menuSetting();
    }

    public void switchFragment(int position,Fragment fragment){
        if(! fragment.isAdded()) {
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
        }
        menuList.setItemChecked(position,true);
        drawerLayout.closeDrawer(drawer);
    }

    public class MenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return imageIcon.length;
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
            convertView = View.inflate(HomeActivity.this,R.layout.menu_list_item,null);
            TextView tvItem = (TextView) convertView.findViewById(R.id.tv_menu_item);
            tvItem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(convertView.getContext(),imageIcon[position]),null,null,null);
            tvItem.setText(getResources().getString(imageStr[position]));
            return convertView;
        }
    }

}
