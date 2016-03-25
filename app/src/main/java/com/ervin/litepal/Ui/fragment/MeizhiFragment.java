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
import com.ervin.litepal.api.GetMeizhiApi;
import com.ervin.litepal.table.Meizhis;
import com.ervin.litepal.ui.adapter.MeizhiListAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    private static int clickNum = 1;


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

        //refreshData(1);

        mFab.setOnClickListener(v -> {
            refreshData(clickNum);
        });
    }

    private void refreshData(int page) {

        GetMeizhiApi.getMeizhiData(page).subscribeOn(Schedulers.io())
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
                        //mMeizhiList.clear();
                        clickNum++;
                        Log.d("ervin","点击次数" + clickNum);
                        mMeizhiList.addAll(meizhises);
                        adpter.notifyDataSetChanged();
                    }
                });

    }

    public void saveMeizhis(List<Meizhis> list){
        for(Meizhis meizhi : list){
            //if(DataSupport.findBySQL("select * from meizhis where url = ?"+ meizhi.url) != null) meizhi.save();
        }
    }



}
