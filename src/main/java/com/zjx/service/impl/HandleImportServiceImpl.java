package com.zjx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.base.Stopwatch;
import com.zjx.dto.JimiCartonDTO;
import com.zjx.dto.JimiRelationDTO;
import com.zjx.entity.JimiCarton;
import com.zjx.entity.JimiRelation;
import com.zjx.service.IHandleImportService;
import com.zjx.utils.EasyExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/10/29 11:07
 * @Version V1.0
 **/
@Service
public class HandleImportServiceImpl implements IHandleImportService {

    private static final Logger log = LoggerFactory.getLogger(HandleImportServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String JIMI_HANDLE_ID = "88.136.66/";

    private static final String JIMI_COM_NAME = "深圳市几米物联有限公司";

    @Value("${spek.file.path.prefix}")
    private String filePath;

    /**
     * 卡通箱标识导入
     *
     * @param file
     * @param sheetNo
     * @return
     */
    @Override
    public String importHandleCarton(MultipartFile file, Integer sheetNo) {

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

        String msg = "";
        if (sheetNo == null) {
            // 读取全部sheet
            msg = readExcel(stopwatch, file, JimiCartonDTO.class, listener);
        } else {
            // 读取单个sheet
            try {
                // 默认读取第一个sheet，也可以指定，如：sheet(1)读取第二个sheet
                EasyExcel.read(file.getInputStream(), JimiCartonDTO.class, listener).sheet(sheetNo).doRead();
            } catch (IOException e) {
                // 重置计数器
                EasyExcelUtils.count.set(0);
                log.error("导入异常，" + e.getMessage(), e);
            }
            msg = String.format("成功导入数据%s条，耗时%s秒", EasyExcelUtils.count, stopwatch.elapsed(TimeUnit.SECONDS));
            log.info(msg);
            // 重置计数器
            EasyExcelUtils.count.set(0);
        }
        return msg;
    }

    /**
     * 关联表标识导入
     *
     * @param file
     * @param sheetNo
     * @return
     */
    @Override
    public String importHandleRelation(MultipartFile file, Integer sheetNo) {

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

        String msg = "";
        if (sheetNo == null) {
            // 读取全部sheet
            msg = readExcel(stopwatch, file, JimiRelationDTO.class, listener);
        } else {
            // 读取单个sheet
            try {
                // 默认读取第一个sheet，也可以指定，如：sheet(1)读取第二个sheet
                EasyExcel.read(file.getInputStream(), JimiRelationDTO.class, listener).sheet(sheetNo).doRead();
            } catch (IOException e) {
                // 重置计数器
                EasyExcelUtils.count.set(0);
                log.error("导入异常，" + e.getMessage(), e);
            }
            msg = String.format("成功导入数据%s条，耗时%s秒", EasyExcelUtils.count, stopwatch.elapsed(TimeUnit.SECONDS));
            log.info(msg);
            // 重置计数器
            EasyExcelUtils.count.set(0);
        }
        return msg;
    }

    /**
     * 读取全部sheet
     *
     * @param stopwatch
     * @param file
     * @param clazz
     * @param listener
     * @return 响应信息
     */
    public String readExcel(Stopwatch stopwatch, MultipartFile file, Class<?> clazz, AnalysisEventListener<?> listener) {
        // 存储临时文件
        File savePathDir = new File(filePath);
        if (!savePathDir.exists()) {
            savePathDir.mkdirs();
        }
        String fileName = filePath + "/" + file.getOriginalFilename();
        File tempFile = new File(fileName);

        try {
            file.transferTo(tempFile);

            // 读取全部sheet 最新版本2.1.0-beta4
            EasyExcel.read(fileName, clazz, listener).doReadAll();

        } catch (IOException e) {
            // 重置计数器
            EasyExcelUtils.count.set(0);
            log.error("标识导入失败，" + e.getMessage(), e);
        } finally {
            // 删除临时文件
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }

        String msg = String.format("成功导入数据%s条，耗时%s秒", EasyExcelUtils.count, stopwatch.elapsed(TimeUnit.SECONDS));
        log.info(msg);
        // 重置计数器
        EasyExcelUtils.count.set(0);
        return msg;
    }

}
