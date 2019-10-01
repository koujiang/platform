package com.koujiang.platform.core.common.security;

import com.koujiang.platform.core.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * <br>(c) Copyright koujiang901123@sina.com
 * <br>@description	: RSA 签名(私钥和公钥全部采用Base64编码) 但凡存在byte[] 都通过Base转换成String
 * <br>@system_name	:platform
 * <br>@author		:koujiang
 * <br>@create_time	:2019/9/5 1:40
 * <br>@mender		:(Please add the modifier name)
 * <br>@Modified	:(Please add modification date)
 */
public class RSASignUtil {

    public static final String SIGN_KEY = "sign";

    // 签名算法
    public static final String SIGN_ALGORITHMS = "MD5withRSA";

    /**
    * <br>description :对参数排序拼装
    * <br>@author ： koujiang
    * <br>@param  ： [requestParam]
    * <br>@return ： java.lang.String
    * <br>@update ： 2019/9/5 1:49
    * <br>@mender ：(Please add the modifier name)
    * <br>@Modified ：(Please add modification date)
    */
    public static String toContent(Map<String, Object> params) {
        Map<String, Object> sortedParams = new TreeMap<>();
        if ((params != null) && (params.size() > 0)) {
            sortedParams.putAll(params);
        }
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = sortedParams.get(key);
            if (key != null && !"".equals(key) && value != null && !"".equals(value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }
    /**
    * <br>description :签名
    * <br>@author ： koujiang
    * <br>@param  ： [content, privateKey]
    * <br>@return ： byte[]
    * <br>@update ： 2019/9/5 2:09
    * <br>@mender ：(Please add the modifier name)
    * <br>@Modified ：(Please add modification date)
    */
    private static byte[] sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return signed;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * <br>description :RSA验签名检查
    * <br>@author ： koujiang
    * <br>@param  ： [content, sign, publicKey]
    * <br>@return ： boolean
    * <br>@update ： 2019/9/5 2:13
    * <br>@mender ：(Please add the modifier name)
    * <br>@Modified ：(Please add modification date)
    */
    private static boolean doCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String sign(Map<String, Object> params, String privateKey) {
        String content = toContent(params);
        byte[] sign = sign(content, privateKey);
        String signScr = Base64.encodeBase64String(sign);
        params.put(SIGN_KEY, signScr);
        return signScr;
    }

    public static boolean doCheck(Map<String, Object> params, String publicKey) {
        if (params.containsKey(SIGN_KEY)) {
            String sign = (String) params.remove(SIGN_KEY);
            String content = toContent(params);
            return doCheck(content, sign, publicKey);
        } else {
            //throw new RuntimeException("不存在签名数据");
            return false;
        }
    }
}
