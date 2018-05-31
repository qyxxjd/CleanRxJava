package com.classic.core.http.function;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 带重试机制的Function
 *
 * <pre>
 *     示例：重试5次，重试延迟间隔1秒
 *    observable.retryWhen(new RetryFunc(5, 1, TimeUnit.SECONDS))
 *              .subscribeOn(Schedulers.computation())
 *              .observeOn(Schedulers.io())
 *              .subscribe(...);
 * </pre>
 *
 * @author classic
 * @version v1.0, 2018/05/30 上午11:38
 */
@SuppressWarnings("unused")
public class RetryFunc implements Function<Observable<? extends Throwable>, ObservableSource<?>> {

    private final TimeUnit mTimeUnit;
    private final int mMaxRetryCount;
    private final int mRetryDelay;
    private int mRetryCount;

    public RetryFunc(int maxRetries, int retryDelayMillis, TimeUnit timeUnit) {
        this.mMaxRetryCount = maxRetries;
        this.mRetryDelay = retryDelayMillis;
        this.mTimeUnit = timeUnit;
        mRetryCount = 0;
    }

    @Override
    public ObservableSource<?> apply(final Observable<? extends Throwable> observable) {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) {
                if (++mRetryCount < mMaxRetryCount) {
                    return Observable.timer(mRetryDelay, mTimeUnit);
                }
                return Observable.error(throwable);
            }
        });
    }
}
