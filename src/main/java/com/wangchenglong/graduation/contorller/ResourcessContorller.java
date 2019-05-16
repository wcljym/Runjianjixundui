package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.News;
import com.wangchenglong.graduation.domain.Resourcess;
import com.wangchenglong.graduation.repository.ResourceRepository;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: Graduation_WCL
 * @description:
 * @author: Wangchenglong
 * @create: 2019-03-16 18:58
 **/
@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class ResourcessContorller {

    @Autowired
    ResourceRepository resourceRepository;

    @RequestMapping("/save_resource")
    public String save_resource() {

        return "admin/resource/upload";
    }

    @ResponseBody
    @RequestMapping(value = "/save_resource", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_news(@RequestParam(value = "software_name", required = true) String software_name,
                                         @RequestParam(value = "path", required = true) String path,
                                         @RequestParam(value = "date", required = true) String time,
                                         @RequestParam(value = "sii", required = true) String sii,
                                         @RequestParam(value = "enunciation", required = true) String enunciation,
                                         @RequestParam(value = "introduction", required = true) String introduction

    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Resourcess resourcess = new Resourcess();
        resourcess.setSoftware_name(software_name);
        resourcess.setDate(date);
        resourcess.setIntroduction(introduction);
        resourcess.setPath(path);
        resourcess.setSize(sii);
        resourcess.setEnunciation(enunciation);
        resourceRepository.save(resourcess);
        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }

    @RequestMapping("/list_resource")
    public String list_resource() {

        return "admin/resource/list_resource";
    }


    @ResponseBody
    @RequestMapping(value = "/list_resource_info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> list_resource_info(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Resourcess> newsInfos = resourceRepository.findAll(pageable);
        List<Resourcess> newslist = newsInfos.getContent();
        System.out.print(newslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", newsInfos.getTotalElements());
        map.put("data", newsInfos.getContent());


        return map;

    }


    /**
     * 删除
     *
     * @param id
     * @return
     */

    @ResponseBody
    @RequestMapping("/del_resource")

    public Map<String, Object> del_resource(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> map = new HashMap<>();
        Resourcess resourcess = resourceRepository.findResourcessById(id);
        if (resourcess != null) {


            resourceRepository.delete(resourcess);
            map.put("code", 200);
            map.put("message", "success");

        }


        return map;

    }

    /**
     * 修改资源信息
     *
     * @param id
     * @return
     */

//    @RequestMapping(value = "/{id}/edit_resource1")
//    private ModelAndView edit_resource(@PathVariable("id") Long id) {
//        Resourcess resourcess = resourceRepository.findResourcessById(id);
//       ModelAndView modelAndView=new ModelAndView("admin/resource/edit_resource");
//       modelAndView.addObject("Resourcess",resourcess);
//       return  modelAndView;
//    }
    @RequestMapping(value = "/{id}/edit_resource1")
    public ModelAndView edit_news(@PathVariable("id") Long id) {
        Resourcess announcement = resourceRepository.findResourcessById(id);
        System.out.println(announcement);
        ModelAndView modelAndView = new ModelAndView("admin/resource/edit_resource");
        modelAndView.addObject("Resourcess", announcement);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/update_resource", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_news(@RequestParam(value = "software_name", required = true) String software_name,
                                         @RequestParam(value = "path", required = true) String path,
                                         @RequestParam(value = "id", required = true) Long id,
                                         @RequestParam(value = "date", required = true) String time,
                                         @RequestParam(value = "sii", required = true) String sii,
                                         @RequestParam(value = "enunciation", required = true) String enunciation,
                                         @RequestParam(value = "introduction", required = true) String introduction

    ) {
        Resourcess resourcess = resourceRepository.findResourcessById(id);

        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        resourcess.setSoftware_name(software_name);
        resourcess.setDate(date);
        resourcess.setIntroduction(introduction);
        resourcess.setPath(path);
        resourcess.setSize(sii);
        resourcess.setEnunciation(enunciation);
        resourceRepository.save(resourcess);
        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }


}
