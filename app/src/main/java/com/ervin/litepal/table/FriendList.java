package com.ervin.litepal.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Ervin on 2015/11/12.
 */
public class FriendList extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    long id;

    @Column()
    @Expose
    @SerializedName("userfriendsName")
    public String userfriendsName;

    @Column
    @Expose
    @SerializedName("friendsStype")
    public int friendsStype;

    @Expose
    @SerializedName("friendBadStateList")
    public List<BadState> friendBadStateList;

    @Column
    @Expose
    @SerializedName("friendNickname")
    public String friendNickname;


    @Column
    @Expose
    @SerializedName("friendHeadUrl")
    public String friendHeadUrl;


    @Column
    @Expose
    @SerializedName("healthIndex")
    public int healthIndex;


    @Column
    @Expose
    @SerializedName("state")
    public int state;

    @Column
    @Expose
    @SerializedName("friendsAge")
    public int friendsAge;

    @Column
    @Expose
    @SerializedName("friendsWeight")
    public float friendsWeight;

    @Column
    @SerializedName("friendsHeight")
    public float friendsHeight;

    @Column
    @SerializedName("friendsSex")
    public String friendsSex;

}
