package com.cqnu.base.mapper;

import com.cqnu.base.model.BaseBean;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author xzchen
 * @Date 2019/1/31 10:23
 * @Version 1.0
 **/
public interface BaseMapper {
    @Select("select sysdate today, null nullstr from dual")
    Map<String, Object> getNow();

    @Select("select level lv, to_char(sysdate + level, 'yyyy-mm-dd') theday, floor(dbms_random.value(0, 100)) thevalue from dual connect by level <= 1000")
    List<Map<String, Object>> getDays(Map<String, Object> param);

    List<BaseBean> qryBaseBeanByMap(Map<String, Object> param);

    List<BaseBean> qryBaseBeanByType(Map<String, Object> param);

    void callProcedual(Map<String, Object> param);
}
