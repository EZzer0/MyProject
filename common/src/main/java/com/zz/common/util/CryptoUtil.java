package com.zz.common.util;

import cn.hutool.crypto.digest.Digester;

import java.security.SecureRandom;
import java.util.Base64;

public class CryptoUtil {

    /**
     * 生成盐值
     *
     * @return 生成的盐值，Base64编码字符串
     */
    public static String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 使用SHA-256和盐值生成密码哈希
     *
     * @param password 密码
     * @param salt     盐值
     * @return 密码的哈希值，包括盐
     */
    public static String hashPassword(String password, String salt) {
        Digester digester = new Digester("SHA-256");
        digester.setSalt(salt.getBytes());
        return salt + ":" + digester.digestHex(password);
    }

    /**
     * 验证密码与哈希值是否匹配
     *
     * @param password         用户提供的密码
     * @param fullHashWithSalt 包含盐的完整哈希字符串
     * @return 如果密码匹配返回true，否则返回false
     */
    public static boolean verifyPassword(String password, String fullHashWithSalt) {
        String[] parts = fullHashWithSalt.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("哈希格式不正确，必须包含盐值和哈希");
        }
        String salt = parts[0];
        String storedHash = parts[1];

        Digester digester = new Digester("SHA-256");
        digester.setSalt(salt.getBytes());
        String computedHash = digester.digestHex(password);
        System.out.println("computedHash: " + computedHash);
        System.out.println("storedHash: " + storedHash);
        return storedHash.equals(computedHash);
    }
}
