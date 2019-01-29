package com.sell.utils;

import com.sell.enums.CodeEnum;

public class EnumUtil {

    public static <T extends CodeEnum> T getBycode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
