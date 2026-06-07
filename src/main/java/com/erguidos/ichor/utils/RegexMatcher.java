package com.erguidos.ichor.utils;

public final class RegexMatcher {
    private RegexMatcher() {
        throw new UnsupportedOperationException("Don't instantiate RegexMatcher");
    }
    
    public static final String USERNAME_PATTERN = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]{10,50}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,127}$";
    
    /**
     * Regex breakdown:
     * 1. ^(?i)(?:Calle|C\/|Avda|Avenida|Paseo|Plaza|Carretera|Ctra|Hospital)[\s\wáéíóúüñÁÉÍÓÚÜÑ.,ºª-]{5,100} -> Matches common street/hospital prefixes and names
     * 2. (?:\d+|s/n)[\s\wºª-]* -> Matches EITHER a street number (\d+) OR "s/n", plus optional floor/door info
     * 3. (?:,\s*)? -> Optional comma and space separator
     * 4. (?:0[1-9]|[1-4]\d|5[0-2])\d{3} -> Matches valid Spanish postal codes (01000 to 52999)
     * 5. \s+[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\s.-]{2,50}$ -> Matches the city/municipality name
     */
    public static final String ADDRESS_PATTERN = 
          "^(?i)(?:Calle|C/|Avda|Avenida|Paseo|Plaza|Carretera|Ctra|Hospital)[\\s\\wáéíóúüñÁÉÍÓÚÜÑ.,ºª-]{5,100}"
        + "(?:\\d+|s/n)[\\s\\wºª-]*"
        + "(?:,\\s*)?"
        + "(?:0[1-9]|[1-4]\\d|5[0-2])\\d{3}"
        + "\\s+[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ\\s.-]{2,50}$";
}
