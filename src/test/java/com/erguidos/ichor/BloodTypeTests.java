package com.erguidos.ichor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.exceptions.BadBloodTypeException;

import java.util.Optional;

import static com.erguidos.ichor.enums.BloodType.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BloodType")
class BloodTypeTest {

    private static record BloodRelationship(BloodType donor, BloodType recipient) {
        public static BloodRelationship of(BloodType donor, BloodType recipient) {
            return new BloodRelationship(donor, recipient);
        }
    }
    
    private static final java.util.Map<BloodRelationship, Boolean> COMPATIBILITY = new java.util.HashMap<>();
    
    static {
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, O_NEGATIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, O_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, A_NEGATIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, A_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, B_NEGATIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, B_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, AB_NEGATIVE), true);
        COMPATIBILITY.put(BloodRelationship.of(O_NEGATIVE, AB_POSITIVE), true);
 
        COMPATIBILITY.put(BloodRelationship.of(O_POSITIVE, O_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_POSITIVE, A_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_POSITIVE, B_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(O_POSITIVE, AB_POSITIVE), true);
 
        COMPATIBILITY.put(BloodRelationship.of(A_NEGATIVE, A_NEGATIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(A_NEGATIVE, A_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(A_NEGATIVE, AB_NEGATIVE), true);
        COMPATIBILITY.put(BloodRelationship.of(A_NEGATIVE, AB_POSITIVE), true);
 
        COMPATIBILITY.put(BloodRelationship.of(A_POSITIVE, A_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(A_POSITIVE, AB_POSITIVE), true);
 
        COMPATIBILITY.put(BloodRelationship.of(B_NEGATIVE, B_NEGATIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(B_NEGATIVE, B_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(B_NEGATIVE, AB_NEGATIVE), true);
        COMPATIBILITY.put(BloodRelationship.of(B_NEGATIVE, AB_POSITIVE), true);
 
        COMPATIBILITY.put(BloodRelationship.of(B_POSITIVE, B_POSITIVE),  true);
        COMPATIBILITY.put(BloodRelationship.of(B_POSITIVE, AB_POSITIVE), true);
 
        COMPATIBILITY.put(BloodRelationship.of(AB_NEGATIVE, AB_NEGATIVE), true);
        COMPATIBILITY.put(BloodRelationship.of(AB_NEGATIVE, AB_POSITIVE), true);
 
        COMPATIBILITY.put(BloodRelationship.of(AB_POSITIVE, AB_POSITIVE), true);
    }
    
    
    
    @Test
    void givenConstants_allCorrect() {
        assertAll(
            () -> assertEquals("O+",  O_POSITIVE.toString()),
            () -> assertEquals("O-",  O_NEGATIVE.toString()),
            () -> assertEquals("A+",  A_POSITIVE.toString()),
            () -> assertEquals("A-",  A_NEGATIVE.toString()),
            () -> assertEquals("B+",  B_POSITIVE.toString()),
            () -> assertEquals("B-",  B_NEGATIVE.toString()),
            () -> assertEquals("AB+", AB_POSITIVE.toString()),
            () -> assertEquals("AB-", AB_NEGATIVE.toString())
        );
    }
    
    @Test
    void canDonateTo() {
        for (BloodType donor : BloodType.values()) {
            for (BloodType recipient : BloodType.values()) {
                assertEquals(
                    COMPATIBILITY.getOrDefault(BloodRelationship.of(donor, recipient), false),
                    donor.canDonateTo(recipient)
                );
            }
        }
    }

    @Test
    void canReceiveFrom() {
        for (BloodType donor : BloodType.values()) {
            for (BloodType recipient : BloodType.values()) {
                assertEquals(
                    donor.canDonateTo(recipient),
                    recipient.canReceiveFrom(donor),
                    donor + " -> " + recipient + ": canDonateTo and canReceiveFrom disagree"
                );
            }
        }
    }

    @Nested
    @DisplayName("null safety")
    class NullSafety {
        @Test
        @DisplayName("canDonateTo(null) returns false")
        void canDonateToNullReturnsFalse() {
            for (BloodType bt : BloodType.values()) {
                assertFalse(bt.canDonateTo(null), bt + ".canDonateTo(null) should be false");
            }
        }

        @Test
        @DisplayName("canReceiveFrom(null) returns false")
        void canReceiveFromNullReturnsFalse() {
            for (BloodType bt : BloodType.values()) {
                assertFalse(bt.canReceiveFrom(null), bt + ".canReceiveFrom(null) should be false");
            }
        }
    }

    @Nested
    @DisplayName("parse")
    class Parse {

        @ParameterizedTest(name = "\"{0}\" → A_POSITIVE")
        @ValueSource(strings = { "A+", "a+", "A +", "A_POSITIVE", "A positive", "A POSITIVE", "a_positive" })
        @DisplayName("accepts all normalised forms for A+")
        void parsesAPositiveVariants(String input) {
            assertEquals(A_POSITIVE, BloodType.parse(input));
        }

        @ParameterizedTest(name = "\"{0}\" → AB_NEGATIVE")
        @ValueSource(strings = { "AB-", "ab-", "AB-", "AB_NEGATIVE", "AB negative", "ab_negative" })
        @DisplayName("accepts all normalised forms for AB-")
        void parsesAbNegativeVariants(String input) {
            assertEquals(AB_NEGATIVE, BloodType.parse(input));
        }

        @ParameterizedTest(name = "display \"{0}\" round-trips correctly")
        @CsvSource({
            "O+,  O_POSITIVE",
            "O-,  O_NEGATIVE",
            "A+,  A_POSITIVE",
            "A-,  A_NEGATIVE",
            "B+,  B_POSITIVE",
            "B-,  B_NEGATIVE",
            "AB+, AB_POSITIVE",
            "AB-, AB_NEGATIVE",
        })
        @DisplayName("display strings round-trip through parse")
        void displayRoundTrip(String display, String expectedName) {
            assertEquals(BloodType.valueOf(expectedName.trim()), BloodType.parse(display.trim()));
        }

        @Test
        @DisplayName("parse(null) throws BadBloodTypeException")
        void parseNullThrows() {
            assertThrows(BadBloodTypeException.class, () -> BloodType.parse(null));
        }

        @ParameterizedTest(name = "\"{0}\" throws BadBloodTypeException")
        @ValueSource(strings = { "C+", "X-", "AB", "++", "positive", "123", "" })
        @DisplayName("unrecognised strings throw BadBloodTypeException")
        void parseInvalidThrows(String input) {
            assertThrows(BadBloodTypeException.class, () -> BloodType.parse(input));
        }
    }

    @Nested
    @DisplayName("tryParse")
    class TryParse {

        @Test
        @DisplayName("returns a present Optional for valid input")
        void validInputReturnsPresent() {
            Optional<BloodType> result = BloodType.tryParse("B-");
            assertTrue(result.isPresent());
            assertEquals(B_NEGATIVE, result.get());
        }

        @ParameterizedTest(name = "\"{0}\" returns empty Optional")
        @NullAndEmptySource
        @ValueSource(strings = { "C+", "garbage", "123" })
        @DisplayName("invalid or null input returns empty Optional")
        void invalidInputReturnsEmpty(String input) {
            assertTrue(BloodType.tryParse(input).isEmpty(),
                "Expected empty Optional for input: " + input);
        }

        @Test
        @DisplayName("tryParse is consistent with parse for all display strings")
        void consistentWithParse() {
            for (BloodType bt : BloodType.values()) {
                Optional<BloodType> result = BloodType.tryParse(bt.toString());
                assertTrue(result.isPresent());
                assertEquals(bt, result.get());
            }
        }
    }
}