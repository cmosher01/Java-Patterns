package nu.mine.mosher.patterns.builder.example;

/**
 * This is just a generic user defined data-type class
 * that we use as an example.
 */
@SuppressWarnings("WeakerAccess")
public final class Heart {
    public final boolean kind;

    private Heart(final boolean kind) {
        this.kind = kind;
    }

    public static final Heart BIG = new Heart(true);
    public static final Heart OLD_MANS = new Heart(false);
}
