package com.hmkj.rxjavaandretrofittest.data.resulthandle;

import rx.functions.Func1;

/**
 * Created by Administrator on 2017/11/15.
 */

public class ServerResultFunc<T> implements Func1<HttpResult<T>,T>
{

    @Override
    public T call(HttpResult<T> result)
    {
        if (result.code != 200)
        {
            throw new ServerException(result.code,result.message);
        }
        return result.data;
    }
}
