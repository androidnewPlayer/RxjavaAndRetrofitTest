package com.hmkj.rxjavaandretrofittest.data;

import com.hmkj.rxjavaandretrofittest.data.model.ClientServiceInfo;
import com.hmkj.rxjavaandretrofittest.data.model.MineInfo;
import com.hmkj.rxjavaandretrofittest.data.model.StockCodeInfo;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.HttpResponseFunc;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.LoadingSubscriber;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.ServerResultFunc;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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

    public void upLoadImageFile(String userId, File file, LoadingSubscriber<String> subscriber)
    {
        DataService service = RetrofitManager.getInstance().getRetrofitClient().create(DataService.class);

        MultipartBody.Part userIdPart = MultipartBody.Part.createFormData("userId",userId);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image1",file.getName(),fileBody);

        service.upLoadImageFile(userIdPart,filePart).map(new ServerResultFunc<JSONObject>()).
                onErrorResumeNext(new HttpResponseFunc<JSONObject>()).map(new Func1<JSONObject, String>() {
            @Override
            public String call(JSONObject object) {
                return null;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
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
