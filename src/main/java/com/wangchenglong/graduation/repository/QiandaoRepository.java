package com.wangchenglong.graduation.repository;


import com.wangchenglong.graduation.domain.Qiandao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QiandaoRepository extends JpaRepository<Qiandao,Long> {
    Qiandao findMemberByUsername(String username);

}
