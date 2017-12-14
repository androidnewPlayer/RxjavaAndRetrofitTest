package com.hmkj.rxjavaandretrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hmkj.rxjavaandretrofittest.data.PersonInfoManager;
import com.hmkj.rxjavaandretrofittest.data.model.MineInfo;
import com.hmkj.rxjavaandretrofittest.data.model.StockCodeInfo;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.ApiException;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.LoadingSubscriber;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private Button startButton;
    private Button stockButton;
    private LoadingSubscriber<MineInfo> mSubscriber;
    private LoadingSubscriber<StockCodeInfo> mStockSubscriber;
    private LoadingSubscriber<String> mClientCenterSubscriber;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
        loadClientCenterInfo();
    }


    private void initView()
    {
        startButton = (Button) findViewById(R.id.bt_start);
        stockButton = (Button) findViewById(R.id.bt_stock);
    }

    private void initAction()
    {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadMineInfo();
            }
        });


        stockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loadStockInfo();
            }
        });

    }


    private void loadMineInfo()
    {
        String userId = "7";
        mSubscriber = new LoadingSubscriber<>(MainActivity.this, new LoadingSubscriber.OnResultListener<MineInfo>()
        {
            @Override
            public void onNext(MineInfo mineInfo)
            {
                Log.e(TAG,mineInfo.getMoney());
                Log.e(TAG,mineInfo.getName());
                Log.e(TAG,mineInfo.getAvailableMoney());
            }

            @Override
            public void onError(ApiException e)
            {
                Toast.makeText(MainActivity.this, e.message + e.code, Toast.LENGTH_SHORT).show();
            }
        });
        PersonInfoManager.getInstance().loadMineInfo(userId,mSubscriber);
    }

    private void loadStockInfo()
    {
        String userId = "7";
        String stockCode = "000001";
        mStockSubscriber = new LoadingSubscriber<>(MainActivity.this, new LoadingSubscriber.OnResultListener<StockCodeInfo>() {
            @Override
            public void onNext(StockCodeInfo stockCodeInfo)
            {
                Log.e(TAG,stockCodeInfo.getAsk_price1());
                Log.e(TAG,stockCodeInfo.getCode_name());
            }

            @Override
            public void onError(ApiException e)
            {
                Toast.makeText(MainActivity.this, e.message + e.code, Toast.LENGTH_SHORT).show();
            }
        });

        PersonInfoManager.getInstance().loadStockInfo(userId,stockCode,mStockSubscriber);
    }

    private void loadClientCenterInfo()
    {
        String userId = "7";
        mClientCenterSubscriber = new LoadingSubscriber<>(MainActivity.this, new LoadingSubscriber.OnResultListener<String>() {
            @Override
            public void onNext(String s) {
                Log.e("result",s);
            }

            @Override
            public void onError(ApiException e) {
                Log.e("result",e.message + e.code);
            }
        });

        PersonInfoManager.getInstance().loadClientCenterInfo(userId,mClientCenterSubscriber);
    }


    @Override
    protected void onDestroy()
    {
        if (mSubscriber != null)
        {
            mSubscriber.unsubscribe();
            mSubscriber = null;
        }
        if (mStockSubscriber != null)
        {
            mStockSubscriber.unsubscribe();
            mStockSubscriber = null;
        }
        super.onDestroy();
    }
}
