package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    List<Type> findAll();
    Type findById(int type);

}
