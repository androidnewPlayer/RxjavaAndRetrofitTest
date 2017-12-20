package com.hmkj.rxjavaandretrofittest.data;

import com.hmkj.rxjavaandretrofittest.data.model.ClientServiceInfo;
import com.hmkj.rxjavaandretrofittest.data.model.MineInfo;
import com.hmkj.rxjavaandretrofittest.data.model.StockCodeInfo;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.HttpResult;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

//    @FormUrlEncoded
//    @POST("app/open/discover/helpCenter")
//    Observable<HttpResult<String>> getClientCenterInfo(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("app/open/discover/helpCenter")
    Observable<HttpResult<ClientServiceInfo>> getClientCenterInfo(@Field("userId") String userId);

    @Multipart
    @POST("app/member/account/doUploadPic")
    Observable<HttpResult<JSONObject>> upLoadImageFile(@Part MultipartBody.Part userIdPart,@Part MultipartBody.Part filePart);
}
