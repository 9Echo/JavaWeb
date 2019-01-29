package com.sell.service.impl;

import com.sell.dataobject.ProductInfo;
import com.sell.dto.CartDto;
import com.sell.enums.ProductStatusEnum;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.repository.ProductInfoRepository;
import com.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(PageRequest pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
    @Override
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto:cartDtoList) {
            ProductInfo productInfo = repository.findOne(cartDto.getProductId());
            if (productInfo == null ){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDto.getProductQuantity();
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        // 遍历购物车
        for (CartDto cartDto: cartDtoList) {
            ProductInfo productInfo = repository.findOne(cartDto.getProductId());
            if (productInfo == null ){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if( result < 0 ){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }


    }

    @Override
    public ProductInfo onSale(String productId) {

        ProductInfo productInfo=  repository.findOne(productId);
        if (productInfo == null){
            //商品不存在
            throw new  SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            //商品为上架状态
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return   repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {

        ProductInfo productInfo=  repository.findOne(productId);
        if (productInfo == null){
            throw new  SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return   repository.save(productInfo);
    }


}
