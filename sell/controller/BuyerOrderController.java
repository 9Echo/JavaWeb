package com.sell.controller;


import com.sell.VO.ResultVO;
import com.sell.converter.OrderFormToOrderDtoConverter;
import com.sell.dto.OrderDto;
import com.sell.enums.ResultEnum;
import com.sell.exception.SellException;
import com.sell.form.OrderForm;
import com.sell.service.BuyerService;
import com.sell.service.OrderService;
import com.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    // 6-10

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】 参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDto orderDto = OrderFormToOrderDtoConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        //创建
        OrderDto createResult = orderService.create(orderDto);

        //对应返回格式
        Map<String, String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtil.success(map);

    }

    // 订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDto>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderDto> orderDtoPage = orderService.findList(openid, request);

        return ResultVOUtil.success(orderDtoPage.getContent());

    }

    // 订单详情

    @GetMapping("/detail")
    public ResultVO<OrderDto> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {

        //逻辑放在service层 保证其安全性
        OrderDto orderDto = buyerService.findOrderOne(openid, orderId);

        return ResultVOUtil.success(orderDto);
    }

    // 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {


        buyerService.cancelOrder(openid, orderId);

        return ResultVOUtil.success();
    }
}
