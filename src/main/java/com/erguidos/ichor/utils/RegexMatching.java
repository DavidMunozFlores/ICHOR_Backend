package com.erguidos.ichor.utils;

import java.util.regex.Pattern;

public final class RegexMatching {
    private RegexMatching() {
        throw new UnsupportedOperationException("Don't instantiate RegexMatching");
    }
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]{10,50}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,127}$");
    
    // Regex breakdown:
    // 1. ^(?i)(?:Calle|C\/|Avda|Avenida|Paseo|Plaza|Carretera|Ctra|Hospital)[\s\wáéíóúüñÁÉÍÓÚÜÑ.,ºª-]{5,100} -> Matches common street/hospital prefixes and names
    // 2. \d+[\s\wºª-]* -> Matches the street number and optional floor/door info (e.g., 12, 45B, 3ºA)
    // 3. (?:,\s*)? -> Optional comma and space separator
    // 4. (?:0[1-9]|[1-4]\d|5[0-2])\d{3} -> Matches valid Spanish postal codes (01000 to 52999)
    // 5. \s+[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\s.-]{2,50}$ -> Matches the city/municipality name
    private static final Pattern ADDRESS_PATTERN = Pattern.compile(
          "^(?i)(?:Calle|C/|Avda|Avenida|Paseo|Plaza|Carretera|Ctra|Hospital)[\\s\\wáéíóúüñÁÉÍÓÚÜÑ.,ºª-]{5,100}"
        + "\\d+[\\s\\wºª-]*"
        + "(?:,\\s*)?"
        + "(?:0[1-9]|[1-4]\\d|5[0-2])\\d{3}"
        + "\\s+[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\s.-]{2,50}$"
    );
    
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
    
    public static boolean isValidAddress(String address) {
        if (address == null) { return false; }
        return RegexMatching.match(ADDRESS_PATTERN, address.trim());
    }
}
