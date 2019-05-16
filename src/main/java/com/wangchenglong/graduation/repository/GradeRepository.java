package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.Grade;
import com.wangchenglong.graduation.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface GradeRepository extends JpaRepository<Grade,Long> {

    Grade findGradeById(Long id);
    @Transactional
    @Query(value = "select e from Grade  e where  e.stuId  like  CONCAT('%',:keyword,'%')  or e.fractional_number   like  CONCAT('%',:keyword,'%') or e.className  like  CONCAT('%',:keyword,'%') ")
    Page<Grade> searchBykeyword(@Param("keyword") String keyword, Pageable pageable);
    Page<Grade> findGradeByUserName(@Param("userName") String userName, Pageable pageable);

}
