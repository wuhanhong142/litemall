package com.bestsoft.litemall.db.service;

import com.bestsoft.litemall.db.dao.LitemallBusiUserMapper;
import com.bestsoft.litemall.db.domain.LitemallBusiUser;
import com.bestsoft.litemall.db.domain.LitemallBusiUserExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuhanhong
 * @date 2019-07-16
 * @descp
 */
@Service
public class BusiUserService {

    @Autowired
    private LitemallBusiUserMapper busiUserMapper;

    public int add(LitemallBusiUser user) {
        user.setAddTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return busiUserMapper.insertSelective(user);
    }

    public List<LitemallBusiUser> queryBusiUser(String name, String code, String phone, Integer pageNum, Integer pageSize, String sort, String order) {
        LitemallBusiUserExample example = new LitemallBusiUserExample();
        LitemallBusiUserExample.Criteria criteria = example.createCriteria();

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(phone)) {
            criteria.andPhoneEqualTo(phone);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.offsetPage(pageNum, pageSize);

        return busiUserMapper.selectByExample(example);
    }

    public int updateById(LitemallBusiUser user) {
        user.setUpdateTime(LocalDateTime.now());
        return busiUserMapper.updateByPrimaryKeySelective(user);
    }

    public LitemallBusiUser findById(Integer id) {
        return busiUserMapper.selectByPrimaryKey(id);
    }

    public int deleteById(Integer id) {
        return busiUserMapper.deleteByPrimaryKey(id);
    }

    public LitemallBusiUser findByCode(String code) {
        LitemallBusiUserExample example = new LitemallBusiUserExample();
        LitemallBusiUserExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);

        return busiUserMapper.selectOneByExample(example);
    }
}
