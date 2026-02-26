package com.trangle.basic.controller;

import com.github.pagehelper.PageInfo;
import com.trangle.basic.entity.SysUser;
import com.trangle.basic.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/{id}")
    public SysUser getById(@PathVariable Long id) {
        return sysUserService.getById(id);
    }

    @GetMapping("/page")
    public PageInfo<SysUser> page(@RequestParam(defaultValue = "1") int pageNum,
                                    @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(required = false) String account,
                                    @RequestParam(required = false) String mobile) {
        return sysUserService.page(pageNum, pageSize, account, mobile);
    }

    @PostMapping
    public boolean save(@RequestBody SysUser sysUser) {
        return sysUserService.save(sysUser);
    }

    @PutMapping
    public boolean updateById(@RequestBody SysUser sysUser) {
        return sysUserService.updateById(sysUser);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return sysUserService.deleteById(id);
    }
}