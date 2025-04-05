package com.example.intelligenttransportation.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交通违章记录实体类
 */
@Data
public class TrafficViolation {
    
    /**
     * 违章ID
     */
    private Long id;
    
    /**
     * 违章编号
     */
    private String violationCode;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 违章类型
     */
    private String violationType;
    
    /**
     * 违章描述
     */
    private String violationDescription;
    
    /**
     * 违章时间
     */
    private Date violationTime;
    
    /**
     * 违章地点
     */
    private String location;
    
    /**
     * 经度
     */
    private BigDecimal longitude;
    
    /**
     * 纬度
     */
    private BigDecimal latitude;
    
    /**
     * 设备ID
     */
    private Long deviceId;
    
    /**
     * 设备名称（非数据库字段，关联查询）
     */
    private String deviceName;
    
    /**
     * 道路ID
     */
    private Long roadId;
    
    /**
     * 道路名称（非数据库字段，关联查询）
     */
    private String roadName;
    
    /**
     * 证据图片URL
     */
    private String evidenceUrl;
    
    /**
     * 罚款金额
     */
    private BigDecimal penaltyAmount;
    
    /**
     * 扣分
     */
    private Integer penaltyPoints;
    
    /**
     * 状态（0：未处理，1：已处理，2：申诉中，3：已撤销）
     */
    private Integer status;
    
    /**
     * 处理人ID
     */
    private Long handlerId;
    
    /**
     * 处理人姓名（非数据库字段，关联查询）
     */
    private String handlerName;
    
    /**
     * 处理时间
     */
    private Date handleTime;
    
    /**
     * 处理结果
     */
    private String handleResult;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 逻辑删除标志（0：未删除，1：已删除）
     */
    private Integer deleted;
} 