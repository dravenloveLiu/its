package com.example.intelligenttransportation.service;

import com.example.intelligenttransportation.entity.TrafficViolation;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交通违章服务接口
 */
public interface TrafficViolationService {
    
    /**
     * 获取所有违章记录
     * @return 违章记录列表
     */
    List<TrafficViolation> findAll();
    
    /**
     * 分页查询违章记录
     * @param page 页码
     * @param size 每页大小
     * @param params 查询参数
     * @return 违章记录列表
     */
    Map<String, Object> findByPage(int page, int size, Map<String, Object> params);
    
    /**
     * 根据ID查询违章记录
     * @param id 违章ID
     * @return 违章记录
     */
    TrafficViolation findById(Long id);
    
    /**
     * 根据车牌号查询违章记录
     * @param licensePlate 车牌号
     * @return 违章记录列表
     */
    List<TrafficViolation> findByLicensePlate(String licensePlate);
    
    /**
     * 根据时间范围查询违章记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 违章记录列表
     */
    List<TrafficViolation> findByTimeRange(Date startTime, Date endTime);
    
    /**
     * 根据违章类型查询违章记录
     * @param violationType 违章类型
     * @return 违章记录列表
     */
    List<TrafficViolation> findByViolationType(String violationType);
    
    /**
     * 创建违章记录
     * @param violation 违章记录对象
     * @return 创建后的违章记录
     */
    TrafficViolation save(TrafficViolation violation);
    
    /**
     * 更新违章记录
     * @param violation 违章记录对象
     * @return 更新后的违章记录
     */
    TrafficViolation update(TrafficViolation violation);
    
    /**
     * 删除违章记录
     * @param id 违章ID
     */
    void delete(Long id);
    
    /**
     * 处理违章
     * @param id 违章ID
     * @param handlerId 处理人ID
     * @param handleResult 处理结果
     * @return 是否处理成功
     */
    boolean handleViolation(Long id, Long handlerId, String handleResult);
    
    /**
     * 撤销违章
     * @param id 违章ID
     * @param handlerId 处理人ID
     * @param reason 撤销原因
     * @return 是否撤销成功
     */
    boolean revokeViolation(Long id, Long handlerId, String reason);
    
    /**
     * 统计各类违章数量
     * @return 违章类型和数量的映射
     */
    Map<String, Long> countViolationTypes();
    
    /**
     * 统计每月违章数量
     * @param year 年份
     * @return 月份和违章数量的映射
     */
    Map<Integer, Long> countViolationsByMonth(int year);
} 