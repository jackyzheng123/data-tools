package com.zjx.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Description 几米卡通箱导入DTO
 * @Author Carson Cheng
 * @Date 2019/10/29 11:23
 * @Version V1.0
 **/
@Document(collection = "jimiCarton")
@Data
public class JimiCarton implements Serializable {

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
     * 箱号
     */
    @Field("boxNo")
    private String boxNo;

    /**
     * IMEI号
     */
    @Field("imeiNo")
    private String imeiNo;

    /**
     * 订单号
     */
    @Field("orderNo")
    private String orderNo;

    /**
     * 机型
     */
    @Field("machineModel")
    private String machineModel;

    /**
     * 软件版本
     */
    @Field("version")
    private String version;

    /**
     * 产品码
     */
    @Field("productNo")
    private String productNo;

    /**
     * 颜色
     */
    @Field("color")
    private String color;

    /**
     * 数量
     */
    @Field("number")
    private String number;

    /**
     * 日期
     */
    @Field("date")
    private String date;

    /**
     * 上传时间
     */
    @Field("uploadDate")
    private String uploadDate;

    /**
     * 贴纸备注信息
     */
    @Field("remark")
    private String remark;

    /**
     * 芯片id号
     */
    @Field("rfidNo")
    private String rfidNo;

    /**
     * 卡通箱第二个号码
     */
    @Field("secondNo")
    private String secondNo;

}
