package com.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sell.dataobject.OrderDetail;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import com.sell.utils.EnumUtil;
import com.sell.utils.serializer.DateToLongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data

//在返回订单列表为null的内容不显示
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {


    private String orderId;

    /*买家姓名*/
    private  String buyerName;

    /*买家电话*/
    private  String buyerPhone;

    /*买家地址*/
    private String buyerAddress;

    /*买家微信ID*/
    private  String buyerOpenid;

    /*订单总金额*/
    private BigDecimal orderAmount;

    /*订单状态，默认为新下单*/
    private Integer orderStatus;

    /*支付状态，默认为0 未支付*/
    private Integer payStatus ;

    /*创建时间*/
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    /*更新时间*/
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;


    List<OrderDetail> orderDetailList ;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {

        return EnumUtil.getBycode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {

        return EnumUtil.getBycode(payStatus,PayStatusEnum.class);
    }

}