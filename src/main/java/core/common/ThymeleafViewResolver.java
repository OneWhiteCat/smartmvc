package core.common;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ThymeleafViewResolver {

    /**
     * 用来处理视图名的方法
     * @param viewName
     * @param request
     * @param response
     * @param sctx  ServletContext
     * @throws IOException
     */
    public void processViewName(String viewName, HttpServletRequest request,
                                HttpServletResponse response, ServletContext sctx) throws IOException {
        //如果视图名是以redirect开头则重定向
        if(viewName.startsWith("redirect:")){
            //获得重定向地址
            String redirectPath=request.getContextPath()+"/"+viewName.substring("redirect:".length());
            //重定向
            response.sendRedirect(redirectPath);
        }else {
            //依据模型返回的处理结果，调用相应的视图来展示
            TemplateEngine engine= ResourceManager.getInstance(sctx).getEngine();
            //webContext 用来为模板引擎提供数据
            WebContext ctx=new WebContext(request,response,sctx);
            //将数据绑定到WebContext
            response.setContentType("text/html;charset=utf-8");//设置mime类型,字符集是utf-8
            /*
                reocess方法会利用前缀+视图名+后缀找到模板文件
                然后利用ctx获得数据，生成相应的动态页面
             */
            engine.process(viewName,ctx,response.getWriter());
        }
    }
}
