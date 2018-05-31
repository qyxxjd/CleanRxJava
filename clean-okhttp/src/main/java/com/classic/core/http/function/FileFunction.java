package com.classic.core.http.function;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

/**
 * 文件转换Function
 *
 * @author classic
 * @version v1.0, 2018/05/30 上午11:38
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FileFunction implements Function<ResponseBody, File> {

    private final File mFile;

    public FileFunction(@NonNull String path) {
        this(new File(path));
    }

    public FileFunction(@NonNull File file) {
        mFile = file;
    }

    @Override
    public File apply(@io.reactivex.annotations.NonNull ResponseBody responseBody)
            throws Exception {
        if (!mFile.exists()) {
            if (!mFile.createNewFile()) {
                throw new IOException("File create fail: " + mFile.getAbsolutePath());
            }
        }
        BufferedSink sink = Okio.buffer(Okio.sink(mFile));
        sink.writeAll(responseBody.source());
        sink.close();
        return mFile;
    }
}
