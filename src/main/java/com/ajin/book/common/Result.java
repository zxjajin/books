package com.ajin.book.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ajin
 * @create 2023-10-09 22:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    private int code;//200是正常，非200表示异常
    private String msg;
    private Object data;

    public static Result succ(Object data){
        return succ(200,"操作成功",data);
    }
    public static Result succ(int code,String msg,Object data){
        return new Result(code,msg,data);
    }
    public static Result fail(String msg){
        return fail(msg,null);
    }
    public static Result fail(String msg,Object data){
        return fail(400,msg,data);
    }
    public static Result fail(int code,String msg,Object data){
        return new Result(code,msg,data);
    }
}
