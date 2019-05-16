package com.wangchenglong.graduation.repository;

import com.wangchenglong.graduation.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysRoleRepository extends JpaRepository<SysRole,Long> {

                SysRole findSysRoleById(Long id);
                List<SysRole> findAll();


}
