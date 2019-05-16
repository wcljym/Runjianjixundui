package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.Grade;
import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.domain.SysRole;
import com.wangchenglong.graduation.repository.AdminRepository;
import com.wangchenglong.graduation.repository.GradeRepository;
import com.wangchenglong.graduation.repository.UserRepository;
import com.wangchenglong.graduation.service.SysRoleService;
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
 * @description: 成绩
 * @author: Wangchenglong
 * @create: 2019-03-14 10:18
 **/

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class GradeController {
    private static final Long ROLE_USER_ROLES_ID = 2L;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    GradeRepository gradeRepository;
    /**
     * @Description:
     * @Param:
     * @return:
     * @Author: WangChenglong
     * @Date:
     */
    @RequestMapping(value = "/to_save_grade", method = RequestMethod.GET)
    public String to_save_grade() {

//        ModelAndView mview = new ModelAndView("grade/save_grade");

        return "admin/grade/save_grade";
    }
    @RequestMapping(value = "/list_grade", method = RequestMethod.GET)
    public String list_grade() {


        return "admin/grade/list_grade";
    }
    /**
     * @Description: 回显学号
     * @Param:
     * @return:
     * @Author: WangChenglong
     * @Date:
     */
    @ResponseBody
    @RequestMapping(value = "/select_grade", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> select_type() {
        Map<String, Object> map = new HashMap<>();
        List<SysRole> list = new ArrayList<>();
        list.add(sysRoleService.getSysRoleById(ROLE_USER_ROLES_ID));
        List<Member> list1 = adminRepository.findMemberByRoles(list);
        map.put("code", 200);
        map.put("list2", list1);
        map.put("message", "成功");
        return map;


    }

    /**
     * @Description: 根据学号回显表单
     * @Param:
     * @return:
     * @Author: WangChenglong
     * @Date:
     */
    @ResponseBody
    @RequestMapping(value = "/search_user_id", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> search_user_id(@RequestParam(value = "stuId", required = true) String stuId) {
        Map<String, Object> map = new HashMap<>();
        Member member = userRepository.findMemberByStuId(stuId);
        if (member != null) {
            map.put("code", 200);
            map.put("member",member);
            map.put("message", "成功");
        }
        return map;


    }


    @ResponseBody
    @RequestMapping(value = "/save_grade", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_news(@RequestParam(value = "id", required = true) Long id,
                                         @RequestParam(value = "stuId", required = true) String stuId,
                                         @RequestParam(value = "className", required = true) String className,
                                         @RequestParam(value = "userName", required = true) String userName,
                                         @RequestParam(value = "date", required = true) String time,
                                         @RequestParam(value = "test_items", required = true) String test_items,
                                         @RequestParam(value = "fractional_number", required = true) double fractional_number,
                                         @RequestParam(value = "realName", required = true) String realName
    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Grade grade=new Grade();
        grade.setStuId(stuId);
        grade.setClassName(className);
        grade.setDate(date);
        grade.setFractional_number(fractional_number);
        grade.setRealName(realName);
        grade.setTest_items(test_items);
        grade.setUserName(userName);
        gradeRepository.save(grade);


        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/listAll_grade", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> listAll_grade(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Grade> gradesInfos = gradeRepository.findAll(pageable);
        List<Grade> gradeslist = gradesInfos.getContent();
        System.out.print(gradeslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", gradesInfos.getTotalElements());
        map.put("data", gradesInfos.getContent());

        return map;


    }
    @ResponseBody
    @RequestMapping(value = "/del_grade")
    public Map<String, Object> del_grade(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> map = new HashMap<>();
        Grade grade = gradeRepository.findGradeById(id);
        gradeRepository.delete(grade);
        map.put("code", 200);
        map.put("message", "success");

        return map;
    }
    @RequestMapping(value = "/{id}/edit_grade")
    public ModelAndView edit_news(@PathVariable("id") Long id) {
        Grade grade = gradeRepository.findGradeById(id);
        System.out.println(grade);
        ModelAndView modelAndView = new ModelAndView("admin/grade/edit_grade");
        modelAndView.addObject("Grade", grade);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/update_grade", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> update_grade(@RequestParam(value = "id", required = true) Long id,
                                         @RequestParam(value = "stuId", required = true) String stuId,
                                         @RequestParam(value = "className", required = true) String className,
                                         @RequestParam(value = "date", required = true) String time,
                                         @RequestParam(value = "test_items", required = true) String test_items,
                                         @RequestParam(value = "userName", required = true) String userName,
                                         @RequestParam(value = "fractional_number", required = true) double fractional_number,
                                         @RequestParam(value = "realName", required = true) String realName
    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Grade grade=gradeRepository.findGradeById(id);
        grade.setStuId(stuId);
        grade.setClassName(className);
        grade.setDate(date);
        grade.setFractional_number(fractional_number);
        grade.setRealName(realName);
        grade.setTest_items(test_items);
        gradeRepository.save(grade);


        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }

    /**
     * 模糊查询
     */
    @ResponseBody
    @RequestMapping(value = "/search1", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> search(@RequestParam(value = "keyword", required = true) String keyword, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Grade> gradeInfos = gradeRepository.searchBykeyword(keyword, pageable);
        List<Grade> gradeslist = gradeInfos.getContent();
        System.out.print(gradeslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", gradeInfos.getTotalElements());
        map.put("data", gradeInfos.getContent());

        return map;
    }


}
