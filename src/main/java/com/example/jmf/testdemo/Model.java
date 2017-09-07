package com.example.jmf.testdemo;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jmfstart on 2016/11/1.
 * 模型层全局类
 */
public class Model {

    private static Model model = new Model();

    //创建全局线程池
    private ExecutorService executor = Executors.newCachedThreadPool();//不用时自动回收
    private Context mContext;

    //构造方法私有化
    private Model(){

    }
    //单例
    public static Model getInstance(){
        return model;
    }

    //初始化
    public void init(Context context){
        mContext = context;
    }

    /**
     * 获取全局线程池
     * @return
     */
    public ExecutorService getGlobalThreadPool(){
        return executor;
    }
}
