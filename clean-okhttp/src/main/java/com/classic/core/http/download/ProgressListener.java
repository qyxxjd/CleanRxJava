package com.classic.core.http.download;

import java.io.File;

/**
 * 下载进度回调
 *
 * @author classic
 * @version v1.0, 2018/05/30 上午11:38
 */
public interface ProgressListener {

    /**
     * 下载进度回调
     *
     * @param currentBytes 已下载字节数
     * @param totalBytes   总字节数(未知情况,返回-1)
     * @param isDone       是否完成
     */
    void onProgress(long currentBytes, long totalBytes, boolean isDone);

    /**
     * 下载完成
     *
     * @param file 已下载的文件
     */
    void onSuccess(File file);

    /**
     * 下载失败
     *
     * @param throwable 异常信息
     */
    void onFailure(Throwable throwable);
}
