package core.web;

import core.common.ResourceManager;
import demo.HelloService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DispatcherServlet",urlPatterns = "*.do",loadOnStartup = 1)
/**
 * urlPatterns属性设置该servlet对应的请求
 * "*.do"匹配所有.do结尾的请求(后缀匹配)
 *
 */
public class DispatcherServlet extends HttpServlet {

    public DispatcherServlet(){
        System.out.println("DispatcherServlet's constructor()");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("DispatcherServlet's init()");
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DispatcherServlet's service()");
        //获得请求路径
        String path=request.getServletPath();
        //依据路径调用相应的模型
        if("/hello.do".equals(path)){
            HelloService helloService=new HelloService();
            String msg=helloService.hello();
            //依据模型返回的处理结果，调用相应的视图来展示
            TemplateEngine engine= ResourceManager.getInstance(getServletContext()).getEngine();
            /*
                step4.调用模板引擎的方法来处理模板文件
             */
            //webContext 用来为模板引擎提供数据
            WebContext ctx=new WebContext(request,response,getServletContext());
            //将数据绑定到WebContext
            ctx.setVariable("msg",msg);
            response.setContentType("text/html;charset=utf-8");//设置mime类型,字符集是utf-8
            /*
                reocess方法会利用前缀+“hello”+后缀找到模板文件
                然后利用ctx获得数据，生成相应的动态页面
             */
            engine.process("hello",ctx,response.getWriter());


        }

    }


}
