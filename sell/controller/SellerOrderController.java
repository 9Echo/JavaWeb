package com.sell.controller;


import com.sell.dataobject.OrderDetail;
import com.sell.dto.OrderDto;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单列表
     * @param page 第几页，从第1页开始
     * @param size 每一页的数据条数
     * @return
     */

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1,size);
        Page<OrderDto> orderDtoPage = orderService.findList(request);
        map.put("orderDtoPage",orderDtoPage);
        map.put("currentPage",page);
        map.put("size",size);
        orderDtoPage.getContent();
        return new ModelAndView("order/list",map);

    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {

        try {
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.cancel(orderDto);
        } catch (SellException e ) {
            log.error("【卖家端取消订单】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");

        return new ModelAndView("common/success");
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId" ) String orderId,
                               Map<String,Object> map){
        OrderDto orderDto = new OrderDto();
        try {
            orderDto =  orderService.findOne(orderId);
        }catch (SellException e){
            log.error("【卖家端查询订单详情】发生异常{} ",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return  new ModelAndView("common/error",map);
        }
        map.put("orderDto",orderDto);
        return new ModelAndView("order/detail",map);

    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId" ) String orderId,
                                 Map<String,Object> map){
        try {
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.finish(orderDto);
        }catch (SellException e){
            log.error("卖家端完成订单  发生异常{} ",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return  new ModelAndView("common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success");

    }

}
