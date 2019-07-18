package com.bestsoft.litemall.wx.web;

import com.bestsoft.litemall.core.util.ResponseUtil;
import com.bestsoft.litemall.db.domain.LitemallBusiUser;
import com.bestsoft.litemall.db.service.BusiUserService;
import com.bestsoft.litemall.db.service.LitemallUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuhanhong
 * @date 2019-07-17
 * @descp
 */
@RestController
@RequestMapping("wx/busiUser")
public class WxBusiUserController {

    @Autowired
    private LitemallUserService userService;
    @Autowired
    private BusiUserService busiUserService;

    @PostMapping("bind")
    public Object bindBusiUser(@RequestBody JSONObject bindObj){
        if (bindObj == null) return ResponseUtil.badArgument();
        Integer userId = bindObj.getInt("userId");
        if (userId == null) return ResponseUtil.unlogin();
        String code = bindObj.getString("code");
        if (StringUtils.isEmpty(code)) return ResponseUtil.badArgument();

        LitemallBusiUser user = busiUserService.findByCode(code);
        if (user == null) return ResponseUtil.fail(100, "邀请码不存在");

        int bindRet = userService.bindBusiUser(userId, code);

        return ResponseUtil.ok(bindRet);
    }
}
