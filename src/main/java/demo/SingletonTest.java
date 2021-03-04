package demo;

public class SingletonTest {

    public static void main(String[] args) {
        Well well1=Well.getInstance();
        Well well2=Well.getInstance();

        System.out.println(well1==well2);
    }

}
