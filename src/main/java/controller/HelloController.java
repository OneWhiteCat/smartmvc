package controller;

import core.annotation.RequestMapping;

/**
 * 处理器类，负责业务逻辑
 */
public class HelloController {

    @RequestMapping("/hello.do")
    public String hello(){
        System.out.println("HelloController's hello()");

        return "hello";
    }

}
