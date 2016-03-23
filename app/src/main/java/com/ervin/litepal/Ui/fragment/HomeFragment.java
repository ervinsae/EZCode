package com.ervin.litepal.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ervin.litepal.model.AppInfo;
import com.ervin.litepal.R;
import com.ervin.litepal.utils.AppinfoUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Ervin on 2015/12/18.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.home_fragment_app_list)
    RecyclerView appListView;
    @Bind(R.id.home_fragment_container)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.loading)
    AVLoadingIndicatorView mLoading;
    private TextView tvTitle;

    AppListAdapter adapter;
    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView  = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        appListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeLayout.setOnRefreshListener(this);
        refreshTheList();
    }

    private void initView(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.toolbar_center_title);
        tvTitle.setText(R.string.menu_home);

        mLoading.setBackgroundColor(getResources().getColor(R.color.material_cyan_300));
        mLoading.setVisibility(View.VISIBLE);
    }

    private void refreshTheList() {
        getApps().toSortedList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppInfo>>() {

                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "Here is the list!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        Log.d("ervin","----------onNext");
                        if(appInfos == null){
                            Log.d("ervin","----------APPInfo is null");
                        }
                        mLoading.setVisibility(View.GONE);
                        appListView.setVisibility(View.VISIBLE);
                        if(adapter == null){
                            adapter = new AppListAdapter(appInfos);
                        }else{
                            adapter.addApplications(appInfos);
                            //adapter.notifyDataSetChanged();
                        }
                        appListView.setAdapter(adapter);
                        swipeLayout.setRefreshing(false);
                    }
                });
    }

    private Observable<AppInfo> getApps() { //被观察者

       return Observable.create(new Observable.OnSubscribe<AppInfo>() {
           @Override
           public void call(Subscriber<? super AppInfo> subscriber) {
               List<AppInfo> list = AppinfoUtils.getAllAppInfo(getActivity());
               for(AppInfo appInfo : list){
                   subscriber.onNext(appInfo);
               }
               if (!subscriber.isUnsubscribed()){
                   subscriber.onCompleted();
               }
               Log.d("ervin",list.size()+"个");
           }
       });
    }

    /**
     * combine getApps() and refreshTheList() with Retrolambda
     */
    /*public void getData(){
        Observable.create()
    }*/


    @Override
    public void onRefresh() {
        Log.i("ervin","刷新了");
        refreshTheList();
    }


    class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder>{

        List<AppInfo> appInfos;
        AppListAdapter(List<AppInfo> appInfos){
            this.appInfos = appInfos;
        }

        AppListAdapter(){

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.adapter_conversation_friend_interact,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.icon.setImageDrawable(appInfos.get(position).getIcon());
            holder.name.setText(appInfos.get(position).getLabelname());
        }

        @Override
        public int getItemCount() {
            return appInfos.size();
        }

        public void addApplications(List<AppInfo> appInfos){
            this.appInfos = appInfos;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @Bind(R.id.iv_avatar)
            ImageView icon;
            @Bind(R.id.tv_name)
            TextView name;
            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

}
