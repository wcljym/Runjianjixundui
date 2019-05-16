package com.wangchenglong.graduation.contorller;

import com.wangchenglong.graduation.domain.Leavess;
import com.wangchenglong.graduation.domain.Member;
import com.wangchenglong.graduation.repository.LeaveRepository;
import com.wangchenglong.graduation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * @create: 2019-03-15 15:49
 **/
@Controller
public class LeaveContorller {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LeaveRepository leaveRepository;

    @ResponseBody
    @RequestMapping(value = "/save_leave", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_news(
            @RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "image", required = true) String image,
            @RequestParam(value = "date", required = true) String time,
            @RequestParam(value = "remarks", required = false) String remarks

    ) {
        Map<String, Object> map = new HashMap<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Member member = userRepository.findMemberByUsername(userName);
        Leavess leave = new Leavess();
        leave.setClassName(member.getClassName());
        leave.setImage(image);
        leave.setRealName(member.getRealName());
        leave.setRemarks(remarks);
        leave.setStar_date(date);
        leave.setUserName(userName);
        leave.setState(0);
        leave.setStuId(member.getStuId());
        leaveRepository.save(leave);

        map.put("code", 200);
        map.put("message", "添加成功");
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/listAll_leavess", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, Object> listAll_grade(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int page = Integer.parseInt(request.getParameter("page"));
        System.out.print(page);
        int limit = Integer.parseInt(request.getParameter("limit"));
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Leavess> gradesInfos = leaveRepository.findAll(pageable);
        List<Leavess> gradeslist = gradesInfos.getContent();
        System.out.print(gradeslist.size());
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", gradesInfos.getTotalElements());
        map.put("data", gradesInfos.getContent());

        return map;


    }
    @ResponseBody
    @RequestMapping(value = "/allow_leaves", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Map<String, Object> save_news(
            @RequestParam(value = "id", required = true) Long id

    ) {
        Map<String, Object> map = new HashMap<>();
        Leavess leavess=leaveRepository.findLeavessById(id);
        if (leavess.getState()==1){
            map.put("code", 404);
            map.put("message", "已成功");

        }else{

            leavess.setState(1);
            leaveRepository.save(leavess);
            map.put("code", 200);
            map.put("message", "批准成功");

        }


        return map;
    }

    @GetMapping("/{id}/search_leavess")
    public ModelAndView list_us_info(@PathVariable("id") Long id) {
        Leavess leavess=leaveRepository.findLeavessById(id);
        ModelAndView modelAndView = new ModelAndView("admin/leavess/leavess_info");
        modelAndView.addObject("Leavess",leavess);
        return modelAndView;
    }

}
