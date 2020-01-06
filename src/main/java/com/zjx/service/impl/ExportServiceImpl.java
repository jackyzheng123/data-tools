package com.zjx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.zjx.dto.JimiCartonDTO;
import com.zjx.mapper.ExportMapper;
import com.zjx.service.IExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Carson Cheng
 * @Date 2020/1/6 13:51
 * @Version V1.0
 **/
@Service
@Slf4j
public class ExportServiceImpl implements IExportService {

    @Autowired
    private ExportMapper exportMapper;

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @Override
    public void download(HttpServletResponse response) {

        ExcelWriter excelWriter = null;
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("几米卡通箱导出测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 查询卡通箱数据(测试：导出前1000条)
            //List<JimiCartonDTO> list = exportMapper.queryJimiCartonList();
            // 这里需要设置不关闭流
            //EasyExcel.write(response.getOutputStream(), JimiCartonDTO.class).sheet("模板").doWrite(list);

            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            excelWriter = EasyExcel.write(response.getOutputStream(), JimiCartonDTO.class).build();
            for (int i = 1; i <= 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "卡通箱数据第" + i + "页").build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                int pageSize = 1000;
                int pageNum = (i - 1) * pageSize + 1;
                List<JimiCartonDTO> data = exportMapper.queryJimiCartonPage(pageNum, pageSize);
                excelWriter.write(data, writeSheet);
            }

        } catch (IOException e) {
            log.error("导出异常，" + e.getMessage(), e);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            excelWriter.finish();
        }
    }

    /**
     * 文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）
     *
     * @since 2.1.1
     */
    @Override
    public void downloadFailedUsingJson(HttpServletResponse response) {

        ExcelWriter excelWriter = null;
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("几米卡通箱导出测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 查询卡通箱数据(测试：导出前1000条)
            //List<JimiCartonDTO> list = exportMapper.queryJimiCartonList();
            // 这里需要设置不关闭流
            //EasyExcel.write(response.getOutputStream(), JimiCartonDTO.class).autoCloseStream(Boolean.FALSE).sheet("模板").doWrite(list);

            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
            excelWriter = EasyExcel.write(response.getOutputStream(), JimiCartonDTO.class).build();
            for (int i = 1; i <= 5; i++) {
                // 每次都要创建writeSheet 这里注意必须指定sheetNo
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "卡通箱数据第" + i + "页").build();
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                int pageSize = 1000;
                int pageNum = (i - 1) * pageSize + 1;
                List<JimiCartonDTO> data = exportMapper.queryJimiCartonPage(pageNum, pageSize);
                excelWriter.write(data, writeSheet);
            }

        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            try {
                response.getWriter().println(JSON.toJSONString(map));
            } catch (IOException e1) {
                log.error("下载文件失败," + e1.getMessage(), e1);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            excelWriter.finish();
        }
    }


}
