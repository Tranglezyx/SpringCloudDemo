package com.trangle.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.trangle.basic.entity.SysUser;
import com.trangle.basic.mapper.SysUserMapper;
import com.trangle.basic.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        PageHelper.startPage(pageNum, pageSize);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(account)) {
            wrapper.like(SysUser::getAccount, account);
        }
        if (StringUtils.hasText(mobile)) {
            wrapper.like(SysUser::getMobile, mobile);
        }
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