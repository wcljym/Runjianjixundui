package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.Leavess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LeaveRepository  extends JpaRepository<Leavess,Long> {

   Leavess findLeavessById(Long id);
   Page<Leavess> findLeavessByUserName(@Param("userName") String userName, Pageable pageable);
}
