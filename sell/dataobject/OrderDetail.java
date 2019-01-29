package com.sell.dataobject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetail {

    @Id
    private String detailId;

    /*订单ID*/
    private String orderId;

    /*商品ID*/
    private String productId;

    /*商品名称*/
    private String productName;

    /*商品单价*/
    private BigDecimal productPrice;

    /*商品数量*/
    private Integer productQuantity;

    /*商品图标*/
    private String productIcon;


}
