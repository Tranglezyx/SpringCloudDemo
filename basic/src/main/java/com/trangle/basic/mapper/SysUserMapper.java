package com.trangle.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trangle.basic.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}