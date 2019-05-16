package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.About;
import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.domain.News;
import com.wangchenglong.graduation.domain.Type;
import com.wangchenglong.graduation.repository.NewsRepository;
import com.wangchenglong.graduation.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: Graduation_WCL
 * @description: 新闻控制器
 * @author: Wangchenglong
 * @create: 2019-03-05 14:24
 **/
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class NewsContorller {

    @Autowired
    TypeRepository typeRepository;
    @Autowired
    NewsRepository newsRepository;

    @ResponseBody
    @RequestMapping(value = "/select_type", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> select_type() {
        Map<String, Object> map = new HashMap<>();
        List<Type> list2 = typeRepository.findAll();
        map.put("code", 200);
        map.put("list2", list2);
        map.put("message", "成功");
        return map;


    }


    @ResponseBody
    @RequestMapping(value = "/save_news", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_news(@RequestParam(value = "title", required = true) String title,
                                         @RequestParam(value = "type", required = true) int type,
                                         @RequestParam(value = "content111", required = true) String content,
                                         @RequestParam(value = "date", required = true) String time,
                                         @RequestParam(value = "author", required = true) String author,
                                         @RequestParam(value = "jiesao", required = true) String jiesao,
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
        Type type11 = typeRepository.findById(type);
        News news = new News();
        news.setTitle(title);
        news.setAuthor(author);
        news.setContent(content);
        news.setDate(date);
        news.setJiesao(jiesao);
        news.setStyle(type11.getName());
        news.setImage(image);
        newsRepository.save(news);
        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }


    /**
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/del_news")
    public Map<String, Object> del_news(@RequestParam(value = "id", required = true) int id) {
        Map<String, Object> map = new HashMap<>();
        News news = newsRepository.findById(id);
        newsRepository.delete(news);
        map.put("code", 200);
        map.put("message", "success");

        return map;
    }


//    @RequestMapping(value = "/edit_news")
//    public Map<String,Object> edit_news(@RequestParam(value = "id", required = true) int id) {
//        Map<String,Object> map=new HashMap<>();
//
//        News news = newsRepository.findById(id);
//        System.out.println(news);
////        ModelAndView modelAndView = new ModelAndView("admin/news/edit_new");
////        modelAndView.addObject("new", news);
//        map.put("code",200);
//        return map;
//    }


    @RequestMapping(value = "/{id}/edit_news")
    public ModelAndView edit_news(@PathVariable("id") int id) {
        News news = newsRepository.findById(id);
        System.out.println(news);
        ModelAndView modelAndView = new ModelAndView("admin/news/edit_new");
        modelAndView.addObject("News", news);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping(value = "/update_news", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> update_news(@RequestParam(value = "title", required = true) String title,
                                           @RequestParam(value = "type", required = true) int type,
                                           @RequestParam(value = "content111", required = true) String content,
                                           @RequestParam(value = "date", required = true) String time,
                                           @RequestParam(value = "jiesao", required = true) String jiesao,
                                           @RequestParam(value = "author", required = true) String author,
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
        Type type11 = typeRepository.findById(type);
        News news = newsRepository.findById(id);
        if (news != null) {
            news.setTitle(title);
            news.setAuthor(author);
            news.setContent(content);
            news.setDate(date);
            news.setJiesao(jiesao);
            news.setStyle(type11.getName());
            news.setImage(image);
            newsRepository.save(news);
            map.put("code", 200);
            map.put("message", "添加成功");
        }
        return map;
    }
}