package com.zjx.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2019/10/29 11:06
 * @Version V1.0
 **/
public interface IHandleImportService {

    /**
     * 卡通箱标识导入
     *
     * @param file
     * @param sheetNo
     * @return
     */
    String importHandleCarton(MultipartFile file, Integer sheetNo);

    /**
     * 关联表标识导入
     *
     * @param file
     * @param sheetNo
     * @return
     */
    String importHandleRelation(MultipartFile file, Integer sheetNo);
}
