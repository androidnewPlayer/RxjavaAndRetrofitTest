package com.hmkj.rxjavaandretrofittest.data;

import com.hmkj.rxjavaandretrofittest.data.model.StockCodeInfo;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.HttpResult;
import com.hmkj.rxjavaandretrofittest.data.model.MineInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/11/15.
 */

public interface DataService
{
    @FormUrlEncoded
    @POST("app/member/account/indexJson")
    Observable<HttpResult<MineInfo>> getMineInfo(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("app/open/quote/getStockByCode")
    Observable<HttpResult<StockCodeInfo>> getStockInfo(@Field("userId") String userId,@Field("code") String code);

    @FormUrlEncoded
    @POST("/app/open/discover/helpCenter")
    Observable<HttpResult<String>> getClientCenterInfo(@Field("userId") String userId);
}
