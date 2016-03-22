package com.ervin.litepal.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Ervin on 2016/3/2.
 */
public class AppInfo implements Comparable<Object>{

    private Drawable icon;
    private String labelname;
    private String packagename;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo)another;
        return getLabelname().compareTo(f.getLabelname());
    }
}
