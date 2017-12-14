package com.hmkj.rxjavaandretrofittest.data.resulthandle;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/15.
 */

public abstract class ErrorSubscriber<T> extends Subscriber<T>
{

    @Override
    public void onError(Throwable e)
    {
        if (e instanceof ApiException)
        {
            error((ApiException) e);
        }else
        {
            error(new ApiException(e,123));
        }
    }

    public abstract void error(ApiException e);
}
