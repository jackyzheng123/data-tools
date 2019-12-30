package com.zjx.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/10/30 9:43
 * @Version V1.0
 **/
public class EasyExcelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EasyExcelUtils.class);

    public static AtomicInteger count = new AtomicInteger(0);

    /**
     * 指定阀值
     *
     * @param consumer
     * @param threshold
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer, int threshold) {
        return new AnalysisEventListener<T>() {

            private List<T> list = new LinkedList<T>();

            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                LOGGER.info("解析到一条数据:{}", JSON.toJSONString(t));
                if (t != null) {
                    count.incrementAndGet();
                    list.add(t);
                }
                if (list.size() == threshold) {
                    consumer.accept(list);
                    list.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                if (list.size() > 0) {
                    consumer.accept(list);
                    list.clear();
                }
                LOGGER.info("所有数据解析完成！");
            }
        };
    }

    /**
     * 不指定阀值, 默认阀值是10
     *
     * @param consumer
     * @param <T>
     * @return
     */
    public static <T> AnalysisEventListener<T> getListener(Consumer<List<T>> consumer) {
        return getListener(consumer, 10);
    }
}
