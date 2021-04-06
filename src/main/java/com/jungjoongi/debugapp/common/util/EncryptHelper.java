package com.jungjoongi.debugapp.common.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class EncryptHelper {

    public static Key getAESKey(String key) throws Exception {
        String iv;
        Key keySpec;

        iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");

        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }

        System.arraycopy(b, 0, keyBytes, 0, len);
        keySpec = new SecretKeySpec(keyBytes, "AES");

        return keySpec;
    }

    // 암호화
    public static String encAES(String str, String iv, String key) throws Exception {
        Key keySpec = getAESKey(key);
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.getEncoder().encode(encrypted));

        return enStr;
    }

    // 복호화
    public static String decAES(String enStr, String iv, String key) throws Exception {
        Key keySpec = getAESKey(key);
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
        byte[] byteStr = Base64.getDecoder().decode(enStr.getBytes("UTF-8"));
        String decStr = new String(c.doFinal(byteStr), "UTF-8");

        return decStr;
    }

}
