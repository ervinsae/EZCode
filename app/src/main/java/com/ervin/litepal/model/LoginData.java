package com.ervin.litepal.model;

import com.ervin.litepal.table.BadState;
import com.ervin.litepal.table.FriendList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ervin on 2015/11/12.
 */
public class LoginData {
    @Expose
    @SerializedName("phoneNumber")
    public String phoneNumber;

    @Expose
    @SerializedName("token")
    public String token;

    @Expose
    @SerializedName("friendsList")
    public List<FriendList> friendsList;


    @Expose
    @SerializedName("friendStatus")
    public int friendStatus;

    @Expose
    @SerializedName("ownBadStateList")
    public List<BadState> ownBadState;

    @Expose
    @SerializedName("nickname")
    public String nickname;

    @Expose
    @SerializedName("sex")
    public int sex;

    @Expose
    @SerializedName("height")
    public float height;

    @Expose
    @SerializedName("weight")
    public float weight;

    @Expose
    @SerializedName("headFileUrl")
    public String headFileUrl;

    @Expose
    @SerializedName("age")
    public int age;

    @Expose
    @SerializedName("vip")
    public int vip;

    @Expose
    @SerializedName("number")
    public int number;

    @Expose
    @SerializedName("whetherSetTarget")
    public int whetherSetTarget;

    @Expose
    @SerializedName("modelName")
    public String modelName;

    @Expose
    @SerializedName("modelId")
    public String modelId;

    @Expose
    @SerializedName("modelType")
    public int modelType;

    @Expose
    @SerializedName("steps")
    public int steps;

    @Expose
    @SerializedName("kilometres")
    public float kilometres;

    @Expose
    @SerializedName("totalCalories")
    public float totalCalories;

    @Expose
    @SerializedName("calories")
    public float calories;

    @Expose
    @SerializedName("timeInterval")
    public float timeInterval;

    @Expose
    @SerializedName("highQuality")
    public float highQuality;

    @Expose
    @SerializedName("waterAmount")
    public int waterAmount;

    @Expose
    @SerializedName("totalIntake")
    public int totalIntake;

    @Expose
    @SerializedName("intake")
    public int intake;

    @Expose
    @SerializedName("intakeType")
    public int intakeType;

    @Expose
    @SerializedName("configurationChange")
    public String configurationChange;
}
