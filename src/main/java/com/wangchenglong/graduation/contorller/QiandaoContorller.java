package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.domain.Qiandao;
import com.wangchenglong.graduation.repository.QiandaoRepository;
import com.wangchenglong.graduation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.awt.SunHints;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: Graduation_WCL
 * @description: 签到控制器
 * @author: Wangchenglong
 * @create: 2019-02-27 23:58
 **/
@Controller
public class QiandaoContorller {
    @Autowired
    UserRepository userRepository;
    @Autowired
    QiandaoRepository qiandaoRepository;

    /**
     * 签到接口
     *
     * @param username
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/Qiandao", produces = "application/json;charset=UTF-8")
    public Map<String, Object> Qiandao(@RequestParam(value = "username", required = true) String username) {

        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQ");
        System.out.println(username);
        if (qiandaoRepository.findMemberByUsername(username) == null) {
            Member member = userRepository.findMemberByUsername(username);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = new Date();
            try {
                date = formatter.parse(formatter.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("QQFDHSHDSHDFSHDSHQQ");
            System.out.println(date);
            member.setState(1);
            int count;
            count=member.getCount()+1;
            member.setCount(count);
            Qiandao qiandao = new Qiandao();
            InetAddress addr = null;
            try {
                addr = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String ip = addr.getHostAddress().toString(); //获取本机ip
            qiandao.setIp(ip);
            qiandao.setUsername(member.getUsername());
            qiandao.setDate(date);
            qiandao.setStuId(member.getStuId());
            qiandao.setRealName(member.getRealName());
            qiandao.setState(1);
            qiandao.setCount(count);


            qiandaoRepository.save(qiandao);
            map.put("code", 200);
            map.put("message", "成功");
        } else {
            map.put("code", 500);
            map.put("message", "失败");


        }

        return map;
    }

    /**
     * @param username
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/ckeckQiandao", produces = "application/json;charset=UTF-8")
    public Map<String, Object> CheckQiandao(@RequestParam(value = "username", required = true) String username) {

        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQ");
        System.out.println(username);
        Member member = userRepository.findMemberByUsername(username);
        if (member.getState() == 1) {
            map.put("code", 200);
            map.put("message", "成功");

        }


        return map;
    }







}
