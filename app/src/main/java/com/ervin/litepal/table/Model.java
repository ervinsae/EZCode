package com.ervin.litepal.table;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by Ervin on 2015/11/20.
 */
public class Model extends DataSupport {

    @Column(unique = true, defaultValue = "ervin")
    public String name;


    public String company;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String location;
    public String email;

    @Column(ignore = true)
    public int followers;
}
