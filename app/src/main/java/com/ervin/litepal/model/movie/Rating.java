package com.ervin.litepal.model.movie;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ervin on 2016/3/22.
 */
public class Rating {
    @SerializedName("max")
    private int max;

    @SerializedName("average")
    private double average;

    @SerializedName("stars")
    private String stars;

    @SerializedName("min")
    private int min;

    public void setMax(int max){
        this.max = max;
    }
    public int getMax(){
        return this.max;
    }
    public void setAverage(double average){
        this.average = average;
    }
    public double getAverage(){
        return this.average;
    }
    public void setStars(String stars){
        this.stars = stars;
    }
    public String getStars(){
        return this.stars;
    }
    public void setMin(int min){
        this.min = min;
    }
    public int getMin(){
        return this.min;
    }

}
