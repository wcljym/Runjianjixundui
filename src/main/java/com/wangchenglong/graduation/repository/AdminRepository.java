package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.domain.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Member,Long> {
    /**
     * 查找未激活的用户，用来激活
     * @param roles
     * @return
     */
    Page<Member>  findMemberByRoles(List<SysRole> roles, Pageable pageable);
    List<Member> findMemberByRoles(List<SysRole> roles);
}
