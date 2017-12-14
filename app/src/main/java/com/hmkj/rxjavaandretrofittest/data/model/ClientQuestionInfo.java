package com.hmkj.rxjavaandretrofittest.data.model;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/3.
 */

public class ClientQuestionInfo {

    private String title;

    private Map<String,String[]> questionMap;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String[]> getQuestionMap() {
        return questionMap;
    }

    public void setQuestionMap(Map<String, String[]> questionMap) {
        this.questionMap = questionMap;
    }
}
