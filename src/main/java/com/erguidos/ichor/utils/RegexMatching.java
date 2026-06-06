package com.erguidos.ichor.utils;

import java.util.regex.Pattern;

public final class RegexMatching {
    private RegexMatching() {
        throw new UnsupportedOperationException("Don't instantiate RegexMatching");
    }
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]{10,50}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,127}$");
    

    private static boolean match(Pattern pattern, String toVerify) {
        return pattern.matcher(toVerify).matches();
    }
    
    public static boolean isValidUsername(String username) {
        if (username == null) { return false; }
        return RegexMatching.match(USERNAME_PATTERN, username);
    }
    
    public static boolean isValidPassword(String password) {
        if (password == null) { return false; }
        return RegexMatching.match(PASSWORD_PATTERN, password);
    }
}
