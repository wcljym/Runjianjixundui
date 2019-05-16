package com.wangchenglong.graduation.service;

import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: Graduation_WCL
 * @description:
 * @author: Wangchenglong
 * @create: 2018-12-09 23:36
 **/
@Service
public class UserService  implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Member user=userRepository.findMemberByUsername(s);
        if (user==null){

            throw new UsernameNotFoundException("用户名不存在");

        }
        System.out.println("s:"+s);
        System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());
        return user;
    }

    /**
     * 用户注册
     * @param member
     * @return
     */
    public  Member registered(Member member ){

        return  userRepository.save(member);
    }

    /**
     * 根据id查找member
     * @param id
     * @return
     */
    public Member findByid(int id){

        return userRepository.findMemberById(id);
    }


}
