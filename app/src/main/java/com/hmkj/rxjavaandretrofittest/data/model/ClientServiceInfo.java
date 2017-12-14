package com.hmkj.rxjavaandretrofittest.data.model;

import java.util.List;

/**
 * Created by Administrator on 2017/11/3.
 */

public class ClientServiceInfo {

    private String phone;
    private List<ClientQuestionInfo> clientQuestionInfos;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ClientQuestionInfo> getClientQuestionInfos() {
        return clientQuestionInfos;
    }

    public void setClientQuestionInfos(List<ClientQuestionInfo> clientQuestionInfos) {
        this.clientQuestionInfos = clientQuestionInfos;
    }
}
