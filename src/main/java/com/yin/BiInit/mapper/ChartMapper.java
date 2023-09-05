package com.yin.BiInit.mapper;

import com.yin.BiInit.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Entity com.yin.BiInit.model.entity.Chart
 */
public interface ChartMapper extends BaseMapper<Chart> {

    List<Map<String, Object>> queryChartData(String querySql);
}




