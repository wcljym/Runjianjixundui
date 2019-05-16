package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.domain.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<Member,Long> {

     Member findMemberByUsername(String username);
     Member findMemberByUsernameAndPassword(String username,String password);
     Member findMemberById(int id);
     @Transactional
     @Query(value = "select e from Member  e where  e.username  like  CONCAT('%',:keyword,'%')  or e.realName   like  CONCAT('%',:keyword,'%') or e.className  like  CONCAT('%',:keyword,'%') ")
      Page<Member> searchBykeyword(@Param("keyword") String keyword,Pageable pageable);
     Member findMemberByStuId(String stuId);
}
