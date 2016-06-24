package com.demo.haisheng.networkcomponentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.haisheng.networkcomponenntlibrary.CommonNetworkStateLayout;

import java.util.Random;

public class NetworkStateActivity extends AppCompatActivity {

    private static final String TAG = "NetworkStateActivity";

    CommonNetworkStateLayout mViewCommonNetworkState;

    Button mChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_state);
        mViewCommonNetworkState = (CommonNetworkStateLayout) findViewById(R.id.view_common_network_state);
        mViewCommonNetworkState.setRetryOnclickListener(new CommonNetworkStateLayout.RetryClickListener() {
            @Override
            public void onClick(View v) {
                requestNetwork();
            }
        });
        //模拟请求网络
        requestNetwork();
        mChange = (Button) findViewById(R.id.change);
    }

    /*
    *模拟网络请求，然后在主线程中设置相应状态
    *@author luhaisheng
    *@time 2016/6/17 10:41
    */
    private void requestNetwork() {
        //网络开始请求。先显示loading页面
        mViewCommonNetworkState.showLoadingLayout("加载中");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        int i = random.nextInt(4) + 1;
                        Log.d(TAG, "I" + i);
                        switch (i) {
                            case 0://网络请求成功
                                break;
                            case 1:
                                mViewCommonNetworkState.showSucceedLayout();
                                //mViewCommonNetworkState.showLoadingLayout();//可以重载此方法
                                break;
                            case 2://没有网络（无网：包括无wifi,无数据流量）
                                //  mViewCommonNetworkState.showNetworkErrLayout("出错，无情的出错");//可以重载此方法
                                mViewCommonNetworkState.showNetworkErrLayout();
                                break;
                            case 3://网络请求为空
                                //mViewCommonNetworkState.showEmptyLayout("空，我还是空");//可以重载此方法
                                mViewCommonNetworkState.showEmptyLayout();
                                break;
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void click(View view) {
        requestNetwork();
    }


}
