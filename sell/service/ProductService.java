package com.sell.service;

import com.sell.dataobject.ProductInfo;
import com.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(PageRequest pageable);

    ProductInfo save(ProductInfo productInfo);

    //添加库存
    void increaseStock(List<CartDto> cartDtoList);

    //减少库存
    void decreaseStock(List<CartDto> cartDtoList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);

}
