package com.example.intelligenttransportation.service.impl;

import com.example.intelligenttransportation.entity.TrafficViolation;
import com.example.intelligenttransportation.mapper.TrafficViolationMapper;
import com.example.intelligenttransportation.service.TrafficViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 交通违章服务实现类
 */
@Service
public class TrafficViolationServiceImpl implements TrafficViolationService {

    @Autowired
    private TrafficViolationMapper trafficViolationMapper;

    @Override
    public List<TrafficViolation> findAll() {
        return trafficViolationMapper.findAll();
    }

    @Override
    public Map<String, Object> findByPage(int page, int size, Map<String, Object> params) {
        // 计算分页参数
        int offset = (page - 1) * size;
        
        // 查询总数
        long total = trafficViolationMapper.count(params);
        
        // 添加分页参数
        params.put("offset", offset);
        params.put("size", size);
        
        // 查询数据
        List<TrafficViolation> records = trafficViolationMapper.findByPage(params);
        
        // 构建结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);
        result.put("page", page);
        result.put("size", size);
        result.put("pages", (total + size - 1) / size);
        
        return result;
    }

    @Override
    public TrafficViolation findById(Long id) {
        return trafficViolationMapper.findById(id);
    }

    @Override
    public List<TrafficViolation> findByLicensePlate(String licensePlate) {
        return trafficViolationMapper.findByLicensePlate(licensePlate);
    }

    @Override
    public List<TrafficViolation> findByTimeRange(Date startTime, Date endTime) {
        return trafficViolationMapper.findByTimeRange(startTime, endTime);
    }

    @Override
    public List<TrafficViolation> findByViolationType(String violationType) {
        return trafficViolationMapper.findByViolationType(violationType);
    }

    @Override
    @Transactional
    public TrafficViolation save(TrafficViolation violation) {
        violation.setCreateTime(new Date());
        trafficViolationMapper.insert(violation);
        return violation;
    }

    @Override
    @Transactional
    public TrafficViolation update(TrafficViolation violation) {
        violation.setUpdateTime(new Date());
        trafficViolationMapper.update(violation);
        return violation;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        trafficViolationMapper.deleteById(id);
    }

    @Override
    @Transactional
    public boolean handleViolation(Long id, Long handlerId, String handleResult) {
        TrafficViolation violation = trafficViolationMapper.findById(id);
        if (violation == null) {
            return false;
        }
        
        // 只有未处理和申诉中的违章才能处理
        if (violation.getStatus() != 0 && violation.getStatus() != 2) {
            return false;
        }
        
        violation.setStatus(1); // 已处理
        violation.setHandlerId(handlerId);
        violation.setHandleTime(new Date());
        violation.setHandleResult(handleResult);
        violation.setUpdateTime(new Date());
        
        return trafficViolationMapper.update(violation) > 0;
    }

    @Override
    @Transactional
    public boolean revokeViolation(Long id, Long handlerId, String reason) {
        TrafficViolation violation = trafficViolationMapper.findById(id);
        if (violation == null) {
            return false;
        }
        
        // 只有未处理和申诉中的违章才能撤销
        if (violation.getStatus() != 0 && violation.getStatus() != 2) {
            return false;
        }
        
        violation.setStatus(3); // 已撤销
        violation.setHandlerId(handlerId);
        violation.setHandleTime(new Date());
        violation.setHandleResult("已撤销：" + reason);
        violation.setUpdateTime(new Date());
        
        return trafficViolationMapper.update(violation) > 0;
    }

    @Override
    public Map<String, Long> countViolationTypes() {
        List<Map<String, Object>> statistics = trafficViolationMapper.countByViolationType();
        
        Map<String, Long> result = new HashMap<>();
        for (Map<String, Object> stat : statistics) {
            String type = (String) stat.get("violationType");
            Long count = (Long) stat.get("count");
            result.put(type, count);
        }
        
        return result;
    }

    @Override
    public Map<Integer, Long> countViolationsByMonth(int year) {
        List<Map<String, Object>> statistics = trafficViolationMapper.countByMonth(year);
        
        Map<Integer, Long> result = new HashMap<>();
        // 初始化所有月份为0
        for (int i = 1; i <= 12; i++) {
            result.put(i, 0L);
        }
        
        // 填充实际数据
        for (Map<String, Object> stat : statistics) {
            Integer month = (Integer) stat.get("month");
            Long count = (Long) stat.get("count");
            result.put(month, count);
        }
        
        return result;
    }
} 