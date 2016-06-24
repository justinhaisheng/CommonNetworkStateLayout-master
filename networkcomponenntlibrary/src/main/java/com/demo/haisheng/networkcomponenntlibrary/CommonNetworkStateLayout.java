package com.demo.haisheng.networkcomponenntlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * @创建者 luhaisheng
 * @创建时间 2016/6/2 14:42
 * @描述 ${TOO}
 * @更新者 $AUTHOR$
 * @创建时间 2016/6/2 14:42
 * @描述
 */
public class CommonNetworkStateLayout extends FrameLayout {

    private static final String TAG = "CommonNetworkStateView";

    private RelativeLayout mLoadingRl;
    private TextView mNetWorkErrTv;
    private RelativeLayout mConnectErrRl;
    private View mRoot;


    private int mLoadingThemecolor;
    private int mConnecterrThemecolor;
    private int mNetworkerrhemecolor;
    private int mNullThemecolor;


    private State mState= State.SUCCEED;

    private View mEmptyCustomLayout;
    private View mNetworkErrCustomLayout;
    private View mConnectErrCustomLayout;
    private View mloadingCustomLayout;

    private String mEmptyText;
    private String mNetworkErrText;
    private String mConnectErrText;
    private String mLoadingText;
    private View mSucceedLayout;
    private int mLoadingDrawableId=-1;
    private ProgressBar mProgressBar;
    private TextView mLoadingTv;
    private int mConnectErrDrawableId=-1;
    private TextView mEmptyTv;

    Context mContext;
    public CommonNetworkStateLayout(Context context) {
        super(context);
        mContext=context;
        initView(context);
    }


    public CommonNetworkStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CommonNetworkStateLayout);

        //没网络状态页面(用户自己设置的)
        mNetworkErrCustomLayout=hasValueToView(context, typedArray,R.styleable.CommonNetworkStateLayout_networkErrLayout);
        Log.i(TAG,"mNetworkErrCustomLayout:"+mNetworkErrCustomLayout);

        //正在加载时的页面（用户自己设置的）
        mloadingCustomLayout=hasValueToView(context,typedArray,R.styleable.CommonNetworkStateLayout_loadingLayout);
        Log.i(TAG,"mloadingCustomLayout:"+mloadingCustomLayout);

        //空的页面（用户自己设置的）
        mEmptyCustomLayout=hasValueToView(context,typedArray,R.styleable.CommonNetworkStateLayout_emptyLayout);
        Log.i(TAG,"mEmptyCustomLayout:"+mEmptyCustomLayout);

        //网络连接失败的页面（用户自己设置的）
        mConnectErrCustomLayout=hasValueToView(context,typedArray,R.styleable.CommonNetworkStateLayout_connectErrLayout);
        Log.i(TAG,"mConnectErrCustomLayout:"+mConnectErrCustomLayout);

        //默认为白色
        mLoadingThemecolor = typedArray.getColor(R.styleable.CommonNetworkStateLayout_loadingThemeColor, -1);
        mConnecterrThemecolor = typedArray.getColor(R.styleable.CommonNetworkStateLayout_connecterrThemeColor, -1);
        mNetworkerrhemecolor = typedArray.getColor(R.styleable.CommonNetworkStateLayout_networkerrThemeColor, -1);
        mNullThemecolor = typedArray.getColor(R.styleable.CommonNetworkStateLayout_emptyThemeColor,-1);
        Log.i(TAG,"mLoadingThemecolor:"+mLoadingThemecolor+"     mConnecterrThemecolor:"+mConnecterrThemecolor+"     mNetworkerrhemecolor:"+mNetworkerrhemecolor+"     mNullThemecolor:"+mNullThemecolor);

        //状态
        if(typedArray.hasValue(R.styleable.CommonNetworkStateLayout_state)) {
            //默认值为加载成功
            mState = State.fromValue(typedArray.getInt(R.styleable.CommonNetworkStateLayout_state, State.SUCCEED.getValue()));
            Log.i(TAG,"mState:"+mState);
        }

        if(typedArray.hasValue(R.styleable.CommonNetworkStateLayout_emptyText)) {
            mEmptyText = typedArray.getString(R.styleable.CommonNetworkStateLayout_emptyText);
            Log.i(TAG,"mEmptyText:"+mEmptyText);
        }

        if(typedArray.hasValue(R.styleable.CommonNetworkStateLayout_networkErrText)) {
            mNetworkErrText = typedArray.getString(R.styleable.CommonNetworkStateLayout_networkErrText);
            Log.i(TAG,"mNetworkErrText:"+mNetworkErrText);
        }

        if(typedArray.hasValue(R.styleable.CommonNetworkStateLayout_connectErrText)) {
            mConnectErrText = typedArray.getString(R.styleable.CommonNetworkStateLayout_connectErrText);
            Log.i(TAG,"mConnectErrText:"+mConnectErrText);
        }

        if(typedArray.hasValue(R.styleable.CommonNetworkStateLayout_connectErrDrawable)){
            mConnectErrDrawableId = typedArray.getResourceId(R.styleable.CommonNetworkStateLayout_connectErrDrawable, -1);
            Log.i(TAG,"mConnectErrDrawableId:"+mConnectErrDrawableId);
        }

        if(typedArray.hasValue(R.styleable.CommonNetworkStateLayout_loadingDrawable)){
            mLoadingDrawableId = typedArray.getResourceId(R.styleable.CommonNetworkStateLayout_loadingDrawable, -1);
            Log.i(TAG,"mLoadingLayoutId:"+mLoadingDrawableId);
        }

        if(typedArray.hasValue(R.styleable.CommonNetworkStateLayout_loadingText)) {
            mLoadingText = typedArray.getString(R.styleable.CommonNetworkStateLayout_loadingText);
            Log.i(TAG,"mLoadingText:"+mLoadingText);
        }

        typedArray.recycle();//回收

    }

    //-------------------向外提供的方法-------------------------------
    //显示成功页面
    public void showSucceedLayout(){
        Log.d(TAG,"showSucceedLayout");
        setState(State.SUCCEED,null);
    }
    //显示正在加载的页面
    public void showLoadingLayout(){
        Log.d(TAG,"showLoadingLayout");
        setState(State.LOADING,null);
    }

    //显示正在加载的页面
    public void showLoadingLayout(String loadingText){
        Log.d(TAG,"showLoadingLayout");
        setState(State.LOADING,loadingText);
    }


    //显示没有网络的页面
    public void showNetworkErrLayout(){
        Log.d(TAG,"showNetworkErrLayout");
        setState(State.NETWORKERR,null);
    }
    public void showNetworkErrLayout(String networkErrText){
        Log.d(TAG,"showNetworkErrLayout");
        setState(State.NETWORKERR,networkErrText);
    }

//    //显示有网连接失败的页面
//    public void showConnectErrLayout(){
//        Log.d(TAG,"showConnectErrLayout");
//        setState(State.CONNECTERR,null);
//    }
//    public void showConnectErrLayout(String connectErrText){
//        Log.d(TAG,"showConnectErrLayout");
//        setState(State.CONNECTERR,connectErrText);
//    }

    //显示空的页面
    public void showEmptyLayout(){
        Log.d(TAG,"showEmptyLayout");
        setState(State.EMPTY,null);
    }
    public void showEmptyLayout(String emptyText){
        Log.d(TAG,"showEmptyLayout");
        setState(State.EMPTY,emptyText);
    }

    //点击重试的回调
    private RetryClickListener mRetryClickListener;

    public interface RetryClickListener extends OnClickListener {
    }

    public void setRetryOnclickListener(RetryClickListener listener) {
        mRetryClickListener = listener;
    }

    //-----------------------------------------------------------------

    private ViewStub mConnectErrVs;
    private ViewStub mLoadingVs;
    private ViewStub mNetworkErrVs;
    private ViewStub mEmptyVs;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
          initView(mContext);
    }

    private void initView(Context context) {
        mRoot= LayoutInflater.from(context).inflate(R.layout.common_network_state_view, this,false);
        //加载第一个子孩子即（成功加载时候的页面）
        mSucceedLayout = getChildAt(0);
        addView(mRoot);

        if(mNetworkErrCustomLayout!=null)
            addView(mNetworkErrCustomLayout);
        if(mloadingCustomLayout!=null)
            addView(mloadingCustomLayout);

        if(mEmptyCustomLayout!=null)
            addView(mEmptyCustomLayout);
        if(mConnectErrCustomLayout!=null)
            addView(mConnectErrCustomLayout);

        setState(mState,null);
    }

    /*
    *显示根据state
    *@author luhaisheng
    *@time 2016/6/4 14:38
    */
    private void setState(State state,String text) {
        mState = state;
        //成功
        if(mSucceedLayout != null)
            mSucceedLayout.setVisibility(state == State.SUCCEED ? View.VISIBLE : View.GONE);

        //加载页面
        if(mloadingCustomLayout!=null){
            mloadingCustomLayout.setVisibility(state== State.LOADING? View.VISIBLE: View.GONE);
        }else{
            if(mLoadingRl!=null)
                mLoadingRl.setVisibility(state== State.LOADING? View.VISIBLE: View.GONE);
            else{
                if(state== State.LOADING)
                initLoadingRl(text);
            }
        }

        //没有网络的页面
        if(mNetworkErrCustomLayout!=null){
            mNetworkErrCustomLayout.setVisibility(state== State.NETWORKERR? View.VISIBLE: View.GONE);
        }else{
            if(mNetWorkErrTv!=null)
                mNetWorkErrTv.setVisibility(state== State.NETWORKERR? View.VISIBLE: View.GONE);
            else{
                if(state== State.NETWORKERR)
                initNetWorkErrTv(text);
            }

        }

//        //网络连接失败页面
//        if(mConnectErrCustomLayout!=null){
//            mConnectErrCustomLayout.setVisibility(state== State.CONNECTERR? View.VISIBLE: View.GONE);
//        }else{
//            if(mConnectErrRl!=null)
//                mConnectErrRl.setVisibility(state== State.CONNECTERR? View.VISIBLE: View.GONE);
//            else{
//                if(state== State.CONNECTERR)
//                initConnectErrRl(text);
//            }
//
//        }
        //空页面
        if(mEmptyCustomLayout!=null){
            mEmptyCustomLayout.setVisibility(state== State.EMPTY? View.VISIBLE: View.GONE);
        }else{
            if(mEmptyTv!=null)
                mEmptyTv.setVisibility(state== State.EMPTY? View.VISIBLE: View.GONE);
            else{
                if(state== State.EMPTY)
                initEmptyTv(text);
            }
        }
    }

    /*
    *初始化自己的空页面
    *@author luhaisheng
    *@time 2016/6/4 11:14
    */
    private void initEmptyTv(String emptyText) {
        mEmptyVs = (ViewStub)mRoot.findViewById(R.id.vb_empty);
        mEmptyTv = (TextView) mEmptyVs.inflate();
        //加载用户自定义的文本
        if(mEmptyText!=null)
            mEmptyTv.setText(mEmptyText);
        //加载用户自定义的背景色
        if(mNullThemecolor!=-1)
            mEmptyTv.setBackgroundColor(mNullThemecolor);

        if(emptyText!=null)
            mEmptyTv.setText(emptyText);


    }


    /*
    *初始化自己的网络连接失败的页面
    *@author luhaisheng
    *@time 2016/6/4 10:45
    */
//    private void initConnectErrRl(String connectErrText) {
//        mConnectErrVs = (ViewStub)mRoot.findViewById(R.id.vb_connect_err);
//        mConnectErrRl = (RelativeLayout) mConnectErrVs.inflate();
//        //设置用户自定义的网络连接失败的文本
//        if(mConnectErrText!=null){
//            TextView connectErrTv= (TextView) mConnectErrRl.findViewById(R.id.tv_connect_err);
//            connectErrTv.setText(mConnectErrText);
//        }
//        //设置用户自定义的网络连接失败的图片
//        if(mConnectErrDrawableId!=-1){
//            ImageView connectErrIv= (ImageView) mConnectErrRl.findViewById(R.id.iv_connect);
//            connectErrIv.setImageResource(mConnectErrDrawableId);
//        }
//        //设置用户自定义的网络连接失败的背景色
//        if(mConnecterrThemecolor!=-1){
//            mConnectErrRl.setBackgroundColor(mConnecterrThemecolor);
//        }
//        //设置点击重试的回调接口
//        if (mRetryClickListener != null) {
//            mConnectErrRl.findViewById(R.id.btn_retry).setOnClickListener(mRetryClickListener);
//        }
//        if(connectErrText!=null){
//            TextView connectErrTv= (TextView) mConnectErrRl.findViewById(R.id.tv_connect_err);
//            connectErrTv.setText(connectErrText);
//        }
//
//    }


    /*
    *初始化自己的无网络状态的页面
    *@author luhaisheng
    *@time 2016/6/4 10:41
    */
    private void initNetWorkErrTv(String networkErrText) {
        mNetworkErrVs = (ViewStub)mRoot.findViewById(R.id.vb_network_err);
        mNetWorkErrTv= (TextView) mNetworkErrVs.inflate();
        //加载用户自定义的文本
        if(mNetworkErrText!=null)
            mNetWorkErrTv.setText(mNetworkErrText);
        //加载用户自定义的背景色
        if(mNetworkerrhemecolor!=-1)
            mNetWorkErrTv.setBackgroundColor(mNetworkerrhemecolor);
        if(networkErrText!=null)
            mNetWorkErrTv.setText(networkErrText);
    }

    /*
    *初始化自己的Loading页面并且显示出来
    *@author luhaisheng
    *@time 2016/6/4 10:17
    */
    private void initLoadingRl(String loadingText) {
        mLoadingVs = (ViewStub) mRoot.findViewById(R.id.vb_loading);
        mLoadingRl= (RelativeLayout) mLoadingVs.inflate();
        //设置用户自定义的progressBar
        if(mLoadingDrawableId!=-1){
            mProgressBar = (ProgressBar) mLoadingRl.findViewById(R.id.pb_loading);
            mProgressBar.setIndeterminateDrawable(getResources().getDrawable(mLoadingDrawableId));
        }
        //设置用户自定义的加载文本
        if(mLoadingText!=null){
            mLoadingTv = (TextView) mLoadingRl.findViewById(R.id. tv_loading);
            mLoadingTv.setText(mLoadingText);
        }
        //设置用户自定义的背景颜色
        if(mLoadingThemecolor!=-1){
            mLoadingRl.setBackgroundColor(mLoadingThemecolor);
        }

        if(loadingText!=null){
            mLoadingTv = (TextView) mLoadingRl.findViewById(R.id. tv_loading);
            mLoadingTv.setText(loadingText);
        }
    }

    /*
    *
    *@author luhaisheng
    *@time 2016/6/3 23:29
    */
    private View hasValueToView(Context context, TypedArray typedArray, int index) {
        //空页面
        if(typedArray.hasValue(index)){
            int resourceId = typedArray.getResourceId(index, -1);
            if(resourceId!=-1) {
                View view = LayoutInflater.from(context).inflate(resourceId, null);
                return view;
            }
        }
        return null;
    }

    /*
    *
    *@author luhaisheng
    *@time 2016/6/3 22:57
    */
    public enum State {
    SUCCEED(0), LOADING(1), NETWORKERR(2), EMPTY(3);
        int mValue;
        static State fromValue(int value) {
            State[] values = State.values();
            return values[value];
        }
        State(int value) {
            mValue = value;
        }
        public int getValue() {
            return mValue;
        }
    }

}
