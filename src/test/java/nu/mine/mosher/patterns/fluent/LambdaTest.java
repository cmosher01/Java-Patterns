package nu.mine.mosher.patterns.fluent;

import org.junit.jupiter.api.Test;

import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LambdaTest {







    // METHOD A

    // infrastructure
    @FunctionalInterface
    interface Block<T> {
        T apply() throws Exception;
        static <T> T f(final Block<T> b) {
            try {
                return b.apply();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // example use
    private static final String actualA = Block.f(() -> {
        String a = "F";
        String b = "O";
        return a+b+b;
    });

    // example test
    @Test
    void lambdaA() {
        assertEquals("FOO", actualA);
    }







    // METHOD B

    private static final String actualB = ((Supplier<String>)() -> {
        String a = "F";
        String b = "O";
        return a + b + b;
    }).get();

    @Test
    void lambdaB() {
        assertEquals("FOO", actualB);
    }







}
