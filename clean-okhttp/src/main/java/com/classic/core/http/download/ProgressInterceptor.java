package com.classic.core.http.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 下载进度拦截器
 *
 * <pre>
 *     {https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java}
 * </pre>
 *
 * @author classic
 * @version v1.0, 2018/05/30 上午11:38
 */
class ProgressInterceptor implements Interceptor {

    private ProgressListener mListener;

    ProgressInterceptor(ProgressListener listener) {
        this.mListener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                               .body(new ProgressResponseBody(originalResponse.body(), mListener))
                               .build();
    }
}
