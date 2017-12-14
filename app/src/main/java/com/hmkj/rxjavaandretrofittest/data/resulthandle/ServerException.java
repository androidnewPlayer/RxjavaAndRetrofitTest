package com.hmkj.rxjavaandretrofittest.data.resulthandle;

/**
 * Created by Administrator on 2017/11/15.
 */

public class ServerException extends RuntimeException {

    public int code;
    public String message;

    public ServerException(int code,String message)
    {
        this.code = code;
        this.message = message;
    }
}
