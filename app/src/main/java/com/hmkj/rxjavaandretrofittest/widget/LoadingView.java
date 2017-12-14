package com.hmkj.rxjavaandretrofittest.widget;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hmkj.rxjavaandretrofittest.R;

/**
 * Created by zhuzidong
 * on 2017/9/26.
 * Email:591079255@qq.com
 */

public class LoadingView {
    private Context mContext;
    private Dialog mDialog;
    private ImageView mIvLoading;

    public LoadingView(Context context) {
        mContext = context;
        initView();
    }

    private void initView() {
        //创建对话框
        mDialog = new Dialog(mContext, R.style.LoadingDialogStyle);
        View view = View.inflate(mContext, R.layout.dialog_loading_view, null);
        mIvLoading = ((ImageView) view.findViewById(R.id.iv_loading));
        ObjectAnimator or = ObjectAnimator.ofFloat(mIvLoading, "rotation", 0,360);
        or.setDuration(1000);
        or.setRepeatCount(999);
        or.setRepeatMode(ObjectAnimator.RESTART);
        or.start();
        mDialog.setContentView(view);
        WindowManager.LayoutParams params =mDialog.getWindow().getAttributes();
        params.width= WindowManager.LayoutParams.WRAP_CONTENT;
        params.height= WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity= Gravity.CENTER;

        //点击对话框外部消失
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
    }
    public void show(){
        if (mDialog!= null){
            mDialog.show();
        }

    }
    public void dismiss(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }

    }
    public boolean isShowing(){
        if (mDialog != null){
            return mDialog.isShowing();
        }
        return false;
    }

}
