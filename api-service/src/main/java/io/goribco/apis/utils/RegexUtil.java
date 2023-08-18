package io.goribco.apis.utils;

import io.goribco.apis.model.exceptions.UrPathNotValidException;

public class RegexUtil {

    public static void validateUrlName(String url, String prefix) throws UrPathNotValidException {
        String regexUrlName = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if (!url.matches(regexUrlName)) {
            throw new UrPathNotValidException(prefix + " url is not valid format");
        }
    }
}
