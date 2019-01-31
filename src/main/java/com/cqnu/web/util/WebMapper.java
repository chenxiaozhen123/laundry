package com.cqnu.web.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Description 通用mapper
 * @Author xzchen
 * @Date 2019/1/31 10:16
 * @Version 1.0
 **/
public interface WebMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
