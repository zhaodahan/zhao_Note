package com.ec.common.utils;

/**
 * @Description:  定义List过滤基本方法
 */
public interface ListFilterStrategy<T> {

    public boolean contain(T t);
}