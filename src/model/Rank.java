package model;

/**
 * Enum representing the ranks of playing cards.
 * Each rank has an associated numeric value.
 */
public enum Rank {
    ace(1),
    two(2),
    three(3),
    four(4),
    five(5),
    six(6),
    seven(7),
    eight(8),
    nine(9),
    ten(10),
    jack(0),
    queen(0),
    king(11),
    joker(11);

    private final int value;

    /**
     * Constructs a Rank enum with the specified numeric value.
     *
     * @param value The numeric value associated with the rank.
     */
    Rank(int value) {
        this.value = value;
    }

    /**
     * Gets the numeric value associated with the rank.
     *
     * @return The numeric value of the rank.
     */
    public int getValue() {
        return value;
    }
}
