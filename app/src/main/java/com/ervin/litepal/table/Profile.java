package com.ervin.litepal.table;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Ervin on 2015/11/11.
 */
public class Profile extends DataSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    private int sex;
    private float weight;
    private float height;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
