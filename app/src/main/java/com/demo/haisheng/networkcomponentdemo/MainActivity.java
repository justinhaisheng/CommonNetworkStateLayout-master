package com.demo.haisheng.networkcomponentdemo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.demo.haisheng.networkcomponenntlibrary.NetworkComponent;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.network_component)
    NetworkComponent mNetworkComponent;
    @Bind(R.id.bt)
    Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


//    <enum name="succeed" value="0"/>
//    <enum name="loading" value="1"/>
//    <enum name="networkErr" value="2"/>
//    <enum name="empty" value="3"/>
    @OnClick(R.id.bt)
    public void onClick() {
        //此处模仿网络请求
        mNetworkComponent.showLoadingLayout();//在请求数据之前先进行加载
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);//休眠就当在请求数据
                Random random=new Random();
                final int ran = random.nextInt(3) + 1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (ran){
                            case 0:
                            case 1:
                                mNetworkComponent.showSucceedLayout();
                                break;
                            case 2:
                                mNetworkComponent.showNetworkErrLayout();
                                break;
                            case 3:
                                mNetworkComponent.showEmptyLayout();
                                break;
                        }
                    }
                });
            }
        }).start();
    }
}
