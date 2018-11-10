package cn.liueleven.jwt.utils;

import lombok.Data;

/**
 * @description: 一定要写注释啊
 * @date: 2018-11-09 23:36
 * @author: 十一
 */
@Data
public class Result {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final Integer FAIL = -1;

    private String msg;

    private Integer code;

    private Object data;

    public Result(String msg,Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public Result(Object data) {
        this.msg = "success";
        this.code = 1;
        this.data = data;
    }

}
