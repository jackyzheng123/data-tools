package com.zjx.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 几米卡通箱导入DTO
 * @Author Carson Cheng
 * @Date 2019/10/29 11:23
 * @Version V1.0
 **/
@Data
public class JimiCartonDTO implements Serializable {

    /**
     * 产品ID
     */
    @ExcelProperty(value = "ID")
    private String productId;

    /**
     * 箱号
     */
    @ExcelProperty(value = "箱号")
    private String boxNo;

    /**
     * IMEI号
     */
    @ExcelProperty(value = "IMEI号")
    private String imeiNo;

    /**
     * 订单号
     */
    @ExcelProperty(value = "订单号")
    private String orderNo;

    /**
     * 机型
     */
    @ExcelProperty(value = "机型")
    private String machineModel;

    /**
     * 软件版本
     */
    @ExcelProperty(value = "软件版本")
    private String version;

    /**
     * 产品码
     */
    @ExcelProperty(value = "产品码")
    private String productNo;

    /**
     * 颜色
     */
    @ExcelProperty(value = "颜色")
    private String color;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private String number;

    /**
     * 重量
     */
    @ExcelProperty(value = "重量")
    private String weight;

    /**
     * 日期
     */
    @ExcelProperty(value = "日期")
    @DateTimeFormat("yyyy.MM.dd")
    private Date date;

    /**
     * IMEI前缀
     */
    @ExcelProperty(value = "IMEI前缀")
    private String imeiPrefix;

    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称")
    private String comName;

    /**
     * 操作人
     */
    @ExcelProperty(value = "操作人")
    private String oper;

    /**
     * 上传时间
     */
    @ExcelProperty(value = "上传时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;

    /**
     * 贴纸备注信息
     */
    @ExcelProperty(value = "贴纸备注信息")
    private String remark;

    /**
     * 芯片id号
     */
    @ExcelProperty(value = "芯片id号")
    private String rfidNo;

    /**
     * 卡通箱第二个号码
     */
    @ExcelProperty(value = "卡通箱第二个号码")
    private String secondNo;

    /**
     * 卡通贴二维码信息
     */
    @ExcelProperty(value = "卡通贴二维码信息")
    private String secondTie;

    /**
     * 检查箱号信息
     */
    @ExcelProperty(value = "检查箱号信息")
    private String checkInfo;

    /**
     * PC端信息
     */
    @ExcelProperty(value = "PC端信息")
    private String pcInfo;

    /**
     * 产品编码
     */
    @ExcelIgnore
    private String productCode;

}

