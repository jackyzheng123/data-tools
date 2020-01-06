package com.zjx.mapper;

import com.zjx.dto.JimiCartonDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/1/6 14:11
 * @Version V1.0
 **/
@Repository
public interface ExportMapper {

    /**
     * 查询卡通箱数据
     * @return
     */
    List<JimiCartonDTO> queryJimiCartonList();

    /**
     * 分页去数据库查询数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<JimiCartonDTO> queryJimiCartonPage(int pageNum, int pageSize);
}
