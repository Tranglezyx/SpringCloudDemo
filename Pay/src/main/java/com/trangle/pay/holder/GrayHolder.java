package com.trangle.pay.holder;

public class GrayHolder {

    public static final ThreadLocal<Integer> GRAY_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Integer value){
        GRAY_THREAD_LOCAL.set(value);
    }

    public static Integer get(){
        return GRAY_THREAD_LOCAL.get();
    }

    public static void remove(){
        GRAY_THREAD_LOCAL.remove();
    }
}
