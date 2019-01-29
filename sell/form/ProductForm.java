package com.sell.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {

    private  String productId;

    /*商品名称*/
    private  String productName;
    /*商品价格*/
    private BigDecimal productPrice;
    /*库存*/
    private  Integer productStock;
    /*描述*/
    private String productDescription;
    /*商品小图标*/
    private String productIcon;
    /*类目编号*/
    private Integer categoryType;
}
