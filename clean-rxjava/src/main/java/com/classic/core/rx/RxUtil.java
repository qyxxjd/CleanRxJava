package com.classic.core.rx;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * RxJava工具类
 *
 * @author classic
 * @version v1.0, 2018/4/22 下午4:48
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class RxUtil {

    private RxUtil() {
        // No instance.
    }

    /**
     * 运行一个定时任务在子线程
     *
     * @param delay  延迟时间，单位：毫秒
     * @param action 定时任务回调
     * @return Disposable
     */
    public static Disposable time(long delay, @NonNull Action action) {
        return time(delay, TimeUnit.MILLISECONDS, action);
    }

    /**
     * 运行一个定时任务在子线程
     *
     * @param delay  延迟时间
     * @param unit   单位
     * @param action 定时任务回调
     * @return Disposable
     */
    public static Disposable time(long delay, TimeUnit unit, @NonNull Action action) {
        return Completable.timer(delay, unit)
                          .compose(RxTransformer.Completable.COMPUTATION)
                          .subscribe(action);
    }

    /**
     * 运行一个定时任务在UI线程
     *
     * @param delay  延迟时间
     * @param unit   单位
     * @param action 定时任务回调
     * @return Disposable
     */
    public static Disposable timeOnUI(long delay, TimeUnit unit, @NonNull Action action) {
        return Completable.timer(delay, unit)
                          .compose(RxTransformer.Completable.COMPUTATION_ON_UI)
                          .subscribe(action);
    }

    /**
     * 运行一个轮询任务在子线程
     *
     * @param interval 轮询间隔，单位：毫秒
     * @param onNext 轮询任务回调
     * @return Disposable
     */
    public static Disposable interval(long interval, @NonNull Consumer<Long> onNext) {
        return interval(interval, TimeUnit.MILLISECONDS, onNext);
    }

    /**
     * 运行一个轮询任务在子线程
     *
     * @param interval 轮询间隔
     * @param unit     单位
     * @param onNext 轮询任务回调
     * @return Disposable
     */
    public static Disposable interval(long interval, TimeUnit unit, @NonNull Consumer<Long> onNext) {
        return interval(0, interval, TimeUnit.MILLISECONDS, onNext);
    }

    /**
     * 运行一个轮询任务在子线程
     *
     * @param initialDelay 第一次执行的延迟时间
     * @param interval 轮询间隔
     * @param unit     单位
     * @param onNext 轮询任务回调
     * @return Disposable
     */
    public static Disposable interval(long initialDelay, long interval, TimeUnit unit,
                                      @NonNull Consumer<Long> onNext) {
        return Observable.interval(initialDelay, interval, unit)
                         .compose(RxTransformer.<Long>applySchedulers(RxTransformer.Observable.COMPUTATION))
                         .subscribe(onNext);
    }

    /**
     * 运行一个任务在子线程
     *
     * @param action 耗时任务
     * @return Disposable
     */
    public static Disposable run(@NonNull final Action action) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
                         @Override
                         public void subscribe(SingleEmitter<Boolean> e) throws Exception {
                             action.run();
                             e.onSuccess(true);
                         }
                     })
                     .compose(RxTransformer.<Boolean>applySchedulers(RxTransformer.Single.IO))
                     .subscribe();
    }

    /**
     * 运行一个任务在UI线程
     *
     * @param backgroundTask 耗时任务
     * @param uiTask UI回调
     * @return Disposable
     */
    public static Disposable runOnUI(@NonNull final Action backgroundTask, @NonNull Action uiTask) {
        return Completable.create(new CompletableOnSubscribe() {
                              @Override
                              public void subscribe(CompletableEmitter e) throws Exception {
                                  backgroundTask.run();
                                  e.onComplete();
                              }
                          })
                          .compose(RxTransformer.Completable.IO_ON_UI)
                          .subscribe(uiTask);
    }

    /**
     * 清理Disposable，释放资源
     *
     * @param disposables Disposable array
     */
    public static void clear(Disposable... disposables) {
        if (null == disposables) {
            return;
        }
        for (Disposable disposable : disposables) {
            if (null != disposable) {
                disposable.dispose();
            }
        }
    }
}
