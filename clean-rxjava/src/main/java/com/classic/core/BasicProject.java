package com.classic.core;

import android.content.Context;
import android.support.annotation.NonNull;

import com.classic.core.exception.CrashHandler;
import com.classic.core.exception.DefaultCrashProcess;
import com.classic.core.interfaces.ICrashProcess;

/**
 * 全局配置
 *
 * @author classic
 * @version v1.0, 2018/4/22 下午4:48
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class BasicProject {

    private static volatile BasicProject sInstance;

    private boolean isDebug;

    private BasicProject(Builder builder) {
        isDebug = builder.isDebug;
        if (null != builder.mExceptionHandler) {
            CrashHandler.getInstance(builder.mExceptionHandler);
        }
    }

    public static BasicProject config(@NonNull Builder builder) {
        if (sInstance == null) {
            synchronized (BasicProject.class) {
                if (sInstance == null) {
                    sInstance = builder.build();
                }
            }
        }
        return sInstance;
    }

    public static BasicProject getInstance() {
        return sInstance == null ? config(new Builder()) : sInstance;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public static final class Builder {

        private boolean isDebug;
        private ICrashProcess mExceptionHandler;

        public Builder() { }

        public Builder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public Builder setExceptionHandler(@NonNull Context context) {
            mExceptionHandler = new DefaultCrashProcess(context.getApplicationContext());
            return this;
        }

        public Builder setExceptionHandler(@NonNull ICrashProcess crashProcess) {
            mExceptionHandler = crashProcess;
            return this;
        }

        public BasicProject build() {
            return new BasicProject(this);
        }
    }
}
