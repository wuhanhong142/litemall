package com.bestsoft.litemall.db.dao;

import org.apache.ibatis.annotations.Param;
import com.bestsoft.litemall.db.domain.LitemallOrder;

import java.time.LocalDateTime;

public interface OrderMapper {
    int updateWithOptimisticLocker(@Param("lastUpdateTime") LocalDateTime lastUpdateTime, @Param("order") LitemallOrder order);
}