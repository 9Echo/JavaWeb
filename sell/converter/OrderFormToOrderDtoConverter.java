package com.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sell.dataobject.OrderDetail;
import com.sell.dto.OrderDto;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderFormToOrderDtoConverter {
    public static OrderDto convert(OrderForm orderForm) {

        Gson gson = new Gson();
        OrderDto orderDto = new OrderDto();

        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerPhone(orderForm.getPhone());
   //     orderDto.setBuyerOpenid(orderForm.getOpenid());

        List <OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e ) {
            log.error("【对象转换】错误，string={} ",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        orderDto.setOrderDetailList(orderDetailList);

        return orderDto;

    }

}
