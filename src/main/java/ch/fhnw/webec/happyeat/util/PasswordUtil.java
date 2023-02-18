package ch.fhnw.webec.happyeat.util;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public final class PasswordUtil {

    private PasswordUtil() { }

    public static String encodePassword(String password) {
        var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(password);
    }

}
