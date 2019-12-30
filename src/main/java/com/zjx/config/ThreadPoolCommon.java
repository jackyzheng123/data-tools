package com.zjx.config;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wuya
 * @description
 * @Date 2019/8/15
 */
public class ThreadPoolCommon {

    /**
     * cpu个数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池运行的核心线程数
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 线程池最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 线程空闲后的存活时长
     */
    private static final int KEEP_ALIVE = 3;

    /**
     * 采用ThreadFactory管理线程
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        /**
         * 线程index
         */
        private final AtomicInteger mCount = new AtomicInteger(1);

        /**
         * {@inheritDoc}
         */
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Framework lib Thread #" + mCount.getAndIncrement());
        }
    };

    /**
     * 提交到线程池的Runnable队列
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(8000);

    /**
     * 线程池
     */
    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
        KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    /**
     * 获取线程池
     * 
     * @return 线程池
     */
    public static Executor getExecutor() {
        return sExecutor;
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        if (!sExecutor.isShutdown()) {
            sExecutor.shutdown();
        }
    }
}
