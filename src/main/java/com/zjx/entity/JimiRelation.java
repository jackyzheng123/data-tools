package com.zjx.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Description 几米关联表导入DTO
 * @Author Carson Cheng
 * @Date 2019/10/29 11:23
 * @Version V1.0
 **/
@Document(collection = "jimiRelation")
@Data
public class JimiRelation implements Serializable {

    /**
     * _id
     */
    private ObjectId _id;

    /**
     * 产品编码
     */
    @Field("productCode")
    private String productCode;

    /**
     * 公司名称
     */
    @Field("comName")
    private String comName;

    /**
     * 产品ID
     */
    @Field("productId")
    private String productId;

    /**
     * SN号
     */
    @Field("snNo")
    private String snNo;

    /**
     * SIM号
     */
    @Field("simNo")
    private String simNo;

    /**
     * ICCID号
     */
    @Field("iccidNo")
    private String iccidNo;

    /**
     * MAC地址
     */
    @Field("macAddr")
    private String macAddr;

    /**
     * 设备号
     */
    @Field("machineNo")
    private String machineNo;

    /**
     * VIP号
     */
    @Field("vipNo")
    private String vipNo;

    /**
     * BAT号
     */
    @Field("batNo")
    private String batNo;

    /**
     * 第二个锁ID
     */
    @Field("secondLockId")
    private String secondLockId;

    /**
     * 机器代码
     */
    @Field("machineCode")
    private String machineCode;

    /**
     * IMSI号
     */
    @Field("imsiNo")
    private String imsiNo;

    /**
     * RFID号
     */
    @Field("rfidNo")
    private String rfidNo;

    /**
     * 订单号
     */
    @Field("orderNo")
    private String orderNo;

    /**
     * 上传时间
     */
    @Field("uploadDate")
    private String uploadDate;

    /**
     * SIM卡激活时间
     */
    @Field("simActiveDate")
    private String simActiveDate;

    /**
     * 网标号
     */
    @Field("netNo")
    private String netNo;

    /**
     * 第二个IMEI号
     */
    @Field("secondIMEI")
    private String secondIMEI;

}
