package com.trangle.basic.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trangle.basic.sys.entity.SysUser;
import com.trangle.basic.sys.mapper.SysUserMapper;
import com.trangle.basic.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public PageInfo<SysUser> page(int pageNum, int pageSize, String account, String mobile) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (account != null && !account.isEmpty()) {
            wrapper.like(SysUser::getAccount, account);
        }
        if (mobile != null && !mobile.isEmpty()) {
            wrapper.like(SysUser::getMobile, mobile);
        }
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(sysUserMapper.selectList(wrapper));
    }

    @Override
    public boolean save(SysUser sysUser) {
        return sysUserMapper.insert(sysUser) > 0;
    }

    @Override
    public boolean updateById(SysUser sysUser) {
        return sysUserMapper.updateById(sysUser) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return sysUserMapper.deleteById(id) > 0;
    }
}
