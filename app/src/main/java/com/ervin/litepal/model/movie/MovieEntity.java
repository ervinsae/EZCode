package com.ervin.litepal.model.movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ervin on 2016/3/22.
 */
public class MovieEntity {

    private int count;

    private int start;

    private int total;

    @SerializedName("subjects")
    private List<Subjects> subjects;

    private String title;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setStart(int start){
        this.start = start;
    }
    public int getStart(){
        return this.start;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setSubjects(List<Subjects> subjects){
        this.subjects = subjects;
    }
    public List<Subjects> getSubjects(){
        return this.subjects;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}
