package com.unknown.common.security;

import com.unknown.core.codec.digest.DigestUtils;
import com.unknown.core.util.VNETookit;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description	: RSA 签名(私钥和公钥全部采用Base64编码) 但凡存在byte[] 都通过Base转换成String
 * <br>@system_name	:platform
 * <br>@author		:koujiang
 * <br>@create_time	:2019/9/5 1:40
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 */
public class MD5SignUtil {

    public static final String SIGN_KEY = "key";

    /**
    * <br>description :MD5验签名检查
    * <br>@author ： koujiang
    * <br>@param  ： [content, sign, publicKey]
    * <br>@return ： boolean
    * <br>@update ： 2019/9/5 2:13
    * <br>@mender ：(Please add the modifier name)
    * <br>@Modified ：(Please add modification date)
    */
    private static boolean doCheck(SortedMap<String, Object> paramMap, String sign, String privateKey) {
        String signStr = sign(paramMap, privateKey);
        if (signStr.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    //签名
    public static String sign(SortedMap<String, Object> paramMap, String privateKey) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> set = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!VNETookit.isNull(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        sb.append(SIGN_KEY).append("=").append(privateKey);
        return DigestUtils.md5Hex(sb.toString().getBytes()).toUpperCase();
    }
}
