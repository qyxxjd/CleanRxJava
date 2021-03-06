package com.classic.core.rx.observer;

import com.classic.core.rx.RxUtil;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 可以自动销毁的Observer
 *
 * @author Classic
 * @version v1.0, 2018/4/22 下午4:04
 */
@SuppressWarnings("unused")
public class AutoSingleObserver<T> implements SingleObserver<T> {
    private Disposable mDisposable;

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        mDisposable = disposable;
    }

    @Override
    public void onSuccess(T t) {
        RxUtil.clear(mDisposable);
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        RxUtil.clear(mDisposable);
    }
}
