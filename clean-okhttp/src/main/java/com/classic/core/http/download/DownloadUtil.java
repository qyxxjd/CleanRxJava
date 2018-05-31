package com.classic.core.http.download;

import android.support.annotation.NonNull;

import com.classic.core.http.function.FileFunction;
import com.classic.core.rx.RxTransformer;
import com.classic.core.rx.observer.AutoObserver;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 下载工具类
 *
 * <pre>
 *     使用示例：
 *     DownloadUtil.create(url, file); //不需要回调的情况
 *
 *     DownloadUtil.create(url, file, new ProgressListener() {
 *         {@literal @}Override
 *          public void onProgress(long currentBytes, long totalBytes, boolean isDone) {
 *              // ...
 *          }
 *         {@literal @}Override
 *          public void onSuccess(File file) {
 *              // ...
 *          }
 *         {@literal @}Override
 *          public void onFailure(Throwable throwable) {
 *              // ...
 *          }
 *     });
 * </pre>
 *
 * @author classic
 * @version v1.0, 2018/05/30 上午11:38
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class DownloadUtil {
    private static final int TIMEOUT = 30;
    private static final String SEPARATOR = "/";

    private DownloadUtil() {
        // no instance
    }

    public static void create(@NonNull String url, @NonNull final String file) {
        create(url, new File(file), null);
    }

    public static void create(@NonNull String url, @NonNull final String file,
                              final ProgressListener listener) {
        create(url, new File(file), listener);
    }

    public static void create(@NonNull String url, @NonNull final File file) {
        create(url, file, null);
    }

    public static void create(@NonNull String url, @NonNull final File file,
                              final ProgressListener listener) {
        final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new ProgressInterceptor(listener))
                                                              .retryOnConnectionFailure(true)
                                                              .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                                                              .build();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(getBaseUrl(url))
                                                        .client(client)
                                                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                        .build();
        retrofit.create(DownloadApi.class)
                .download(url)
                .map(new FileFunction(file))
                .compose(RxTransformer.<File>applySchedulers(RxTransformer.Observable.IO))
                .subscribe(new AutoObserver<File>() {
                    @Override
                    public void onNext(File file) {
                        if (null != listener) {
                            listener.onSuccess(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (null != listener) {
                            listener.onFailure(e);
                        }
                        super.onError(e);
                    }
                });
    }

    private static String getBaseUrl(@NonNull String url) {
        // http://.../....
        // https://.../....
        final String[] items = url.split(SEPARATOR);
        return items[0] + SEPARATOR + SEPARATOR + items[2] + SEPARATOR;
    }

}
