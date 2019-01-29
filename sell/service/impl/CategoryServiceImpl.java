package com.sell.service.impl;

import com.sell.dataobject.ProductCategory;
import com.sell.dataobject.ProductInfo;
import com.sell.repository.ProductCategoryRepository;
import com.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {

        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {

        return  repository.findAll();
    }


    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory){

        return repository.save(productCategory);
    }


}
