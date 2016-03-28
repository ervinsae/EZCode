package com.ervin.litepal.api;

import com.ervin.litepal.model.meizhi.MeizhiEntity;
import com.ervin.litepal.model.meizhi.VideoEntity;
import com.ervin.litepal.request.RequestConstants;
import com.ervin.litepal.request.RestClient;

import rx.Observable;

/**
 * Created by Ervin on 2016/3/24.
 */
public class GetGankApi {

    public static Observable<MeizhiEntity> getMeizhiData(int page){
        return RestClient.RestRxClient(RequestConstants.GANK_URL,true).getMeizhiData(page);
    }

    public static Observable<VideoEntity> getVideoData(int page){
        return RestClient.RestRxClient(RequestConstants.GANK_URL,true).getVedioData(page);
    }
}
