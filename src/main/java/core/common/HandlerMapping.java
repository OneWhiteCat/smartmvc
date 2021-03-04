package core.common;

import core.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 映射处理器类:负责提供请求路径和处理器实例及方法的对应关系
 * 如：请求路径为“/hello.do”，则该请求由HelloController的hello方法执行
 */
public class HandlerMapping {
    //handlerMap用于存放请求路径和处理器实例及其方法的对应关系
    //handler对象封装有处理器实例及其方法
    private Map<String,Handler> handlerMap=new HashMap<>();

    /**
     * 负责建立请求路径和处理器实例及方法的对应关系
     * @param beans   处理器实例组成的集合
     */
    public void process(List beans) {
        //遍历处理器实例组成的集合
        for (Object obj:beans){
            //获得加在类前的注解@RequestMapping
            RequestMapping rc=obj.getClass().getAnnotation(RequestMapping.class);
            //有可能为空
            String path1="";
            if(rc!=null){
                path1=rc.value();
            }
            //获得处理器的方法
            Method[] methods= obj.getClass().getDeclaredMethods();
            //遍历所有方法
            for(Method mh:methods) {
                //获得加在方法前的注解
                RequestMapping rm = mh.getAnnotation(RequestMapping.class);
                if (rm != null) {
                    //获得请求路径
                    String path2= rm.value();
                    //将处理器实例封装到handler
                    Handler handler=new Handler();
                    handler.setObj(obj);
                    handler.setMh(mh);
                    //将请求路径与handler对象对应关系存放起来
                    handlerMap.put(path1+path2,handler);
                }
            }
        }

    }

    /**
     * 依据请求路径返回Handler对象
     * @param key  请求路径
     * @return
     */
    public Handler getHandler(String key){
        return handlerMap.get(key);
    }
}
