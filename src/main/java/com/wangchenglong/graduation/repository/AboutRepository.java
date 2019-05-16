package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.About;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AboutRepository extends JpaRepository<About,Long> {
    List<About> findAll();
    About findById(int id);
}
