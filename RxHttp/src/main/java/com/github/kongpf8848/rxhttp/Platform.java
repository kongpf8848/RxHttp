package com.github.kongpf8848.rxhttp;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public static Platform get() {
        return PLATFORM;
    }

    public Executor defaultCallbackExecutor() {
        return null;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {
        }

        return new Java();
    }

    static class Java extends Platform {
        @Override
        public Executor defaultCallbackExecutor() {
            return new Executor() {
                @Override
                public void execute(Runnable command) {
                    Executors.newCachedThreadPool().execute(command);
                }
            };
        }
    }

    static class Android extends Platform {
        @Override
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable r) {
                handler.post(r);
            }
        }
    }
}
