package com.hmkj.rxjavaandretrofittest.data;

import com.hmkj.rxjavaandretrofittest.data.model.ClientServiceInfo;
import com.hmkj.rxjavaandretrofittest.data.model.MineInfo;
import com.hmkj.rxjavaandretrofittest.data.model.StockCodeInfo;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.HttpResponseFunc;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.LoadingSubscriber;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.ServerResultFunc;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/15.
 */

public class PersonInfoManager {


    private static final String TAG = "PersonInfoManager";
    private static PersonInfoManager mManager;

    private PersonInfoManager()
    {
        // need private;
    }

    public static PersonInfoManager getInstance()
    {
        if (mManager != null)
        {
            return mManager;
        }
        synchronized (TAG)
        {
            if (mManager == null)
            {
                mManager = new PersonInfoManager();
            }
        }
        return mManager;
    }

    public void loadMineInfo(String userId, LoadingSubscriber<MineInfo> subscriber)
    {
        DataService service = RetrofitManager.getInstance().getRetrofitClient().create(DataService.class);
        service.getMineInfo(userId).map(new ServerResultFunc<MineInfo>()).onErrorResumeNext(new HttpResponseFunc<MineInfo>())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    public void loadStockInfo(String userId, String stockCode, LoadingSubscriber<StockCodeInfo> subscriber)
    {
        DataService service = RetrofitManager.getInstance().getRetrofitClient().create(DataService.class);
        transform(service.getStockInfo(userId,stockCode),subscriber);
    }

    public void loadClientCenterInfo(String userId,LoadingSubscriber<ClientServiceInfo> subscriber)
    {
        DataService service = RetrofitManager.getInstance().getRetrofitClient().create(DataService.class);
        transform(service.getClientCenterInfo(userId),subscriber);
    }


    private <T> void transform(Observable observable, LoadingSubscriber subscriber)
    {
        observable.map(new ServerResultFunc<T>())
                .onErrorResumeNext(new HttpResponseFunc<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
