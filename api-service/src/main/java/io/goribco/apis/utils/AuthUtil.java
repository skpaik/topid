package io.goribco.apis.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Stream;

public class AuthUtil {


    public static String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return "";

        return auth.getName();
    }


    public static String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getName();
    }


    public static String[] concatStringArray(String[]... arrays) {
        return Stream.of(arrays)
                .flatMap(Stream::of)        // or, use `Arrays::stream`
                .toArray(String[]::new);
    }
}