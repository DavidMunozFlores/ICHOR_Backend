package com.erguidos.ichor.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public final class IchorUtils {
    private static final String[] FIRST_WORDS = {
        "Blue", "Silent", "Rapid", "Golden", "Dark",
        "Lucky", "Bright", "Wild", "Frozen", "Crimson"
    };

    private static final String[] SECOND_WORDS = {
        "Tiger", "Shadow", "Rocket", "Forest", "Storm",
        "Falcon", "River", "Phoenix", "Knight", "Machine"
    };

    private static final Random RANDOM = new Random();

    public static String randomString() {
        String first = FIRST_WORDS[RANDOM.nextInt(FIRST_WORDS.length)];
        String second = SECOND_WORDS[RANDOM.nextInt(SECOND_WORDS.length)];
        return first + " " + second;
    }
    
    public static BigDecimal randomLatitude() {
        double value = -90 + (180 * RANDOM.nextDouble());

        return BigDecimal.valueOf(value)
                .setScale(9, RoundingMode.HALF_UP);
    }

    // Random longitude between -180 and 180
    public static BigDecimal randomLongitude() {
        double value = -180 + (360 * RANDOM.nextDouble());

        return BigDecimal.valueOf(value)
                .setScale(9, RoundingMode.HALF_UP);
    }
}
