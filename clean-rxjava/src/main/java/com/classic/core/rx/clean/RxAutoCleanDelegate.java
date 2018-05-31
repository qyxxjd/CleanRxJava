package com.classic.core.rx.clean;

import android.support.annotation.NonNull;

import com.classic.core.event.ActivityEvent;
import com.classic.core.event.FragmentEvent;
import com.classic.core.rx.RxUtil;
import com.classic.core.rx.lifecycle.LifecycleListener;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 自动清理实现类
 *
 * @author classic
 * @version v1.0, 2018/4/22 下午4:48
 */
public final class RxAutoCleanDelegate implements AutoClean, LifecycleListener {

    private final BehaviorSubject<Integer> mBehaviorSubject = BehaviorSubject.create();
    private CompositeDisposable mCompositeDisposable;

    /**
     * 生命周期改变回调方法
     *
     * @param event LifecycleEvent
     */
    @Override
    public void onLifecycleChange(int event) {
        mBehaviorSubject.onNext(event);
        if (event == ActivityEvent.DESTROY || event == FragmentEvent.DESTROY_VIEW) {
            clear();
        }
    }

    /**
     * 绑定一个 Activity、Fragment 的生命周期，自动释放资源
     * <br/>
     * 例如：网络请求时绑定{@link ActivityEvent#STOP} 或 {@link FragmentEvent#STOP},
     * onStop()时会自动取消网络请求.
     *
     * @param event 事件类型
     * @see ActivityEvent
     * @see FragmentEvent
     */
    @Override
    public <Type> ObservableTransformer<Type, Type> bindEvent(final int event) {
        final Observable<Integer> observable = mBehaviorSubject.filter(new InnerPredicate(event))
                                                               .take(1);
        return new InnerTransformer<>(observable);
    }

    /**
     * 绑定一个 Activity、Fragment 的生命周期，自动释放资源
     * <br/>
     * 例如：网络请求时绑定{@link ActivityEvent#STOP} 或 {@link FragmentEvent#STOP},
     * onStop()时会自动取消网络请求.
     *
     * @param event 事件类型
     * @see ActivityEvent
     * @see FragmentEvent
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public <Type> FlowableTransformer<Type, Type> bindEventWithFlowable(final int event) {
        final Flowable<Integer> flowable = mBehaviorSubject.toFlowable(BackpressureStrategy.LATEST)
                                                           .filter(new InnerPredicate(event))
                                                           .take(1);
        return new InnerFlowableTransformer<>(flowable);
    }

    /**
     * 回收Disposable,
     * 默认 {@link ActivityEvent#DESTROY} 或 {@link FragmentEvent#DESTROY_VIEW} 统一释放资源
     *
     * @param disposable Disposable
     */
    @Override
    public void recycle(@NonNull Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 清理资源
     */
    @Override
    public void clear() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 清理资源
     *
     * @param disposables Disposable
     */
    @Override
    public void clear(Disposable... disposables) {
        RxUtil.clear(disposables);
    }


    private static final class InnerPredicate implements Predicate<Integer> {
        private final int mEvent;

        InnerPredicate(int event) {
            mEvent = event;
        }

        @Override
        public boolean test(Integer integer) {
            return integer == mEvent;
        }
    }
    private static final class InnerTransformer<Type> implements ObservableTransformer<Type, Type> {
        private final Observable<Integer> mObservable;

        InnerTransformer(Observable<Integer> observable) {
            mObservable = observable;
        }

        @Override
        public ObservableSource<Type> apply(Observable<Type> upstream) {
            return upstream.takeUntil(mObservable);
        }
    }
    @SuppressWarnings("SpellCheckingInspection")
    private static final class InnerFlowableTransformer<Type> implements FlowableTransformer<Type, Type> {
        private final Flowable<Integer> mFlowable;

        InnerFlowableTransformer(Flowable<Integer> flowable) {
            mFlowable = flowable;
        }

        @Override
        public Publisher<Type> apply(Flowable<Type> upstream) {
            return upstream.takeUntil(mFlowable);
        }
    }
}
