package com.erguidos.ichor.enums;

import java.util.Optional;

import com.erguidos.ichor.exceptions.BadBloodTypeException;

public enum BloodType {
     O_POSITIVE(ABOGroup.O,  RHFactor.POSITIVE),
     O_NEGATIVE(ABOGroup.O,  RHFactor.NEGATIVE),
     A_POSITIVE(ABOGroup.A,  RHFactor.POSITIVE),
     A_NEGATIVE(ABOGroup.A,  RHFactor.NEGATIVE),
     B_POSITIVE(ABOGroup.B,  RHFactor.POSITIVE),
     B_NEGATIVE(ABOGroup.B,  RHFactor.NEGATIVE),
    AB_POSITIVE(ABOGroup.AB, RHFactor.POSITIVE),
    AB_NEGATIVE(ABOGroup.AB, RHFactor.NEGATIVE);

    private enum ABOGroup {
        A, B, AB, O;

        static boolean canDonate(ABOGroup donor, ABOGroup recipient) {
            return switch (donor) {
                case A  -> recipient == ABOGroup.A  || recipient == ABOGroup.AB;
                case AB -> recipient == ABOGroup.AB;
                case B  -> recipient == ABOGroup.B  || recipient == ABOGroup.AB;
                case O  -> true;
            };
        }
    }

    private enum RHFactor {
        POSITIVE("+"),
        NEGATIVE("-");

        final String symbol;

        RHFactor(String symbol) { this.symbol = symbol; }

        static boolean canDonate(RHFactor donor, RHFactor recipient) {
            return switch (donor) {
                case POSITIVE -> recipient == RHFactor.POSITIVE;
                case NEGATIVE -> true;
            };
        }
    }

    private final ABOGroup aboGroup;
    private final RHFactor rhFactor;
    private final String display;

    BloodType(ABOGroup aboGroup, RHFactor rhFactor) {
        this.aboGroup = aboGroup;
        this.rhFactor = rhFactor;
        this.display  = aboGroup.name() + rhFactor.symbol;
    }
    
    @Override
    public String toString() {
        return this.display;
    }

    public boolean canDonateTo(BloodType recipient) {
        return recipient != null
            && RHFactor.canDonate(this.rhFactor, recipient.rhFactor)
            && ABOGroup.canDonate(this.aboGroup, recipient.aboGroup);
    }

    public boolean canReceiveFrom(BloodType donor) {
        if (donor == null) { return false; }
        return donor.canDonateTo(this);
    }

    /**
     * Parses a string into a {@link BloodType}.
     *
     * <br>The input is normalized before matching: whitespace and underscores
     * are stripped, the text is upper-cased, and the words {@code POSITIVE} /
     * {@code NEGATIVE} are replaced with {@code +} / {@code -}. This means all
     * of the following resolve to {@link #A_POSITIVE}:
     * <ul>
     *   <li>{@code "A+"}</li>
     *   <li>{@code "a+"}</li>
     *   <li>{@code "A_POSITIVE"}</li>
     *   <li>{@code "A positive"}</li>
     * </ul>
     *
     * @param value the string to parse
     * @return the matching {@link BloodType}
     * @throws BadBloodTypeException if {@code value} is {@code null} or does not
     * match any known blood type
     */
    public static BloodType parse(String value) {
        if (value == null) { throw new BadBloodTypeException(); }

        String normalized = value.trim()
                                 .toUpperCase()
                                 .replace("POSITIVE", "+")
                                 .replace("NEGATIVE", "-")
                                 .replaceAll("[ _]+", "");

        for (BloodType bt : BloodType.values()) {
            if (bt.display.equals(normalized)) {
                return bt;
            }
        }

        throw new BadBloodTypeException(value);
    }

    /**
     * Attempts to parse a string into a {@link BloodType}, returning an empty
     * {@link Optional} instead of throwing if the input is invalid.
     *
     * @param value the string to parse; may be {@code null}
     * @return an {@link Optional} containing the matched {@link BloodType}, or
     *         {@link Optional#empty()} if the input is unrecognized or
     *         {@code null}
     */
    public static Optional<BloodType> tryParse(String value) {
        try {
            return Optional.of(BloodType.parse(value));
        } catch (BadBloodTypeException bbte) {
            return Optional.empty();
        }
    }
}
