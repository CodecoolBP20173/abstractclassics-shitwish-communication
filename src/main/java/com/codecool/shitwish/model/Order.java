package com.codecool.shitwish.model;

import java.util.Date;
import java.util.List;

public class Order {

    private Integer id;

    private Integer userId;

    private List<Present> presentList;

    private Date timeStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Present> getPresentList() {
        return presentList;
    }

    public void setPresentList(List<Present> presentList) {
        this.presentList = presentList;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
