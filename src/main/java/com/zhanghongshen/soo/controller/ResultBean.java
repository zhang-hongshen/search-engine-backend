package com.zhanghongshen.soo.controller;

import lombok.Data;

@Data
public class ResultBean<T>{
    public static final int NO_LOGIN = -1;
    public static final int FAIL = 0;
    public static final int SUCCESS = 1;
    public static final int NO_PERMISSION = 2;
    private String message = "success";
    private int code = SUCCESS;
    private T data;
    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.message = e.toString();
        this.code = FAIL;
    }
}