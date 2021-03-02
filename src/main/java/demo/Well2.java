package demo;

/**
 * 懒汉式单例
 * 当外界调用的时候，才会创建实例
 * 优点:不造成资源的浪费
 * 缺点:存在线程安全问题，
 */
public class Well2 {
    private static Well2 well;

    private Well2(){
        System.out.println("Well2私有构造器");
    }
    public synchronized static Well2 getInstance(){
        if(well==null){
            well=new Well2();
        }
        return well;
    }

}
