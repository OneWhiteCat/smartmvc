package core.web;

import core.common.Handler;
import core.common.HandlerMapping;
import core.common.ResourceManager;
import demo.HelloService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
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
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DispatcherServlet",urlPatterns = "*.do",loadOnStartup = 1)
/**
 * urlPatterns属性设置该servlet对应的请求
 * "*.do"匹配所有.do结尾的请求(后缀匹配)
 *
 */
public class DispatcherServlet extends HttpServlet {
        private HandlerMapping handlerMapping;

    public DispatcherServlet(){
        System.out.println("DispatcherServlet's constructor()");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("DispatcherServlet's init()");
        SAXReader saxReader=new SAXReader();
        //构造一个文件输入流
        InputStream in=getClass().getClassLoader().getResourceAsStream("smartmvc.xml");
        try {
            Document doc=saxReader.read(in);
            //找到根节点
            Element root=doc.getRootElement();
            //找到根节点下面所有的子节点
            List<Element> elementList=root.elements("bean");
            //beans集合存放处理器实例
            List beans=new ArrayList();
            //遍历子节点
            for(Element element:elementList){
                //获得处理器类名
                String className=element.attributeValue("class");
                //将处理器实例化
                Object obj=Class.forName(className).newInstance();
                //将处理器实例保存起来
                beans.add(obj);
            }
            handlerMapping =new HandlerMapping();
            //将处理器实例交给HandlerMapping处理
            handlerMapping.process(beans);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DispatcherServlet's service()");
        //获得请求路径
        String path=request.getServletPath();
        //依据HandlerMapping提供的信息来调用相应的处理器来处理请求
        Handler handler=handlerMapping.getHandler(path);
        //handler对象可能不存在
        if(handler==null){
            response.sendError(404);
            return;
        }
        //通过handler对象获得处理器实例
        Object obj=handler.getObj();
        //通过handler对象获得方法对象
        Method mh=handler.getMh();
        //viewName是视图名
        String viewName;
        try {
            //调用处理器的方法,rv是处理器方法的返回值
            Object rv=mh.invoke(obj);
            //获得视图名
            viewName=rv.toString();
            //依据模型返回的处理结果，调用相应的视图来展示
            TemplateEngine engine= ResourceManager.getInstance(getServletContext()).getEngine();
            //webContext 用来为模板引擎提供数据
            WebContext ctx=new WebContext(request,response,getServletContext());
            //将数据绑定到WebContext
            ctx.setVariable("msg","Hello");
            response.setContentType("text/html;charset=utf-8");//设置mime类型,字符集是utf-8
            /*
                reocess方法会利用前缀+视图名+后缀找到模板文件
                然后利用ctx获得数据，生成相应的动态页面
             */
            engine.process(viewName,ctx,response.getWriter());

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }


    }


}
