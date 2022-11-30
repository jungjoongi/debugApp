package com.weolbu.admin.common.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MD5Generator {
    static private MD5Generator instance;

    public static MD5Generator getInstance() {
        return instance == null ? instance = new MD5Generator() : instance;
    }

/*    public MD5Generator(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest mdMD5 = MessageDigest.getInstance("MD5");
        mdMD5.update(input.getBytes("UTF-8"));
        byte[] md5Hash = mdMD5.digest();
        StringBuilder hexMD5hash = new StringBuilder();
        for(byte b : md5Hash) {
            String hexString = String.format("%02x", b);
            hexMD5hash.append(hexString);
        }
        result = hexMD5hash.toString();
    }*/

    public String makeFileName(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SimpleDateFormat format = new SimpleDateFormat ("yyyyMMddHHmmsss");
        Calendar time = Calendar.getInstance();
        String nowTime = format.format(time.getTime());
        input = input+nowTime;

        MessageDigest mdMD5 = MessageDigest.getInstance("MD5");
        mdMD5.update(input.getBytes("UTF-8"));
        byte[] md5Hash = mdMD5.digest();
        StringBuilder hexMD5hash = new StringBuilder();
        for(byte b : md5Hash) {
            String hexString = String.format("%02x", b);
            hexMD5hash.append(hexString);
        }
        return hexMD5hash.toString();
    }
}