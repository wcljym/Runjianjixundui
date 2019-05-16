package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.News;
import com.wangchenglong.graduation.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {

    News findById(int id);
    //News findByStyle(String stytle);
   List<News> findByStyle(String stytle);


}
