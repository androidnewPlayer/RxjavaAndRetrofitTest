package com.hmkj.rxjavaandretrofittest.data.resulthandle;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/11/15.
 */

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>>
{
    @Override
    public Observable<T> call(Throwable throwable)
    {
        return Observable.error(ExceptionHandler.handleException(throwable));
    }
}
