package org.freelasearch.utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {

    public static String convertTo(String password) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes(), 0, password.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5.convertTo", e.getMessage(), e);
        }
        return null;
    }
}
