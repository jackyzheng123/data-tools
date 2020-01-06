package com.zjx.controller;

import com.zjx.service.IExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description Easy Excel导出
 * @Author Carson Cheng
 * @Date 2020/1/6 9:41
 * @Version V1.0
 **/
@Api(tags = {"Easy Excel导出测试控制类"})
@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private IExportService exportService;

    @ApiOperation(value = "文件下载（失败了会返回一个有部分数据的Excel）")
    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        exportService.download(response);
    }


    @ApiOperation(value = "文件下载并且失败的时候返回json（默认失败了会返回一个有部分数据的Excel）")
    @GetMapping("/downloadFailedUsingJson")
    public void downloadFailedUsingJson(HttpServletResponse response) {
        exportService.downloadFailedUsingJson(response);
    }

}
