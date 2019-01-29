package com.sell.VO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * http请求返回的最外层对象
 */
@Data

public class ResultVO<T> {

    /** 错误码 */
    private Integer code;

    /** 提示信息 */
    private String msq;

    /** 具体的返回内容 */
    private T data;


}
