package com.ervin.litepal.event;

/**
 * Created by Ervin on 2015/12/15.
 */
public class DataReady extends EventBase {

    public String type;
    public DataReady(String type){
        this.type = type;
    }
}
