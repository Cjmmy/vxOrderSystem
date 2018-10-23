package com.cjmmy.vxordersystem.VO;


import lombok.Data;


@Data
public class ResultVO<T> {
    private int code;
    private String msg;
    private T data;
}
