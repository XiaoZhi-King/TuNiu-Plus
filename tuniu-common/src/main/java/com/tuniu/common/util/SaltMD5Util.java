package com.tuniu.common.util;

import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5 加盐加密工具。
 * <p>保留与旧版完全一致的算法，确保数据库中已存在的密码仍可校验通过。
 * 盐为 16 位随机数字，混入 32 位 MD5 中得到 48 位密文。</p>
 */
public final class SaltMD5Util {

    private SaltMD5Util() {
    }

    /**
     * 生成普通 MD5（32 位小写十六进制）
     */
    public static String MD5(String input) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(input.getBytes());
            return toHex(md5Bytes);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 生成盐并加盐后的 MD5 码，将盐混入 MD5 码中（48 位）
     */
    public static String generateSaltPassword(String password) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = stringBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                stringBuilder.append("0");
            }
        }
        String salt = stringBuilder.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            cs[i + 1] = salt.charAt(i / 3);
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 验证明文与加盐 MD5 是否匹配
     */
    public static boolean verifySaltPassword(String password, String md5) {
        if (md5 == null || md5.length() != 48) {
            return false;
        }
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }

    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return toHex(bs);
        } catch (Exception e) {
            return null;
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            int val = b & 0xff;
            if (val < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }
}
