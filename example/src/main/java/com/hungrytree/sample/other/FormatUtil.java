package com.hungrytree.sample.other;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by wp.nine on 2018/1/9.
 */

public class FormatUtil {
    /**
     * 价格除100得到真实价格
     */
    public static String formatPrice(long price) {
        BigDecimal totalPrice = str2BigDecimal(price + "").multiply(str2BigDecimal(0.01 + ""));
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        return decimalFormat.format(totalPrice.doubleValue());

    }


    public static BigDecimal str2BigDecimal(String arg) {
        if (TextUtils.isEmpty(arg)) {
            return BigDecimal.ZERO;
        }

        BigDecimal bigDecimal = null;
        try {
            bigDecimal = new BigDecimal(arg);
            bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            return bigDecimal;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}
