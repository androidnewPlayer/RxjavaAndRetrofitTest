package com.hmkj.rxjavaandretrofittest.data.model;

/**
 * Created by Administrator on 2017/11/15.
 */

public class MineInfo {

    private String availableMoney;
    private String name;
    private int auth;
    private String money;

    public String getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(String availableMoney) {
        this.availableMoney = availableMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
