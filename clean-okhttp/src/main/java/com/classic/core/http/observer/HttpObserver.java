package com.classic.core.http.observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;

/**
 * 网络请求回调封装
 *
 * @author classic
 * @version v1.0, 2018/05/30 上午11:38
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class HttpObserver<T> extends DisposableObserver<T> {

    private static final String TAG = "HttpObserver";

    private final Context mAppContext;

    public HttpObserver(@NonNull Context context) {
        this.mAppContext = context.getApplicationContext();
    }

    /**
     * 请求成功回调
     */
    public abstract void onSuccess(T t);

    @Override
    public void onStart() {
        super.onStart();
        if (!isNetworkAvailable(mAppContext)) {
            dispose();
            onNetworkNotConnected();
            onFinish();
        }
    }

    @Override
    public final void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public final void onComplete() {
        onFinish();
    }

    @Override
    public final void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            onSocketTimeout();
        } else {
            onFailure(e);
        }
        onFinish();
    }

    /**
     * 接口调用之前会检测网络，如果无网络执行此回调方法
     */
    public void onNetworkNotConnected() { }


    /**
     * 网络连接超时回调
     */
    public void onSocketTimeout() { }

    /**
     * 请求失败回调
     *
     * @param e 异常信息
     */
    public void onFailure(Throwable e) { }

    /**
     * 不管成功失败，最后都会执行此方法
     */
    public void onFinish() { }

    private boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission")
        NetworkInfo mNetworkInfo = mConnectivityManager != null ? mConnectivityManager.getActiveNetworkInfo() : null;
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }
}