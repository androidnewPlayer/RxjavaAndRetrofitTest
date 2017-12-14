package com.hmkj.rxjavaandretrofittest.data.resulthandle;

/**
 * Created by Administrator on 2017/11/15.
 */

public class ApiException extends Exception {

    public int code;
    public String message;

    public ApiException(Throwable throwable, int code)
    {
        super(throwable);
        this.code = code;
    }
}
