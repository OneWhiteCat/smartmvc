package demo;

import java.util.TreeSet;

public class ArrayDemo {
    public static void main(String[] args) {
        int[] ary1 = {5, 4, 3, 2, 1};
        int[] ary2 = {2, 3, 4, 5, 6, 7, 8};
        //创建TreeSet对象
        TreeSet<Object> set1 = new TreeSet<>();
        TreeSet<Object> set2 = new TreeSet<>();
        //遍历数组赋值给set
        for (int i : ary1) {
            set1.add(i);
        }
        for (int i : ary2) {
            set2.add(i);
        }
        //以下代码求并集
//        set1.addAll(set2);
//        for(Object o:set1) {
//            System.out.print(o+" ");
//        }
        //以下代码求差集
        TreeSet<Object> min = set1.size() < set2.size() ? set1 : set2;//最短集合
        TreeSet<Object> max = min == set1 ? set2 : set1;//最长集合
        //遍历min,判断元素是否在max中存在
        for (Object obj : min) {
            if (max.contains(obj)) {
                max.remove(obj);
            } else {
                max.add(obj);
            }
        }
        for (Object o : max) {
            System.out.println(o + " ");
        }

    }
}

