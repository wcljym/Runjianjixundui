package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.*;
import com.wangchenglong.graduation.repository.*;
import com.wangchenglong.graduation.service.SysRoleService;
import com.wangchenglong.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: Graduation_WCL
 * @description:
 * @author: Wangchenglong
 * @create: 2018-12-09 22:13
 **/
@Controller

public class MainContorller {
    private static final Long ROLE_USER_ROLES_ID = 3L;
    private static final Long ROLE_ADMIN_ROLES_ID = 1L;
    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    AboutRepository aboutRepository;
    @Autowired
    SysRoleRepository sysRoleRepository;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AnnouncementRepository announcementRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    LeaveRepository leaveRepository;
    @Autowired
    ResourceRepository resourceRepository;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public ModelAndView index() {
        List<SysRole> list = new ArrayList<>();
        list.add(sysRoleService.getSysRoleById(ROLE_ADMIN_ROLES_ID));
        List<Member> list1 = adminRepository.findMemberByRoles(list);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("list1", list1);


        return modelAndView;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String logineorr(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "eorr/403";
    }

    @RequestMapping("/signOut")
    public String loginOut() {
        return "redirect:/login?logout";
    }

    /**
     * @return
     */
    @RequestMapping("/login?logout")
    public String tologinOut() {
        return "index";
    }


    @GetMapping("/toregistered")
    public String toregistered() {


        return "user/registered";
    }

    /**
     * @return
     */
    @GetMapping("/aboutUs")
    public String about() {


        return "/about1";
    }


    /**
     * 注册控制器，注册之后需要后台 管理员
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/registered")
    public int registered(@RequestParam(value = "username", required = true) String userName, @RequestParam(value = "password",
            required = true) String password, @RequestParam(value = "password1",
            required = true) String password1,
                          @RequestParam(value = "stuId", required = true) String stuId, @RequestParam(value = "realName", required = true) String realName) {

        int result = 0;
        Member member = userRepository.findMemberByUsername(userName);

        if (member != null) {
            result = 1;

        }

        //        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Member member = new Member();
//        List<SysRole> list = new ArrayList<>();
//        list.add(sysRoleService.getSysRoleById(ROLE_USER_ROLES_ID));
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String realName = request.getParameter("realName");
//
//        Date date = new Date();
//        try {
//            date = formatter.parse(formatter.format(new Date()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String stuId = request.getParameter("stuId");// Integer.parseInt(request.getParameter("stuId"));
//        password = new BCryptPasswordEncoder().encode(password);
//        member.setPassword(password);
//        member.setUsername(username);
//        member.setRealName(realName);
//        member.setStuId(stuId);
//        member.setRoles(list);
//        member.setEntryTime(date);
//        System.out.print(member.isAccountNonLocked());
//        member.isAccountNonLocked();
//
//        if (userService.registered(member) != null) {
//            model.addAttribute("mes", "注册成功");
//            return "redirect:/login";
//
//        }


        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search_name", produces = "application/json;charset=UTF-8")
    public Map<String, Object> search_name(@RequestParam(value = "username", required = true) String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member member = userRepository.findMemberByUsername(username);
        System.out.println(member);
        if (member != null) {
            map.put("code", 2);
            map.put("message", "失败");
            return map;
        } else {

            map.put("code", 1);
            map.put("message", "成功");
            return map;
        }
    }

    /**
     * 注册
     *
     * @param userName
     * @param password
     * @param password1
     * @param stuId
     * @param realName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/registered1", produces = "application/json;charset=UTF-8")
    public Map<String, Object> add_user(@RequestParam(value = "username", required = true) String userName, @RequestParam(value = "password",
            required = true) String password, @RequestParam(value = "password1",
            required = true) String password1,
                                        @RequestParam(value = "stuId", required = true) String stuId, @RequestParam(value = "realName", required = true) String realName

    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("___________");
        System.out.println(userName);
        System.out.println(password);
        System.out.println(password1);

        System.out.println(stuId);
        System.out.println("___________");
        Date date = new Date();
        try {
            date = formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Member member = userRepository.findMemberByUsername(userName);

        if (member != null) {
            map.put("code", 0);
            map.put("Message", "该用用户名已被添加");

        } else {
            member = new Member();
            List<SysRole> list = new ArrayList<>();
            if (!userName.trim().equals("") && userName.matches("^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$")) {
                if (!password.trim().equals("")&&password.equals(password1)&&password.matches("^[\\S]{6,12}$")&&realName.matches("^[\\u4E00-\\u9FA5A-Za-z]+$")) {
                    list.add(sysRoleService.getSysRoleById(3L));
                    member.setUsername(userName);
                    password = new BCryptPasswordEncoder().encode(password);
                    member.setPassword(password);
                    member.setRoles(list);
                    member.setStuId(stuId);
                    member.setRealName(realName);
                    member.setEntryTime(date);
                    userRepository.save(member);
                    map.put("message", "成功");
                    map.put("code", 2);
                } else {
                    map.put("code", 3);
                    map.put("Message", "数据格式错误");

                }

            } else {
                map.put("code", 4);
                map.put("Message", "用户名错误");

            }

        }


        return map;
    }

    /**
     * 跳转个人信息页面
     *
     * @param username
     * @param model
     * @return
     */
    @RequestMapping(value = "/{username}/personal")
    @PreAuthorize("authentication.name.equals(#username)")//每个人只能进自己的主页
    public String personal(@PathVariable("username") String username, Model model, HttpServletRequest request) {

        Member member = userRepository.findMemberByUsername(username);
        System.out.println("111111111111111111111111111111111111111111111");
        System.out.println(member);

        model.addAttribute("Member", member);
        model.addAttribute("fileName", member.getAvatar());
        return "user/member_center";
    }

    @RequestMapping(value = "/{username}/chengji")
    @PreAuthorize("authentication.name.equals(#username)")//每个人只能进自己的主页
    public String chengji(@PathVariable("username") String username, Model model, HttpServletRequest request) {

//        Member member = userRepository.findMemberByUsername(username);
//        System.out.println("111111111111111111111111111111111111111111111");
//        System.out.println(member);
//
//        model.addAttribute("Member", member);
//        model.addAttribute("fileName", member.getAvatar());

        return "user/member_grade";
    }

    /**
     * 显示个人的成绩
     *
     * @param username
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list_my_grade", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> search(@RequestParam(value = "userName", required = true) String userName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        System.out.println(userName);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Grade> gradeInfos = gradeRepository.findGradeByUserName(userName, pageable);
        List<Grade> gradeslist = gradeInfos.getContent();
        System.out.print(gradeslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", gradeInfos.getTotalElements());
        map.put("data", gradeInfos.getContent());

        return map;
    }


    /**
     * 跳转请假页面
     *
     * @param username
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/{username}/qingjia")
    @PreAuthorize("authentication.name.equals(#username)")//每个人只能进自己的主页
    public String qingjia(@PathVariable("username") String username, Model model, HttpServletRequest request) {


        return "user/member_leave";
    }


    /**
     * 保存修改的个人信息
     */

    @RequestMapping("/proInfo_update")

    public String savepersoal(@RequestParam(value = "username", required = true) String userName,
                              @RequestParam(value = "realName", required = true) String realName,
                              @RequestParam(value = "classname", required = true) String classname,
                              @RequestParam(value = "sex", required = true) String sex,
                              @RequestParam(value = "jianjie", required = true) String jianjie,
                              @RequestParam(value = "phone", required = true) String phone, Model model
    ) {


        Member member = userRepository.findMemberByUsername(userName);
        System.out.println("测试：");
        System.out.println(member);
        if (member != null) {

            if (realName.trim() != null && classname != null && sex != null && phone != null) {

                member.setPhone(phone);
                member.setClassName(classname);
                member.setRealName(realName);
                member.setSex(sex);
                member.setJianjie(jianjie);

                userRepository.save(member);
                model.addAttribute("Member", member);
                model.addAttribute("message", "修改成功");

            }


        }


        return "user/member_center";
    }


    /**
     * 跳转基本信息页面
     */
    @RequestMapping(value = "/proInfo")

    public String proInfo() {


        return "user/member_center";
    }

    /**
     * 跳转基修改密码页面
     */
    @RequestMapping(value = "/makepassword")

    public String makepassword() {


        return "user/makepassword";
    }


    /**
     * 跳转基修改头像
     */
    @RequestMapping(value = "/makephoto")
    public String makephoto() {


        return "user/makephoto";
    }


    @ResponseBody
    @RequestMapping(value = "/check_password")
    public Map<String, Object> check_password(@RequestParam(value = "username", required = true) String userName,
                                              @RequestParam(value = "oldpassword", required = true) String oldpassword) {
        Member member = userRepository.findMemberByUsername(userName);
        Map<String, Object> map = new HashMap<String, Object>();
        if (member != null) {
            String password = member.getPassword();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(oldpassword, password)) {

                map.put("code", 200);
                map.put("message", "正确");
            }


        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/save_password")
    public Map<String, Object> check_password(@RequestParam(value = "username", required = true) String userName,
                                              @RequestParam(value = "password", required = true) String password,
                                              @RequestParam(value = "password1", required = true) String password1,
                                              @RequestParam(value = "oldpassword", required = true) String oldpassword) {
        Member member = userRepository.findMemberByUsername(userName);
        Map<String, Object> map = new HashMap<String, Object>();
        String opassword = member.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        if (!password.trim().equals("") && password.matches("^[\\S]{6,12}$") && password.equals(password1)) {

            if (encoder.matches(oldpassword, password)) {

                password = new BCryptPasswordEncoder().encode(password);
                member.setPassword(password);
                userRepository.save(member);
                map.put("code", 200);
                map.put("message", "success");
            } else {

                map.put("code", 3);
                map.put("message", "faile");

            }
        } else {

            map.put("code", 3);
            map.put("message", "faile");

        }


        return map;
    }

    /**
     * 跳转新闻页面
     *
     * @return
     */
    @RequestMapping(value = "/news")
    public ModelAndView listNews(@RequestParam(value = "start", defaultValue = "0") Integer start,
                                 @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        start = start < 0 ? 0 : start;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, limit, sort);
        Page<News> list = newsRepository.findAll(pageable);
        ModelAndView mview = new ModelAndView("news1");
        mview.addObject("elist", list);
        List<News> list1 = newsRepository.findByStyle("大图");
        mview.addObject("list1", list1);


        return mview;
    }


    /**
     * 单个新闻的详细页面
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/{id}/news_info")

    public String personal(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        News news = newsRepository.findById(id);
        model.addAttribute("News", news);
        int count = news.getCount();
        count = count + 1;
        news.setCount(count);
        newsRepository.save(news);

        return "user/new_info";
    }

    @RequestMapping(value = "/{id}/resource_info")

    public ModelAndView resource_info(@PathVariable("id") Long id, HttpServletRequest request) {
      Resourcess resourcess=resourceRepository.findResourcessById(id);
        ModelAndView modelAndView = new ModelAndView("user/resource_info");
        modelAndView.addObject("resourcess", resourcess);

        return modelAndView;
    }



    @RequestMapping(value = "/listAbout", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ModelAndView listAbout() {
        List<About> list = new ArrayList<>();
        list = aboutRepository.findAll();
        ModelAndView mview = new ModelAndView("about1");
        mview.addObject("elist", list);
        return mview;
    }


    @RequestMapping(value = "/Announcement")
    public ModelAndView Announcement() {
        List<Announcement> list = announcementRepository.findAll();

        ModelAndView mview = new ModelAndView("announcement");
        mview.addObject("list", list);


        return mview;
    }

    /**
     * 资源列表
     * @param start
     * @param limit
     * @return
     */
    @GetMapping("/resource")
    public ModelAndView resource(@RequestParam(value = "start", defaultValue = "0") Integer start,
                                 @RequestParam(value = "limit", defaultValue = "8") Integer limit) {
        start = start < 0 ? 0 : start;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, limit, sort);
        Page<Resourcess> list = resourceRepository.findAll(pageable);
        ModelAndView mview = new ModelAndView("resource1");
        mview.addObject("elist", list);

        return mview;
    }

    /**
     * @param id
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/{id}/announcement_info")

    public ModelAndView announcement_info(@PathVariable("id") Long id, HttpServletRequest request) {
        Announcement announcement = announcementRepository.findAnnouncementById(id);
        ModelAndView modelAndView = new ModelAndView("user/anncouncement_info");
        modelAndView.addObject("announcement", announcement);

        return modelAndView;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/leave_infoo")
    public ModelAndView leave_infoo() {
        ModelAndView modelAndView = new ModelAndView("user/member_leave_info");
        return modelAndView;
    }

    /**
     * @param userName
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list_my_leavess", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> list_my_leavess(@RequestParam(value = "userName", required = true) String userName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        System.out.println(userName);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Leavess> gradeInfos = leaveRepository.findLeavessByUserName(userName, pageable);
        List<Leavess> gradeslist = gradeInfos.getContent();
        System.out.print(gradeslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", gradeInfos.getTotalElements());
        map.put("data", gradeInfos.getContent());

        return map;
    }

    /**
     * 模糊查询资源
     * @param request
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(value = "/search_resource")
    public  ModelAndView search_resource(HttpServletRequest request,@RequestParam(value = "start", defaultValue = "0") Integer start,
                                         @RequestParam(value = "limit", defaultValue = "5") Integer limit){
        String keyword=request.getParameter("keyword");
        start = start < 0 ? 0 : start;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(start, limit, sort);
        Page<Resourcess> list = resourceRepository.searchBykeyword(keyword,pageable);
        ModelAndView mview = new ModelAndView("resource1");
        mview.addObject("elist", list);

        return mview;


    }



}
