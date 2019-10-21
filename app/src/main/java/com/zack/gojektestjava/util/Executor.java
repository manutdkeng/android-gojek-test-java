package com.zack.gojektestjava.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Use Executor as following way.
 *
 * Executor.IOThread(() ->  // DB query );
 */
public class Executor {

    public static void IOThread(Runnable t) {
        ExecutorService IO_EXECUTOR = Executors.newSingleThreadExecutor();
        IO_EXECUTOR.execute(t);
    }
}
