package com.classic.core.rx;

import org.reactivestreams.Publisher;

import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换的封装
 *
 * @author classic
 * @version v1.0, 2018/4/22 下午4:48
 */
@SuppressWarnings({"unused", "WeakerAccess", "unchecked"})
public final class RxTransformer {

    private RxTransformer() {
        // No instance.
    }

    public static class Observable {

        public static final ObservableTransformer THREAD = new ObservableTransformer() {
            @Override
            public ObservableSource apply(@NonNull io.reactivex.Observable upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(Schedulers.newThread());
            }
        };

        public static final ObservableTransformer THREAD_ON_UI = new ObservableTransformer() {
            @Override public ObservableSource apply(@NonNull io.reactivex.Observable upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final ObservableTransformer IO = new ObservableTransformer() {
            @Override public ObservableSource apply(@NonNull io.reactivex.Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final ObservableTransformer IO_ON_UI = new ObservableTransformer() {
            @Override public ObservableSource apply(@NonNull io.reactivex.Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final ObservableTransformer COMPUTATION = new ObservableTransformer() {
            @Override public ObservableSource apply(@NonNull io.reactivex.Observable upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final ObservableTransformer COMPUTATION_ON_UI = new ObservableTransformer() {
            @Override public ObservableSource apply(@NonNull io.reactivex.Observable upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static class Flowable {

        public static final FlowableTransformer THREAD = new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull io.reactivex.Flowable upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(Schedulers.newThread());
            }
        };

        public static final FlowableTransformer THREAD_ON_UI = new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull io.reactivex.Flowable upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final FlowableTransformer IO = new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull io.reactivex.Flowable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final FlowableTransformer IO_ON_UI = new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull io.reactivex.Flowable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final FlowableTransformer COMPUTATION = new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull io.reactivex.Flowable upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final FlowableTransformer COMPUTATION_ON_UI = new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull io.reactivex.Flowable upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static class Single {

        public static final SingleTransformer THREAD = new SingleTransformer() {
            @Override
            public SingleSource apply(io.reactivex.Single upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(Schedulers.newThread());
            }
        };

        public static final SingleTransformer THREAD_ON_UI = new SingleTransformer() {
            @Override public SingleSource apply(@NonNull io.reactivex.Single upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final SingleTransformer IO = new SingleTransformer() {
            @Override public SingleSource apply(@NonNull io.reactivex.Single upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final SingleTransformer IO_ON_UI = new SingleTransformer() {
            @Override public SingleSource apply(@NonNull io.reactivex.Single upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final SingleTransformer COMPUTATION = new SingleTransformer() {
            @Override public SingleSource apply(@NonNull io.reactivex.Single upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final SingleTransformer COMPUTATION_ON_UI = new SingleTransformer() {
            @Override public SingleSource apply(@NonNull io.reactivex.Single upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static class Completable {

        public static final CompletableTransformer THREAD = new CompletableTransformer() {
            @Override
            public CompletableSource apply(io.reactivex.Completable upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(Schedulers.newThread());
            }
        };

        public static final CompletableTransformer THREAD_ON_UI = new CompletableTransformer() {
            @Override public CompletableSource apply(io.reactivex.Completable upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final CompletableTransformer IO = new CompletableTransformer() {
            @Override public CompletableSource apply(io.reactivex.Completable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final CompletableTransformer IO_ON_UI = new CompletableTransformer() {
            @Override public CompletableSource apply(io.reactivex.Completable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final CompletableTransformer COMPUTATION = new CompletableTransformer() {
            @Override public CompletableSource apply(io.reactivex.Completable upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final CompletableTransformer COMPUTATION_ON_UI = new CompletableTransformer() {
            @Override public CompletableSource apply(io.reactivex.Completable upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static class Maybe {

        public static final MaybeTransformer THREAD = new MaybeTransformer() {
            @Override
            public MaybeSource apply(io.reactivex.Maybe upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(Schedulers.newThread());
            }
        };

        public static final MaybeTransformer THREAD_ON_UI = new MaybeTransformer() {
            @Override public MaybeSource apply(io.reactivex.Maybe upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                               .unsubscribeOn(Schedulers.newThread())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final MaybeTransformer IO = new MaybeTransformer() {
            @Override public MaybeSource apply(io.reactivex.Maybe upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final MaybeTransformer IO_ON_UI = new MaybeTransformer() {
            @Override public MaybeSource apply(io.reactivex.Maybe upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };

        public static final MaybeTransformer COMPUTATION = new MaybeTransformer() {
            @Override public MaybeSource apply(io.reactivex.Maybe upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(Schedulers.io());
            }
        };

        public static final MaybeTransformer COMPUTATION_ON_UI = new MaybeTransformer() {
            @Override public MaybeSource apply(io.reactivex.Maybe upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                               .unsubscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 应用一种线程切换策略
     */
    public static <T> ObservableTransformer<T, T> applySchedulers(ObservableTransformer transformer) {
        return (ObservableTransformer<T, T>)transformer;
    }

    /**
     * 应用一种线程切换策略
     */
    public static <T> FlowableTransformer<T, T> applySchedulers(FlowableTransformer transformer) {
        return (FlowableTransformer<T, T>)transformer;
    }

    /**
     * 应用一种线程切换策略
     */
    public static <T> SingleTransformer<T, T> applySchedulers(SingleTransformer transformer) {
        return (SingleTransformer<T, T>)transformer;
    }

    /**
     * 应用一种线程切换策略
     */
    public static <T> MaybeTransformer<T, T> applySchedulers(MaybeTransformer transformer) {
        return (MaybeTransformer<T, T>)transformer;
    }
}
