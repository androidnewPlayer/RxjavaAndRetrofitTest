package com.hmkj.rxjavaandretrofittest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hmkj.rxjavaandretrofittest.data.PersonInfoManager;
import com.hmkj.rxjavaandretrofittest.data.model.ClientServiceInfo;
import com.hmkj.rxjavaandretrofittest.data.model.MineInfo;
import com.hmkj.rxjavaandretrofittest.data.model.StockCodeInfo;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.ApiException;
import com.hmkj.rxjavaandretrofittest.data.resulthandle.LoadingSubscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private static final int REQ_CODE_TAKE_PICTURE = 0x01;
    private Button startButton;
    private Button stockButton;
    private Button upLoadButton;
    private ImageView testImageView;
    private LoadingSubscriber<MineInfo> mSubscriber;
    private LoadingSubscriber<StockCodeInfo> mStockSubscriber;
    private LoadingSubscriber<ClientServiceInfo> mClientCenterSubscriber;

    private File mTempFile;
    private File mCompressedFile;

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
        upLoadButton = (Button) findViewById(R.id.bt_image);
        testImageView = (ImageView) findViewById(R.id.iv_test);
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

        upLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePictureAndSaveIntoTempFile();
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
        mClientCenterSubscriber = new LoadingSubscriber<>(MainActivity.this, new LoadingSubscriber.OnResultListener<ClientServiceInfo>() {
            @Override
            public void onNext(ClientServiceInfo clientServiceInfo) {
                Log.e("result_tag",clientServiceInfo.getPhone());
                Log.e("result_tag",clientServiceInfo.getClientQuestionInfos().size()+"");
            }

            @Override
            public void onError(ApiException e) {

            }
        });

        PersonInfoManager.getInstance().loadClientCenterInfo(userId,mClientCenterSubscriber);
    }

    /**
     * 将相机拍下的图片保存到temp文件中;
     */
    private void capturePictureAndSaveIntoTempFile() {
        mTempFile = generateTempImageFile();
        if (mTempFile == null) {
            Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            imageUri  = FileProvider.getUriForFile(MainActivity.this,"com.account.fileprovider",mTempFile);
        }else
        {
            imageUri = Uri.fromFile(mTempFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQ_CODE_TAKE_PICTURE);
    }

    private void takePictureSuccessRespond(File file)
    {
        if (file == null) return;

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        testImageView.setImageBitmap(bitmap);

        LoadingSubscriber<String> subscriber = new LoadingSubscriber<>(MainActivity.this, new LoadingSubscriber.OnResultListener<String>() {
            @Override
            public void onNext(String s) {
                Log.e("result","successful" + s);
            }

            @Override
            public void onError(ApiException e) {
                Log.e("result","failed" + e.code + e.message);
            }
        });

       PersonInfoManager.getInstance().upLoadImageFile("419",file,subscriber);
    }

    private synchronized File compressImageIntoCompressFile(String imageFilePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
        if (bitmap == null) {
            Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            return null;
        }
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int minLength = bitmapWidth > bitmapHeight ? bitmapHeight : bitmapWidth;
        options.inJustDecodeBounds = false;
        if (minLength > 100) {
            options.inSampleSize = minLength / 100;
        }
        bitmap = BitmapFactory.decodeFile(imageFilePath, options);
        mCompressedFile = generateCompressImageFile();
        if (mCompressedFile == null) {
            return null;
        }
        try {
            FileOutputStream out = new FileOutputStream(mCompressedFile, false);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
        }
        return mCompressedFile;
    }

    private synchronized File generateTempImageFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File fileDir = Environment.getExternalStorageDirectory();
        File saveDir = new File(fileDir, "pz_app");
        if (!saveDir.exists()) {
            if (!saveDir.mkdirs()) {
                return null;
            }
        }
        mTempFile = new File(saveDir, "temp.png");
        if (mTempFile.exists()) {
            mTempFile.delete();
        }
        try {
            mTempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mTempFile;
    }

    private synchronized File generateCompressImageFile() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        File fileDir = Environment.getExternalStorageDirectory();
        File saveDir = new File(fileDir, "pz_app");
        if (!saveDir.exists()) {
            if (!saveDir.mkdirs()) {
                return null;
            }
        }
        mCompressedFile = new File(saveDir, "upLoad"+System.currentTimeMillis()+".png");
        if (mCompressedFile.exists()) {
            mCompressedFile.delete();
        }
        try {
            mCompressedFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mCompressedFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQ_CODE_TAKE_PICTURE:
                takePictureSuccessRespond(compressImageIntoCompressFile(mTempFile.getAbsolutePath()));
                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
        }

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
