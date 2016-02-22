package com.ervin.litepal.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;


public class BadState extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    public long id;

    @Column
    public String phone;

    @Column
    @Expose
    @SerializedName("friendBadState")
    public int friendBadState;


    @Column
    @Expose
    @SerializedName("ownBadState")
    public int ownBadState;


    @Column
    @Expose
    @SerializedName("actOfStateId")
    public String actOfStateId;

    @Column
    @Expose
    @SerializedName("numericalValue")
    public String numericalValue;

    @Expose
    @SerializedName("dateTime")
    public String dateTime;//不良状态产生时间
}
