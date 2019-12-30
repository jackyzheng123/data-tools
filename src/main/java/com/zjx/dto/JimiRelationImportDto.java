package com.zjx.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 几米关联表导入DTO
 * @Author Carson Cheng
 * @Date 2019/10/29 11:23
 * @Version V1.0
 **/
@Data
public class JimiRelationImportDto implements Serializable {

    /**
     * 产品ID
     */
    @ExcelProperty(value = "ID")
    private String productId;

    /**
     * IMEI号
     */
    @ExcelProperty(value = "IMEI号")
    private String imeiNo;

    /**
     * SN号
     */
    @ExcelProperty(value = "SN号")
    private String snNo;

    /**
     * SIM号
     */
    @ExcelProperty(value = "SIM号")
    private String simNo;

    /**
     * ICCID号，数据长度超过18位，excel单元格要设置成文本，防止丢位
     */
    @ExcelProperty(value = "ICCID号")
    private String iccidNo;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * MAC地址
     */
    @ExcelProperty(value = "MAC地址")
    private String macAddr;

    /**
     * 设备号
     */
    @ExcelProperty(value = "Equipment 设备号")
    private String machineNo;

    /**
     * VIP号
     */
    @ExcelProperty(value = "VIP号")
    private String vipNo;

    /**
     * BAT号
     */
    @ExcelProperty(value = "BAT号")
    private String batNo;

    /**
     * 第二个锁ID
     */
    @ExcelProperty(value = "第二个锁ID")
    private String secondLockId;

    /**
     * 机器代码
     */
    @ExcelProperty(value = "机器代码")
    private String machineCode;

    /**
     * IMSI号
     */
    @ExcelProperty(value = "IMSI号")
    private String imsiNo;

    /**
     * RFID号
     */
    @ExcelProperty(value = "RFID号")
    private String rfidNo;

    /**
     * 制单
     */
    @ExcelProperty(value = "制单")
    private String sheet;

    /**
     * 上传时间
     */
    @ExcelProperty(value = "测试时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date uploadDate;

    /**
     * SIM卡激活时间
     */
    @ExcelProperty(value = "SIM卡激活时间")
    private String simActiveDate;

    /**
     * 网标号
     */
    @ExcelProperty(value = "网标号")
    private String netNo;

    /**
     * 第二个IMEI号
     */
    @ExcelProperty(value = "第二个IMEI号")
    private String secondIMEI;

    /**
     * 公司名称
     */
    @ExcelIgnore
    private String comName;

    /**
     * 产品编码
     */
    @ExcelIgnore
    private String productCode;

}
