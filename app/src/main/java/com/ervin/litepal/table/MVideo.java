package com.ervin.litepal.table;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by Ervin on 2016/3/24.
 */
public class MVideo extends DataSupport {
    @Column(unique = true, defaultValue = "unknown")
    public String url;

    public String type;

    public String desc;

    public String who;

    public boolean used;

    public Date createdAt;

    public Date updatedAt;

    public Date publishedAt;
}
