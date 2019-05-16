package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.domain.News;
import com.wangchenglong.graduation.domain.Qiandao;
import com.wangchenglong.graduation.domain.SysRole;
import com.wangchenglong.graduation.repository.AdminRepository;
import com.wangchenglong.graduation.repository.NewsRepository;
import com.wangchenglong.graduation.repository.QiandaoRepository;
import com.wangchenglong.graduation.repository.UserRepository;
import com.wangchenglong.graduation.service.SysRoleService;
import com.wangchenglong.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: Graduation_WCL
 * @description:
 * @author: Wangchenglong
 * @create: 2018-12-11 16:09
 **/
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
public class AdminsContorller {
    private static final Long ROLE_USER_ROLES_ID = 2L;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QiandaoRepository qiandaoRepository;
    @Autowired
    NewsRepository newsRepository;

    /**
     * 未激活用户列表json数据接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/list_authorization")
    public Map<String, Object> listAuthorization(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<SysRole> sysRoleList = new ArrayList<>();
        SysRole sysRole = new SysRole();
        sysRole.setId(3L);
        sysRoleList.add(sysRole);
        Page<Member> userInfos = adminRepository.findMemberByRoles(sysRoleList, pageable);
        List<Member> userlist = userInfos.getContent();
        System.out.print(userlist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", userInfos.getTotalElements());
        map.put("data", userInfos.getContent());


        return map;

    }

    @GetMapping("/")
    public String root() {
        return "redirect:/admin_index";
    }

    /**
     * 跳转后台首页
     *
     * @return
     */
    @GetMapping("/admin_index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/admin_index");
        return modelAndView;
    }


    /**
     * 跳转用户列表页面
     *
     * @return
     */
    @GetMapping("/list_all")
    public ModelAndView list_all() {
        ModelAndView modelAndView = new ModelAndView("admin/list_user");
        return modelAndView;
    }

    /**
     * 跳转用户授权界面
     *
     * @return
     */
    @GetMapping("/authorization")
    public ModelAndView authorization() {
        ModelAndView modelAndView = new ModelAndView("admin/authorization");
        return modelAndView;
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping("/list_user")
    public Map<String, Object> listuser(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<SysRole> sysRoleList = new ArrayList<>();
//        SysRole sysRole = new SysRole();
//        sysRole.setId(3L);
//        sysRoleList.add(sysRole);
        Page<Member> userInfos = adminRepository.findAll(pageable);
        List<Member> userlist = userInfos.getContent();
        System.out.print(userlist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", userInfos.getTotalElements());
        map.put("data", userInfos.getContent());


        return map;

    }

    @RequestMapping(value = "/jihuo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> jihuo(@RequestParam(value = "id", required = true) int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = userService.findByid(id);
        System.out.print(member);
        List<SysRole> list = new ArrayList<>();
        list.add(sysRoleService.getSysRoleById(ROLE_USER_ROLES_ID));
        member.setRoles(list);

        if (adminRepository.save(member) != null) {
            int state = 1;
            map.put("code", 200);
            map.put("msg", "成功");
            map.put("data", state);
            map.put("state", 1);


        }


        return map;
    }

    @RequestMapping(value = "/del_user", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> del_user(@RequestParam(value = "id", required = true) int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = userService.findByid(id);
        adminRepository.delete(member);
        System.out.print(member);

        int state = 1;
        map.put("code", 200);
        map.put("msg", "成功");
        map.put("data", state);
        map.put("state", 1);


        return map;
    }

     /**
     * 跳转添加页面
     *
     * @return
     */
    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("admin/add_MemberInfo");
        return modelAndView;
    }


    @RequestMapping(value = "/add_user", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> add_user(@RequestParam(value = "username", required = true) String userName, @RequestParam(value = "password",
            required = true) String password, @RequestParam(value = "stuId", required = true) String stuId, @RequestParam(value = "realName", required = true) String realName, @RequestParam(value = "roles", required = true) Long rolesId) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("___________");
        System.out.println(userName);
        System.out.println(rolesId);
        System.out.println(stuId);

        System.out.println("___________");
        Member member = userRepository.findMemberByUsername(userName);
        if (member != null) {
            map.put("code", 0);
            map.put("Message", "该用用户名已被添加");
            return map;

        } else {
            member = new Member();
            List<SysRole> list = new ArrayList<>();
            list.add(sysRoleService.getSysRoleById(rolesId));
            password = new BCryptPasswordEncoder().encode(password);
            member.setUsername(userName);
            member.setPassword(password);
            member.setRoles(list);
            member.setStuId(stuId);
            member.setRealName(realName);

            if (userRepository.save(member) != null) {
                map.put("code", 200);
                map.put("msg", "成功");
            } else {
                map.put("code", 1);
                map.put("Message", "添加失败");

            }

            return map;
        }
    }

    /**
     * 从数据库读取下拉框的内容
     *
     * @return
     */
    @RequestMapping(value = "/selectRoleList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> selectRoleList() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SysRole> list = new ArrayList<>();
        list = sysRoleService.listall();
        if (list != null) {

            map.put("code", 200);
            map.put("meg", "成功");
            map.put("roleList", list);

            return map;
        }


        return map;
    }

    /**
     * @param id
     * @param userName
     * @param stuId
     * @param realName
     * @param rolesId
     * @return
     */
    @RequestMapping(value = "/edit_user", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> edit_user(@RequestParam(value = "id", required = true) int id, @RequestParam(value = "username", required = true) String userName,
                                         @RequestParam(value = "stuId", required = true) String stuId, @RequestParam(value = "realName", required = true) String realName, @RequestParam(value = "roles", required = true) Long rolesId) {
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println("___________");
        System.out.println(id);
        System.out.println(userName);
        System.out.println(rolesId);
        System.out.println(stuId);
        System.out.println("___________");
        Member member = userRepository.findMemberById(id);
//         member = userRepository.findMemberByUsername(userName);
//        if (member != null) {
//            map.put("code", 0);
//            map.put("Message", "该用用户名已被添加");
//            return map;
//
//        } else {
        List<SysRole> list = new ArrayList<>();
        list.add(sysRoleService.getSysRoleById(rolesId));
        member.setUsername(userName);
        member.setRoles(list);
        member.setStuId(stuId);
        member.setRealName(realName);
        if (userRepository.save(member) != null) {
            map.put("code", 200);
            map.put("msg", "成功");
        } else {
            map.put("code", 1);
            map.put("Message", "添加失败");

        }

        return map;

    }

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/search_user", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> search_user(@RequestParam(value = "id", required = true) int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = new Member();
        member = userRepository.findMemberById(id);
        System.out.println(member);
        if (member != null) {
            map.put("code", 200);
            map.put("mes", "成功");
            map.put("member", member);
            return map;
        }
        return map;
    }

    /**
     * 模糊查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> search(@RequestParam(value = "keyword", required = true) String keyword, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        List<SysRole> sysRoleList = new ArrayList<>();
        Page<Member> userInfos = userRepository.searchBykeyword(keyword, pageable);
        List<Member> userlist = userInfos.getContent();
        System.out.print(userlist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", userInfos.getTotalElements());
        map.put("data", userInfos.getContent());

        return map;
    }


    @RequestMapping(value = "/search_name", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> search_name(@RequestParam(value = "username", required = true) String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = userRepository.findMemberByUsername(username);
        System.out.println(member);
        if (member != null) {

            map.put("code", 1);
            map.put("message", "成功");

            return map;
        }


        return map;
    }

    /**
     * 后台显示已经签到的学生
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/Qiandao1", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> Qiandao1(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Qiandao> qiandaoInfos = qiandaoRepository.findAll(pageable);
        List<Qiandao> qiandaolist = qiandaoInfos.getContent();
        System.out.print(qiandaolist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", qiandaoInfos.getTotalElements());
        map.put("data", qiandaoInfos.getContent());


        return map;

    }

    /**
     * 跳转签到页面
     *
     * @return
     */
    @GetMapping("/qiandao")
    public ModelAndView qiandao() {
        ModelAndView modelAndView = new ModelAndView("admin/admin_index");
        return modelAndView;
    }


    /**
     * 跳转到资讯发布页面
     */


    @GetMapping("/make_news")
    public ModelAndView make_news() {
        ModelAndView modelAndView = new ModelAndView("admin/admin_news");
        return modelAndView;
    }


    /**
     *
     *
     */


    /**
     * 跳转到资讯发布页面
     */


    @GetMapping("/list_news")
    public ModelAndView list_news() {
        ModelAndView modelAndView = new ModelAndView("admin/news/list_news");
        return modelAndView;
    }

    @RequestMapping(value = "/list_news_info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> list_news_info(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<News> newsInfos = newsRepository.findAll(pageable);
        List<News> newslist = newsInfos.getContent();
        System.out.print(newslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", newsInfos.getTotalElements());
        map.put("data", newsInfos.getContent());


        return map;

    }
    /**
     * 跳转到上传关于我们的页面u
     */
    @GetMapping("/save_us_info")
    public ModelAndView save_us_info() {
        ModelAndView modelAndView = new ModelAndView("admin/about/save_about");
        return modelAndView;
    }
    /**
     * 跳转到关于我们的列表
     */
    @GetMapping("/list_us_info")
    public ModelAndView list_us_info() {
        ModelAndView modelAndView = new ModelAndView("admin/about/list_about");
        return modelAndView;
    }


    /**
     * 跳转在线请假列表
     *
     * @return
     */
    @GetMapping("/list_leavess")
    public ModelAndView list_leavess() {
        ModelAndView modelAndView = new ModelAndView("admin/leavess/list_leavess");
        return modelAndView;
    }




}
