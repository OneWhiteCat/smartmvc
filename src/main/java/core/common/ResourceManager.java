package core.common;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

/**
 * 资源管理器类
 * 负责对系统的资源进行管理，采用单例模式
 */
public class ResourceManager {
    private static ResourceManager resourceManager;
    private TemplateEngine engine;
    public TemplateEngine getEngine() {
        return engine;
    }
    /**
     * 系统资源都可以在构造器当中创建
     * 因为该构造器只会调用一次,所以这些系统资源也只创建一次
     */
    private ResourceManager(ServletContext sctx){
        System.out.println("ResourceManager's constructor()");
        /*1.创建模板解析器  ,getServletContext()方法来自GenericServlet(该类是HttpServlet的父类)
            该方法的作用获得ServletContext*/
        ServletContextTemplateResolver sctr=new ServletContextTemplateResolver(sctx);
        //2.给解析器设置一些特性
        sctr.setTemplateMode(TemplateMode.HTML);//要处理的文件类型是html
        sctr.setPrefix("/WEB-INF/");     //前缀
        sctr.setSuffix(".html");         //后缀
        sctr.setCharacterEncoding("utf-8");//编解码字符集
        //在开发阶段，最好不要使用缓存
        sctr.setCacheable(false);//是否使用缓存
            /*
                step3.创建模板引擎
             */
        engine=new TemplateEngine();
        engine.setTemplateResolver(sctr);

    }

    public synchronized static ResourceManager getInstance(ServletContext sctx){
        if(resourceManager==null){
            resourceManager=new ResourceManager(sctx);
        }
        return resourceManager;
    }

}
