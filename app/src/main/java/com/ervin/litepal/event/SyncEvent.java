package com.ervin.litepal.event;

/**
 * Created by Ervin on 2015/12/15.
 */
public class SyncEvent extends EventBase {

    public static final int SUCCESS = 0;
    public static final int FAIL = 1;

    public int code;
    public SyncEvent(int code){
        this.code = code;
    }
}
