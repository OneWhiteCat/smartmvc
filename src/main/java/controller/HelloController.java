package controller;

import core.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理器类，负责业务逻辑
 */
public class HelloController {

    @RequestMapping("/hello.do")
    public String hello(HttpServletRequest request){
        System.out.println("HelloController's hello()");
        //将数据绑定到request
        request.setAttribute("msg","Hello,SmartMVC!!!");
        return "hello";
    }

}
