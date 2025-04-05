package com.example.intelligenttransportation.mapper;

import com.example.intelligenttransportation.entity.TrafficViolation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交通违章数据访问接口
 */
@Mapper
public interface TrafficViolationMapper {
    
    /**
     * 获取所有违章记录
     * @return 违章记录列表
     */
    List<TrafficViolation> findAll();
    
    /**
     * 分页查询违章记录
     * @param params 查询参数，包含offset, size以及其他过滤条件
     * @return 违章记录列表
     */
    List<TrafficViolation> findByPage(Map<String, Object> params);
    
    /**
     * 根据条件统计记录数
     * @param params 查询参数
     * @return 记录数
     */
    long count(Map<String, Object> params);
    
    /**
     * 根据ID查询违章记录
     * @param id 违章ID
     * @return 违章记录
     */
    TrafficViolation findById(@Param("id") Long id);
    
    /**
     * 根据车牌号查询违章记录
     * @param licensePlate 车牌号
     * @return 违章记录列表
     */
    List<TrafficViolation> findByLicensePlate(@Param("licensePlate") String licensePlate);
    
    /**
     * 根据时间范围查询违章记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 违章记录列表
     */
    List<TrafficViolation> findByTimeRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
    
    /**
     * 根据违章类型查询违章记录
     * @param violationType 违章类型
     * @return 违章记录列表
     */
    List<TrafficViolation> findByViolationType(@Param("violationType") String violationType);
    
    /**
     * 插入违章记录
     * @param violation 违章记录对象
     * @return 影响行数
     */
    int insert(TrafficViolation violation);
    
    /**
     * 更新违章记录
     * @param violation 违章记录对象
     * @return 影响行数
     */
    int update(TrafficViolation violation);
    
    /**
     * 删除违章记录（逻辑删除）
     * @param id 违章ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 统计各类违章数量
     * @return 违章类型和数量的映射列表
     */
    List<Map<String, Object>> countByViolationType();
    
    /**
     * 统计每月违章数量
     * @param year 年份
     * @return 月份和违章数量的映射列表
     */
    List<Map<String, Object>> countByMonth(@Param("year") int year);
} 