package core.common;

import java.lang.reflect.Method;

/**
 * 为了方便利用java反射机制去调用处理器方法而设计的
 *
 */
public class Handler {
    //obj:存放处理器实例
    private Object obj;
    //mh:存放处理器方法对应的方法对象
    private Method mh;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMh() {
        return mh;
    }

    public void setMh(Method mh) {
        this.mh = mh;
    }
}
