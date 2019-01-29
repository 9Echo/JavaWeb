package com.sell.service;

import com.sell.dataobject.SellerInfo;

public interface SellerService {

    /**
     * 通过openid查询卖家端信息
     * @param username
     * @return
     */

    SellerInfo findSellerInfoByUsername(String username);


}
