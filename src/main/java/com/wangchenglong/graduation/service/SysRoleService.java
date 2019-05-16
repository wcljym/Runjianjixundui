package com.wangchenglong.graduation.service;

import com.wangchenglong.graduation.domain.SysRole;
import com.wangchenglong.graduation.repository.SysRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: Graduation_WCL
 * @description:
 * @author: Wangchenglong
 * @create: 2018-12-10 23:10
 **/
@Service
public class SysRoleService  {

    @Autowired
    SysRoleRepository sysRoleRepository;
        public SysRole getSysRoleById(Long id){

            return  sysRoleRepository.findSysRoleById(id);
        }

        public List<SysRole> listall(){

            return sysRoleRepository.findAll();
        }

}
