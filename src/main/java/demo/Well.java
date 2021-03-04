package demo;

/**
 * 饿汉式单例
 * 即该类的实例是在类加载期间创建的
 * 优点:没有线程安全问题，
 * 缺点:有可能造成资源浪费，
 */
public class Well {
    private static Well well=new Well();

    private Well(){
        System.out.println("私有构造器");
    }

    public static Well getInstance(){
        return well;
    }

}
