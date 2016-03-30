package com.ervin.litepal.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ervin.litepal.R;
import com.ervin.litepal.api.GetGankApi;
import com.ervin.litepal.model.meizhi.MeizhiEntity;
import com.ervin.litepal.model.meizhi.VideoEntity;
import com.ervin.litepal.table.MVideo;
import com.ervin.litepal.table.Meizhis;
import com.ervin.litepal.ui.adapter.MeizhiListAdapter;
import com.ervin.litepal.utils.DateUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ervin on 2016/3/24.
 */
public class MeizhiFragment extends Fragment {

    @Bind(R.id.rv_meizhi)
    RecyclerView mRecycleview;
    @Bind(R.id.main_fab)
    FloatingActionButton mFab;

    private MeizhiListAdapter adpter;
    private static List<Meizhis> mMeizhiList;
    private int clickNum = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_meizhi,container,false);
        ButterKnife.bind(this,view);
        mMeizhiList = new ArrayList<>();
        mMeizhiList.addAll(DataSupport.findAll(Meizhis.class));
        initView();
        return view;
    }

    private void initView() {

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecycleview.setLayoutManager(layoutManager);

        adpter = new MeizhiListAdapter(getActivity(),mMeizhiList);
        mRecycleview.setAdapter(adpter);

        refreshData(1);

        mFab.setOnClickListener(v -> {
            refreshData(clickNum);
        });
    }

    private void refreshData(int page) {

        Observable.zip(GetGankApi.getMeizhiData(page),GetGankApi.getVideoData(page),this::mergeDesc)//zip操作符是把两个observable提交的结果，严格按照顺序进行合并，作为参数去调用第三个方法
                .map(meizhiEntity -> meizhiEntity.results)//map转换类型操作符，将返回的MeizhiEntity类型的数据转成List<Meizhis>类型
                .flatMap(Observable::from)//flatmap输出一个一对多的数据，它输出的Observable对象才是Subcribe所真正想接收的数据,这里变形为：Observable.from(List<Meizhis>)，观察者将接收到一个个Meizhi对象。
                .toSortedList((meizhis, meizhis2) -> {//（Returns an Observable that emits a list that contains the items emitted by the source Observable, in a sorted order.将一个个的Meizhi对象根据规则变成list<>）
                    return meizhis2.publishedAt.compareTo(meizhis.publishedAt);
                })
                .doOnNext(this::saveMeizhis)//在观察者接收之前保存数据，数据为MeiZhis,上一条链已经将一个个的meizhi对象封装成了已经排序好了的List。
                .subscribeOn(Schedulers.io())//事件产生的线程（在IO线程调用subscribe()）
                .observeOn(AndroidSchedulers.mainThread())//事件的消费线程在主线程，也就是subscribe里代码所执行的线程
                .subscribe(new Subscriber<List<Meizhis>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(),"data finish loading",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Meizhis> meizhises) {
                        clickNum++;
                        Log.d("ervin","点击次数" + clickNum);
                        mMeizhiList.addAll(meizhises);
                        adpter.notifyDataSetChanged();
                    }
                });



    }
    private void loadMeizhiData(int page){
        GetGankApi.getMeizhiData(page).subscribeOn(Schedulers.io())
                .map(meizhiEntity -> meizhiEntity.results)
                .doOnNext(this::saveMeizhis)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Meizhis>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(),"data finish loading",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Meizhis> meizhises) {
                        clickNum++;
                        Log.d("ervin","点击次数" + clickNum);
                        mMeizhiList.addAll(meizhises);
                        adpter.notifyDataSetChanged();
                        loadVideoData(page);
                    }
                });
    }

    private void loadVideoData(int page){
        GetGankApi.getVideoData(page).subscribeOn(Schedulers.io())
                .map(videoEntity -> videoEntity.results)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MVideo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(),"video:" + e.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<MVideo> mVideos) {
                        Log.d("ervin","video desc" + mVideos.get(0).desc);
                    }
                });
    }

    public void saveMeizhis(List<Meizhis> list){
        for(Meizhis meizhi : list){
            //if(DataSupport.findBySQL("select * from meizhis where url = ?"+ meizhi.url) != null) meizhi.save();

        }
    }

    //合并妹纸和vedio的描述文本
    public MeizhiEntity mergeDesc(MeizhiEntity mezhi, VideoEntity video){
        Observable.from(mezhi.results).forEach(meizhis -> {meizhis.desc = meizhis.desc + " " + getFirstVideoDesc(meizhis.publishedAt,video.results);});
        return mezhi;
    }

    private int mLastVideoIndex = 0;
    private String getFirstVideoDesc(Date publishedAt, List<MVideo> results) {
        String videoDesc = "";
        for (int i = mLastVideoIndex; i < results.size(); i++) {
            MVideo video = results.get(i);
            if (DateUtils.isTheSameDay(publishedAt, video.publishedAt)) {
                videoDesc = video.desc;
                Log.i("ervin",videoDesc);
                mLastVideoIndex = i;
                break;
            }
        }
        return videoDesc;
    }

    @Override
    public void onPause() {
        super.onPause();
        clickNum = 1;
    }

}
