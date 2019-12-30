package com.zjx.controller;

import com.zjx.service.IHandleImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description 标识导入控制类
 * @Author Carson Cheng
 * @Date 2019/10/29 10:58
 * @Version V1.0
 **/
@Api(tags = {"标识导入控制类"})
@RestController
@RequestMapping("/handle/import")
public class HandleImportController {

    @Autowired
    private IHandleImportService handleImportService;

    /**
     * 几米卡通箱--标识导入
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "几米卡通箱--标识导入")
    @PostMapping("/carton")
    public String importHandleCarton(@RequestParam("file") MultipartFile file,
                                     @ApiParam(value = "Excel工作表序号，索引从0开始，即0读取第一个工作薄，如此类推，不传默认读取所有工作薄") @RequestParam(name = "sheetNo", required = false) Integer sheetNo) {
        return handleImportService.importHandleCarton(file, sheetNo);
    }

    /**
     * 几米关联表--标识导入
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "几米关联表--标识导入")
    @PostMapping("/relation")
    public String importHandleRelation(@RequestParam("file") MultipartFile file,
                                       @ApiParam(value = "Excel工作表序号，索引从0开始，即0读取第一个工作薄，如此类推，不传默认读取所有工作薄") @RequestParam(name = "sheetNo", required = false) Integer sheetNo) {
        return handleImportService.importHandleRelation(file, sheetNo);
    }
}
