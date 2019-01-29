package com.sell.controller;


import com.sell.constant.CookieConstant;
import com.sell.constant.RedisConstant;
import com.sell.enums.ResultEnum;
import com.sell.form.SellerForm;
import com.sell.service.SellerService;
import com.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login/index")
    public ModelAndView loginindex(){
        ModelAndView modelAndView = new ModelAndView("seller/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid SellerForm form,
                              BindingResult bindingResult,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        //1.与数据库中的username相匹配
       /* SellerInfo sellerInfo = sellerService.findSellerInfoByUsername(username);
        if (sellerInfo == null ) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error");
        }*/
        if(sellerService.findSellerInfoByUsername(form.getUsername())!=null && sellerService.findSellerInfoByUsername(form.getUsername()).getPassword().equals(form.getPassword())
                &&sellerService.findSellerInfoByUsername(form.getUsername()).getUsername().equals(form.getUsername())){
            String openid = sellerService.findSellerInfoByUsername(form.getUsername()).getOpenid();

        //2.设置token至redis
            /*String token = UUID.randomUUID().toString();
            Integer expire = RedisConstant.EXPIRE;

            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX), openid, expire, TimeUnit.SECONDS);*/

        //3.设置token至cookis
            /* CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
             return new ModelAndView("redirect:"+"/seller/order/list");*/
        }else {
            map.put("msg","账号或密码错误");
            map.put("url","/login/index");
            return new ModelAndView("common/error",map);
        }
        return new ModelAndView("redirect:"+"/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                HttpServletResponse response,
                Map<String,Object> map) {

            //1.从cookie查询
            Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
            if (cookie != null) {
                //2.清除redis
                redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
                //3.清除Cookie
                CookieUtil.set(response,CookieConstant.TOKEN,null,0);
            }
            map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
            map.put("url","/sell/seller/login/index");
            return new ModelAndView("common/success",map);

        }
}
