package com.erguidos.ichor.utils;

import java.util.regex.Pattern;

public final class RegexMatching {
    private RegexMatching() {
        throw new UnsupportedOperationException("Don't instantiate RegexMatching");
    }
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]{10, 50}");
    
    public static boolean isValidUsername(String username) {
        if (username == null) { return false; }
        return RegexMatching.match(USERNAME_PATTERN, username);
    }
    
    private static boolean match(Pattern pattern, String toVerify) {
        return pattern.matcher(toVerify).matches();
    }
}
