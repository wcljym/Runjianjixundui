package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.About;
import com.wangchenglong.graduation.domain.News;
import com.wangchenglong.graduation.domain.SysRole;
import com.wangchenglong.graduation.domain.Type;
import com.wangchenglong.graduation.repository.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: Graduation_WCL
 * @description: 关于我们的控制器
 * @author: Wangchenglong
 * @create: 2019-03-01 12:51
 **/
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class AboutContorller {


    @Autowired
    AboutRepository aboutRepository;




    @ResponseBody
    @RequestMapping(value = "/save_abouts", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_news(@RequestParam(value = "title", required = true) String title,
                                         @RequestParam(value = "content111", required = true) String content,
                                         @RequestParam(value = "date", required = true) String time,
                                         @RequestParam(value = "image", required = true) String image
    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        About about = new About();
        about.setCopy(content);
        about.setDate(date);
        about.setImg(image);
        about.setTitle(title);
        aboutRepository.save(about);
        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }
//
//    @ResponseBody
//    @RequestMapping(value = "/listAbouts", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public Map<String,Object> listAbout() {
//        Map<String,Object> map=new HashMap<>();
//        List<About> list = new ArrayList<>();
//        list = aboutRepository.findAll();
////        ModelAndView mview = new ModelAndView("about1");
////        mview.addObject("elist", list);
//
//        map.put("code",200);
//        map.put("message","success");
//        map.put("list",list);
//        return map;
//    }


    @ResponseBody
    @RequestMapping(value = "/list_about_info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> list_about_info(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<About> aboutsInfos = aboutRepository.findAll(pageable);
        List<About> aboutslist = aboutsInfos.getContent();
        System.out.print(aboutslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", aboutsInfos.getTotalElements());
        map.put("data", aboutsInfos.getContent());


        return map;

    }

    @ResponseBody
    @RequestMapping(value = "/del_abouts")
    public Map<String, Object> del_abouts(@RequestParam(value = "id", required = true) int id) {
        Map<String, Object> map = new HashMap<>();
        About about = aboutRepository.findById(id);
        aboutRepository.delete(about);
        map.put("code", 200);
        map.put("message", "success");

        return map;
    }

    @RequestMapping(value = "/{id}/edit_abouts")
    public ModelAndView edit_abouts(@PathVariable("id") int id) {
        About about = aboutRepository.findById(id);
        System.out.println(about);
        ModelAndView modelAndView = new ModelAndView("admin/about/edit_about");
        modelAndView.addObject("Abouts", about);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/update_abouts", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> update_abouts(@RequestParam(value = "title", required = true) String title,
                                             @RequestParam(value = "content111", required = true) String content,
                                             @RequestParam(value = "date", required = true) String time,
                                             @RequestParam(value = "id", required = true) int id,
                                             @RequestParam(value = "image", required = true) String image
    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        About about = aboutRepository.findById(id);
        about.setCopy(content);
        about.setDate(date);
        about.setImg(image);
        about.setTitle(title);
        aboutRepository.save(about);
        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }


}
