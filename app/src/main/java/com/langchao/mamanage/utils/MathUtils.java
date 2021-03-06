package com.langchao.mamanage.utils;

import java.math.BigDecimal;

/**
 * Created by miaohl on 2016/6/29.
 */
public class MathUtils {

    public static String scaleDouble(double num){
        if(num == 0){
            return "0";
        }
        BigDecimal n2 = new BigDecimal(num);
        return n2.setScale(5,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static String scaleDouble4(double num){
        if(num == 0){
            return "0";
        }
        BigDecimal n2 = new BigDecimal(num);
        return n2.setScale(5,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }
}
