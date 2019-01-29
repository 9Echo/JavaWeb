package com.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class CategoryForm {

    private Integer categoryId;
    /*类目名称*/
    @NotEmpty(message = "类目名称必填")
    private String categoryName;
    /*类目编号*/
    @NotNull(message = "类目编号必填")
    private  Integer categoryType;
}
