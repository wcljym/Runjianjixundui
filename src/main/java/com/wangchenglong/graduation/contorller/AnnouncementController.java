package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.Announcement;
import com.wangchenglong.graduation.repository.AnnouncementRepository;
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
 * @description: 公告
 * @author: Wangchenglong
 * @create: 2019-03-09 17:17
 **/


@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class AnnouncementController {

    @Autowired
    AnnouncementRepository announcementRepository;

    @RequestMapping(value = "/list_Announcement")
    public String list_Announcement() {


        return "admin/announcement/list_announcement";
    }

    @ResponseBody
    @RequestMapping(value = "/list_Announcement_info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> list_Announcement_info(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Announcement> announcementInfos = announcementRepository.findAll(pageable);
        List<Announcement> announcementlist = announcementInfos.getContent();
        System.out.print(announcementlist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", announcementInfos.getTotalElements());
        map.put("data", announcementInfos.getContent());
        return map;

    }

    @RequestMapping(value = "/save_gonggao")
    public String save_gonggao() {


        return "admin/announcement/save_announcement";
    }

    @ResponseBody
    @RequestMapping(value = "/save_announcement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_announcement(@RequestParam(value = "title", required = true) String title,
                                         @RequestParam(value = "content111", required = true) String content,
                                         @RequestParam(value = "date", required = true) String time,
                                         @RequestParam(value = "publisher", required = true) String publisher,
                                         @RequestParam(value = "introduction", required = true) String introduction

    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int aa = cal.get(Calendar.DAY_OF_MONTH);
        System.out.println("日：");
        System.out.println(date);


        System.out.println(aa);

        Announcement announcement=new Announcement();
        announcement.setContent(content);
        announcement.setIntroduction(introduction);
        announcement.setDate(date);
        announcement.setPublisher(publisher);
        announcement.setTitle(title);
        announcement.setDay(aa);
        announcementRepository.save(announcement);
        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }
    @ResponseBody
    @RequestMapping(value = "/del_announcement")
    public Map<String, Object> del_announcement(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> map = new HashMap<>();
        Announcement announcement = announcementRepository.findAnnouncementById(id);
        announcementRepository.delete(announcement);
        map.put("code", 200);
        map.put("message", "success");

        return map;
    }


    @RequestMapping(value = "/{id}/edit_announcement")
    public ModelAndView edit_news(@PathVariable("id") Long id) {
        Announcement announcement = announcementRepository.findAnnouncementById(id);
        System.out.println(announcement);
        ModelAndView modelAndView = new ModelAndView("admin/announcement/edit_announcement");
        modelAndView.addObject("announcement", announcement);
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping(value = "/update_announcement", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> update_news(@RequestParam(value = "title", required = true) String title,
                                           @RequestParam(value = "content111", required = true) String content,
                                           @RequestParam(value = "date", required = true) String time,
                                           @RequestParam(value = "id", required = true) Long id,
                                           @RequestParam(value = "publisher", required = true) String publisher,
                                           @RequestParam(value = "introduction", required = true) String introduction

    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Announcement announcement = announcementRepository.findAnnouncementById(id);
        if (announcement != null) {
            announcement.setContent(content);
            announcement.setIntroduction(introduction);
            announcement.setDate(date);
            announcement.setPublisher(publisher);
            announcement.setTitle(title);
            announcementRepository.save(announcement);
            map.put("code", 200);
            map.put("message", "添加成功");
        }
        return map;
    }

}
