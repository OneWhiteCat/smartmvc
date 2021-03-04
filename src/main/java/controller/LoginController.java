package controller;

import core.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController {
    @RequestMapping("/toLogin.do")
    public String toLogin(){

        return "login";
    }
    @RequestMapping("/login.do")
    public String login(HttpServletRequest request) {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(username+","+password);
        if("张三".equals(username)&&"test".equals(password)){
            return "redirect:welcome.do";
        }
        request.setAttribute("login_msg","登陆失败");
        return "login";
    }
    @RequestMapping("/welcome.do")
    public String welcome(){

        return "welcome";
    }
}
