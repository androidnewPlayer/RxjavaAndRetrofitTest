package com.hmkj.rxjavaandretrofittest.data.resulthandle;

/**
 * Created by Administrator on 2017/11/15.
 */

public class HttpResult<T>
{
    public int code;
    public String message;
    public T data;
}
