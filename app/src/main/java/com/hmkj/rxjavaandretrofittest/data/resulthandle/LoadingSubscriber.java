package com.hmkj.rxjavaandretrofittest.data.resulthandle;

import android.content.Context;

import com.hmkj.rxjavaandretrofittest.widget.LoadingView;

/**
 * Created by Administrator on 2017/11/19.
 */

public class LoadingSubscriber<T> extends ErrorSubscriber<T>
{
    private Context mContext;
    private OnResultListener<T> mResultListener;
    private LoadingView mLoadView;

    public LoadingSubscriber(Context context, OnResultListener<T> resultListener)
    {
        this.mContext = context;
        this.mResultListener = resultListener;
        initLoadPop();
    }


    private void initLoadPop()
    {
        mLoadView = new LoadingView(mContext);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mLoadView.show();
    }

    @Override
    public void error(ApiException e)
    {
        mLoadView.dismiss();
        if (mResultListener != null)
        {
            mResultListener.onError(e);
        }
    }

    @Override
    public void onCompleted()
    {
        mLoadView.dismiss();
    }

    @Override
    public void onNext(T t)
    {
        if (mResultListener != null)
        {
            mResultListener.onNext(t);
        }
    }


    public interface OnResultListener<T>
    {
        public void onNext(T t);
        public void onError(ApiException e);
    }
}
