package com.zjx.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.base.Stopwatch;
import com.zjx.DataToolsApplication;
import com.zjx.dto.JimiCartonDTO;
import com.zjx.dto.JimiRelationDTO;
import com.zjx.entity.JimiCarton;
import com.zjx.entity.JimiRelation;
import com.zjx.utils.EasyExcelUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description 一次性导入目录下所有excel数据到MongoDB
 * @Author Carson Cheng
 * @Date 2019/10/31 18:11
 * @Version V1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataToolsApplication.class)
public class JimiHandleImportAllTest {

    private static final Logger log = LoggerFactory.getLogger(JimiHandleImportAllTest.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String JIMI_HANDLE_ID = "88.136.66/";

    private static final String JIMI_COM_NAME = "深圳市几米物联有限公司";

    private static final String CARTON_PATH = "E:/zjx/work/几米资料/出货信息/carton/";

    private static final String RELATION_PATH = "E:/zjx/work/几米资料/出货信息/Relation/";

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 17, 10,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

    /**
     * 几米卡通箱数据全部导入
     */
    @Test
    public void importAllJimiCarton() {

        File path = new File(CARTON_PATH);
        if (path.exists()) {
            File[] files = path.listFiles();
            CountDownLatch latch = new CountDownLatch(files.length);

            for (File file : files) {
                if (!(file.getName().endsWith(".xlsx"))) {
                    continue;
                }
                executor.submit(() -> {
                    try {
                        latch.countDown();

                        Stopwatch stopwatch = Stopwatch.createStarted();
                        List<JimiCarton> tempList = new ArrayList<>();
                        AnalysisEventListener<JimiCartonDTO> listener = EasyExcelUtils.getListener(list -> {
                            list.forEach(x -> {
                                JimiCarton entity = new JimiCarton();
                                BeanUtils.copyProperties(x, entity);
                                entity.setProductCode(JIMI_HANDLE_ID + x.getImeiNo());
                                entity.setComName(JIMI_COM_NAME);
                                tempList.add(entity);
                            });
                            // 保存数据
                            mongoTemplate.insertAll(tempList);
                            tempList.clear();
                        }, 3000);

                        EasyExcel.read(file, JimiCartonDTO.class, listener).doReadAll();
                        log.info("线程：{},成功导入数据{}条，耗时{}秒", Thread.currentThread().getName(), EasyExcelUtils.count, stopwatch.elapsed(TimeUnit.SECONDS));
                    } catch (Exception e) {
                        // 重置计数器
                        EasyExcelUtils.count.set(0);
                        log.error("导入异常，" + e.getMessage(), e);
                    }
                });
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }


    /**
     * 几米关联表数据全部导入
     */
    @Test
    public void importAllJimiRelation() {

        File path = new File(RELATION_PATH);
        if (path.exists()) {
            File[] files = path.listFiles();
            CountDownLatch latch = new CountDownLatch(files.length);

            for (File file : files) {
                if (!(file.getName().endsWith(".xlsx"))) {
                    continue;
                }
                executor.submit(() -> {
                    try {
                        latch.countDown();

                        Stopwatch stopwatch = Stopwatch.createStarted();
                        List<JimiRelation> tempList = new ArrayList<>();
                        AnalysisEventListener<JimiRelationDTO> listener = EasyExcelUtils.getListener(list -> {
                            list.forEach(x -> {
                                JimiRelation entity = new JimiRelation();
                                BeanUtils.copyProperties(x, entity);
                                entity.setProductCode(JIMI_HANDLE_ID + x.getImeiNo());
                                entity.setComName(JIMI_COM_NAME);
                                tempList.add(entity);
                            });
                            // 保存数据
                            mongoTemplate.insertAll(tempList);
                            tempList.clear();
                        }, 3000);

                        EasyExcel.read(file, JimiRelationDTO.class, listener).doReadAll();
                        log.info("线程：{},成功导入数据{}条，耗时{}秒", Thread.currentThread().getName(), EasyExcelUtils.count, stopwatch.elapsed(TimeUnit.SECONDS));

                    } catch (Exception e) {
                        // 重置计数器
                        EasyExcelUtils.count.set(0);
                        log.error("导入异常，" + e.getMessage(), e);
                    }
                });
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
