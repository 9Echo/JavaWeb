package com.sell.Handle;


import com.sell.VO.ResultVO;
import com.sell.exception.SellException;
import com.sell.exception.SellerAuthorizeException;
import com.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class SellExceptionHandler {

    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"+"/seller/login/index");
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        log.info("错误码：{}，错误信息：{}",e.getCode(),e.getMessage());

        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
}
