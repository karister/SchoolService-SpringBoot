package schoolservice.controller;

import com.alibaba.fastjson.JSON;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.event.EventRecodingLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import schoolservice.mybatis.entity.Student;
import schoolservice.service.StudentService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author karister
 * @create 2021-07-24 18:44
 */

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private StudentService studentService;

    Map<String, Object> map = new HashMap<String, Object>();


    @RequestMapping("/tologin")
    String tologin(HttpSession session){ return "login"; }

    @RequestMapping("/torepair")
    String torepair(){ return "repair"; }

    @RequestMapping("/toregister")
    String toregister(){ return "register"; }

    @RequestMapping("/tosstudent")
    String tosstudent(){return "student";}

    @RequestMapping("/upLoadTest")
    String uploadTest(){return "uploadTest";}

    @GetMapping("/settype")
    @ResponseBody
    String settype(HttpServletRequest request){
        String type = request.getParameter("type");
        request.getSession().setAttribute("type",type);
        return (type.equals("1")) ? "学生登录" : "管理员登录";
    }

    @GetMapping("/gettype")
    @ResponseBody
    String gettype(HttpServletRequest request){
        String msg = (String) request.getSession().getAttribute("type");
        return JSON.toJSONString(msg);
    }

    //注册学号验证
    @RequestMapping("/check")
    @ResponseBody
    String checkStuID(String stuid){
        Student student = studentService.queryStuByStuID(stuid);
        if (student != null) {
            map.put("code", 210);
            map.put("msg", "");
        }
        else
        {
            map.put("code", 0);
        }
        String result = JSON.toJSONString(map);
        System.out.println(result);
        return result;
    }

    //登录验证，信息写入session
    @RequestMapping("/login")
    @ResponseBody
    String login(HttpServletRequest request, HttpServletResponse response,Model model) {
        //从请求中获取提交的信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        System.out.println(remember);
        Student student = studentService.queryStuByName(username);
        //AJAX返回验证json
        if (student == null) {
            map.put("code", 200);
            map.put("msg", "用户名无效！");
        } else if (!student.getUserPass().equals(password)) {
            map.put("code", 201);
            map.put("msg", "密码错误！");
        } else {
            //登录成功
            map.put("code", 100);
            map.put("msg", "");
            //添加session
            request.getSession().setAttribute("student", student);
            //添加cookie(记住密码功能)
            if (remember.equals("true")) {
                //创建两个Cookie对象
                Cookie nameCookie = new Cookie("username", username);
                //设置Cookie的有效期为1天
                nameCookie.setMaxAge(60*60*24);
                Cookie pwdCookie = new Cookie("password", password);
                pwdCookie.setMaxAge(60*60*24);
                response.addCookie(nameCookie);
                response.addCookie(pwdCookie);
                System.out.println("cookie已创建");
            }

        }
        String result = JSON.toJSONString(map);
        System.out.println("******************************");
        System.out.println(result);
        System.out.println("******************************");
        return result;
    }

    //退出登录
    @RequestMapping("/logout")
    String logout(HttpServletRequest request){
        //删除session
        request.getSession().removeAttribute("student");
        if (request.getSession().getAttribute("student") == null) {
            System.out.println("用户已退出");
        }
        return "login";
    }

    //前台获取session数据
    @RequestMapping("/getsn")
    @ResponseBody
    String getSession(HttpSession session){
        Map<String, Object> sessionMap = new HashMap<String, Object>();
        Student student = (Student) session.getAttribute("student");
        //session存在，用户未注销
        if(student != null){
            sessionMap.put("name",student.getName());
            sessionMap.put("stuid",student.getId());
            sessionMap.put("faculty",student.getFaculty());
            sessionMap.put("classs",student.getStuClass());
            sessionMap.put("phone",student.getPhone());
            sessionMap.put("code",1);
        }
        else
            sessionMap.put("code",0);
        String result = JSON.toJSONString(sessionMap);
        System.out.println(result);
        return result;
    }
}
