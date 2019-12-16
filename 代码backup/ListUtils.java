package com.ec.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;


public final class ListUtils {
    /**
     * 根据条件对List进行过滤
     * @param list
     * @param strategy
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, ListFilterStrategy<T> strategy) {
        List<T> rList = new ArrayList<T>();
        if(CollectionUtils.isNotEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                T t =  list.get(i);
                if (strategy.contain(t)){
                    rList.add(t);
                }
            }
        }
        ((ArrayList<T>) rList).trimToSize();//释放申请的多余的空间
        return rList;
    }

//    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i <10; i++) {
//            list.add(i);
//        }
//        final List<Integer> alist = Arrays.asList(1, 2, 3);
//        List<Integer> rList=filter(list, new ListFilterStrategy<Integer>() {
//            @Override
//            public boolean contain(Integer integer) {
//                return alist.contains(integer);
//            }
//        });
//        System.out.println(rList.size());
//    }
}  
