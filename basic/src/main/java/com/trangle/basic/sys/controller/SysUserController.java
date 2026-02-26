package com.trangle.basic.sys.controller;

import com.github.pagehelper.PageInfo;
import com.trangle.basic.common.dto.BaseResponse;
import com.trangle.basic.sys.entity.SysUser;
import com.trangle.basic.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/{id}")
    public BaseResponse<SysUser> getById(@PathVariable Long id) {
        return BaseResponse.success(sysUserService.getById(id));
    }

    @GetMapping("/page")
    public BaseResponse<PageInfo<SysUser>> page(@RequestParam(defaultValue = "1") int pageNum,
                                                 @RequestParam(defaultValue = "10") int pageSize,
                                                 @RequestParam(required = false) String account,
                                                 @RequestParam(required = false) String mobile) {
        return BaseResponse.success(sysUserService.page(pageNum, pageSize, account, mobile));
    }

    @PostMapping
    public BaseResponse<Boolean> save(@RequestBody SysUser sysUser) {
        return BaseResponse.success(sysUserService.save(sysUser));
    }

    @PutMapping
    public BaseResponse<Boolean> updateById(@RequestBody SysUser sysUser) {
        return BaseResponse.success(sysUserService.updateById(sysUser));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteById(@PathVariable Long id) {
        return BaseResponse.success(sysUserService.deleteById(id));
    }
}
