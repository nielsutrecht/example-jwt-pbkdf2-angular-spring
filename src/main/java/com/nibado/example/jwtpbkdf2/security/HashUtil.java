package com.nibado.example.jwtpbkdf2.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * PBKDF2 functions based on https://gist.github.com/jtan189/3804290
 */
public class HashUtil {
    private static final int ITERATION_INDEX = 0;
    private static final int SALT_INDEX = 1;
    private static final int HASH_INDEX = 2;

    private static final String SEPARATOR = ":";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final String algorithm;
    private final int saltBytes;
    private final int hashBytes;
    private final int iterations;

    public HashUtil(String algorithm, int saltBytes, int hashBytes, int iterations) {
        this.algorithm = algorithm;
        this.saltBytes = saltBytes;
        this.hashBytes = hashBytes;
        this.iterations = iterations;
    }

    public String createHash(String password) {
        byte[] salt = new byte[saltBytes];
        RANDOM.nextBytes(salt);
        byte[] hash;
        try {
            hash = pbkdf2(password.toCharArray(), salt, iterations, hashBytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return iterations + SEPARATOR + toBase64(salt) + SEPARATOR +  toBase64(hash);
    }

    public boolean validatePassword(String password, String goodHash) {
        String[] params = goodHash.split(SEPARATOR);
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromBase64(params[SALT_INDEX]);
        byte[] hash = fromBase64(params[HASH_INDEX]);

        byte[] testHash;
        try {
            testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
        return skf.generateSecret(spec).getEncoded();
    }

    private static byte[] fromBase64(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    private static String toBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }
}