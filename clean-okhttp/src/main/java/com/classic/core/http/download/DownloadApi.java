package com.classic.core.http.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 下载API
 *
 * @author classic
 * @version v1.0, 2018/05/30 上午11:38
 */
public interface DownloadApi {

    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);
}
