package com.trangle.basic.service;

import com.github.pagehelper.PageInfo;
import com.trangle.basic.entity.SysUser;

public interface SysUserService {
    SysUser getById(Long id);

    PageInfo<SysUser> page(int pageNum, int pageSize, String account, String mobile);

    boolean save(SysUser sysUser);

    boolean updateById(SysUser sysUser);

    boolean deleteById(Long id);
}