package com.bestsoft.litemall.admin.web;

import com.alibaba.fastjson.JSON;
import com.bestsoft.litemall.admin.annotation.RequiresPermissionsDesc;
import com.bestsoft.litemall.core.util.ResponseUtil;
import com.bestsoft.litemall.core.validator.Order;
import com.bestsoft.litemall.core.validator.Sort;
import com.bestsoft.litemall.db.domain.LitemallBusiUser;
import com.bestsoft.litemall.db.service.BusiUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuhanhong
 * @date 2019-07-16
 * @descp
 */
@RestController
@RequestMapping("admin/busiUser")
public class BusiUserController extends BaseController {

    @Autowired
    private BusiUserService busiUserService;

    @RequiresPermissions("admin:busi:list")
    @RequiresPermissionsDesc(menu = {"业务员管理", "业务员管理"}, button = "查询")
    @PostMapping("/list")
    public Object list(String name, String code, String phone,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {

        List<LitemallBusiUser> userList = busiUserService.queryBusiUser(name, code, phone, page, limit, sort, order);
        return ResponseUtil.okList(userList);
    }

    @RequiresPermissions("admin:busi:create")
    @RequiresPermissionsDesc(menu = {"业务员管理","业务员管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallBusiUser user) {
        logger.info("admin:busi:create:{}", JSON.toJSONString(user));
        Object error = validate(user);
        if (error != null) return error;

        busiUserService.add(user);
        return ResponseUtil.ok(user);
    }

    @RequiresPermissions("admin:busi:update")
    @RequiresPermissionsDesc(menu = {"业务员管理", "业务员管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallBusiUser user) {
        Object error = validate(user);
        if (error != null) return error;
        if (busiUserService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(user);
    }

    @RequiresPermissions("admin:busi:delete")
    @RequiresPermissionsDesc(menu = {"业务员管理", "业务员管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallBusiUser user) {
        Integer id = user.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        busiUserService.deleteById(id);
        return ResponseUtil.ok();
    }


    private Object validate(LitemallBusiUser user) {
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getCode()) || StringUtils.isEmpty(user.getPhone())) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}
