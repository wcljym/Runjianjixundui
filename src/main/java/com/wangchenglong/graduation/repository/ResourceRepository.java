package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.Grade;
import com.wangchenglong.graduation.domain.Resourcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resourcess, Long> {


    Resourcess findResourcessById(Long id);


    @Transactional
    @Query(value = "select r from Resourcess  r where  r.software_name  like  CONCAT('%',:keyword,'%')  or r.enunciation   like  CONCAT('%',:keyword,'%')  ")
    Page<Resourcess> searchBykeyword(@Param("keyword") String keyword, Pageable pageable);


}
