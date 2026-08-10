/*
 *     Copyright © 2026, Christopher Alan Mosher, New York, New York, USA, <cmosher01@gmail.com>.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package nu.mine.mosher;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("SimplifiableAssertion")
public class TestDouble {
    public static final double ONE = 1.0D;

    public static final double POSITIVE_ZERO = ONE / Double.POSITIVE_INFINITY;
    public static final double NEGATIVE_ZERO = ONE / Double.NEGATIVE_INFINITY;

    public static int asInt(final double d) {
        return StrictMath.clamp(asLong(d), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public static long asLong(final double d) {
        return StrictMath.round(StrictMath.rint(d));
    }
    public static boolean isNegative(final double d) {
        return negbit(d) != 0;
    }
    public static boolean isPositive(final double d) {
        return negbit(d) == 0;
    }
    private static long negbit(final double d) {
        return Double.doubleToLongBits(d) & (1L<<63);
    }


    private static final double[] doubles = {
            Double.NEGATIVE_INFINITY,
            -1.0D,
            NEGATIVE_ZERO,
            POSITIVE_ZERO,
            +1.0D,
            Double.POSITIVE_INFINITY,
            Double.NaN
    };
    private static final String[] labels = {
            "NEGATIVE_INFINITY",
            "-1.0D",
            "NEGATIVE_ZERO",
            "POSITIVE_ZERO",
            "+1.0D",
            "POSITIVE_INFINITY",
            "NaN",
    };
    private static final String[] shortlabels = {
            "-INF",
            "-1.0D",
            "-0.0D",
            "+0.0D",
            "+1.0D",
            "+INF",
            " NaN",
    };



    private record NamedDouble(double val, String lab, String shr) {
        @Override
        public String toString() {
            return shr();
        }
    }

    private static final ArrayList<NamedDouble> namedDoubles = new ArrayList<>(doubles.length);
    static {
        for (int i = 0; i < doubles.length; ++i) {
            namedDoubles.add(new NamedDouble(doubles[i], labels[i], shortlabels[i]));
        }
    }

    private record Pair(NamedDouble a, NamedDouble b) {
        @Override
        public String toString() {
            return "("+a+","+b+")";
        }

        public String comparisons() {
            final boolean lt = (a.val < b.val);
            final boolean gt = (a.val > b.val);
            final boolean eq = (a.val == b.val);
            final int ct = Double.compare(a.val, b.val);

            return String.format("%+1d %1s", ct, lt?"<":(eq?"=":(gt?">":"")));
        }

        public String subtraction() {
            final double difference = b.val - a.val;
            return tweakDoubleOutput(String.format("%+3.1f", difference));
        }

        public String subtractionAsInt() {
            final double c = Double.compare(a.val, b.val);
            return tweakIntOutput(String.format("%2d  ", asInt(c)));
        }

        private String tweakIntOutput(final String i) {
            return i;
        }

        private static String tweakDoubleOutput(final String d) {
            String r = d;

            final char s = d.charAt(0);
            if (!(s == '+' || s == '-')) {
                r = " "+r;
            }

            return r.substring(0, 4);
        }
    }

    private static final ArrayList<Pair> pairs = new ArrayList<>(doubles.length*doubles.length);
    static {
        for (int i = 0; i < doubles.length; ++i) {
            for (int j = 0; j < doubles.length; ++j) {
                pairs.add(new Pair(namedDoubles.get(i), namedDoubles.get(j)));
            }
        }
    }



    @Disabled("fails when a and/or b is NaN")
    @ParameterizedTest
    @FieldSource("pairs")
    @DisplayName("lt == !ge")
    void invariantLT(Pair p) {
        final double a = p.a.val;
        final double b = p.b.val;
        boolean lt = (a < b);  boolean ge = (a >= b);
        boolean le = (a <= b); boolean gt = (a > b);
        boolean eq = (a == b); boolean ne = (a != b);
        assertTrue(lt==!ge);
    }

    @Disabled("fails when a and/or b is NaN")
    @ParameterizedTest
    @FieldSource("pairs")
    @DisplayName("gt == !le")
    void invariantGT(Pair p) {
        final double a = p.a.val;
        final double b = p.b.val;
        boolean lt = (a < b);  boolean ge = (a >= b);
        boolean le = (a <= b); boolean gt = (a > b);
        boolean eq = (a == b); boolean ne = (a != b);
        assertTrue(gt==!le);
    }

    // The following three tests (ne == !eq), (le == (lt||eq), and (ge == (gt||eq))
    // prove that ne, le, and ge are redundant, given eq, lt, and gt.

    @ParameterizedTest
    @FieldSource("pairs")
    @DisplayName("ne == !eq")
    void invariantEQ(Pair p) {
        final double a = p.a.val;
        final double b = p.b.val;
        boolean lt = (a < b);  boolean ge = (a >= b);
        boolean le = (a <= b); boolean gt = (a > b);
        boolean eq = (a == b); boolean ne = (a != b);
        assertTrue(ne==!eq);
    }

    @ParameterizedTest
    @FieldSource("pairs")
    @DisplayName("le == (lt||eq)")
    void invariantLE(Pair p) {
        final double a = p.a.val;
        final double b = p.b.val;
        boolean lt = (a < b);  boolean ge = (a >= b);
        boolean le = (a <= b); boolean gt = (a > b);
        boolean eq = (a == b); boolean ne = (a != b);
        assertTrue(le==(lt||eq));
    }

    @ParameterizedTest
    @FieldSource("pairs")
    @DisplayName("ge == (gt||eq)")
    void invariantGE(Pair p) {
        final double a = p.a.val;
        final double b = p.b.val;
        boolean lt = (a < b);  boolean ge = (a >= b);
        boolean le = (a <= b); boolean gt = (a > b);
        boolean eq = (a == b); boolean ne = (a != b);
        assertTrue(ge==(gt||eq));
    }

    /*
        +------+-------+-------+-------+-------+-------+-------+-------+
        |      | -INF  | -1.0D | -0.0D | +0.0D | +1.0D | +INF  |  NaN  |
        +------+-------+-------+-------+-------+-------+-------+-------+
        |-INF  | +0 =  | -1 <  | -1 <  | -1 <  | -1 <  | -1 <  | -1    |
        |-1.0D | +1 >  | +0 =  | -1 <  | -1 <  | -1 <  | -1 <  | -1    |
        |-0.0D | +1 >  | +1 >  | +0 =  | -1 =  | -1 <  | -1 <  | -1    |
        |+0.0D | +1 >  | +1 >  | +1 =  | +0 =  | -1 <  | -1 <  | -1    |
        |+1.0D | +1 >  | +1 >  | +1 >  | +1 >  | +0 =  | -1 <  | -1    |
        |+INF  | +1 >  | +1 >  | +1 >  | +1 >  | +1 >  | +0 =  | -1    |
        | NaN  | +1    | +1    | +1    | +1    | +1    | +1    | +0    |
        +------+-------+-------+-------+-------+-------+-------+-------+

        compare: -INF  < -1.0D < -0.0D < +0.0D < +1.0D < +INF  <  NaN

     */
    @Test
    void comparisonOperatorTable() {
        bar();
        p(String.format("|%-5s |", " "));
        namedDoubles.forEach(d -> p(String.format(" %-5s |", d.shr())));
        nl();
        bar();
        for (final NamedDouble a : namedDoubles) {
            p(String.format("|%-5s |", a.shr()));
            for (final NamedDouble b : namedDoubles) {
                final Pair p = new Pair(a, b);
                p(String.format(" %s  |", p.comparisons()));
            }
            nl();
        }
        bar();
    }

    // positive and negative zero are equal
    // a NaN compared with anything, even another NaN, is false
    @Test
    void oddComparisons() {

        assertFalse(Double.NEGATIVE_INFINITY < Double.NaN);
        assertFalse(-1.0D < Double.NaN);
        assertFalse(NEGATIVE_ZERO < Double.NaN);
        assertFalse(POSITIVE_ZERO < Double.NaN);
        assertFalse(+1.0D < Double.NaN);
        assertFalse(Double.POSITIVE_INFINITY < Double.NaN);

        assertFalse(Double.NaN == Double.NaN);
        assertFalse(0.0d / 0.0 == 0.0d / 0.0);

        assertFalse(Double.NaN > Double.NEGATIVE_INFINITY);
        assertFalse(Double.NaN > -1.0D);
        assertFalse(Double.NaN > NEGATIVE_ZERO);
        assertFalse(Double.NaN > POSITIVE_ZERO);
        assertFalse(Double.NaN > +1.0D);
        assertFalse(Double.NaN > Double.POSITIVE_INFINITY);


        // double vs. Double (different behavior)
        assertTrue(POSITIVE_ZERO == NEGATIVE_ZERO);
        assertTrue(NEGATIVE_ZERO == POSITIVE_ZERO);
        final Double p0 = Double.valueOf(POSITIVE_ZERO);
        final Double n0 = Double.valueOf(NEGATIVE_ZERO);
        assertFalse(p0.equals(n0));
        assertFalse(n0.equals(p0));

        assertFalse(Double.NaN == Double.NaN);
        final Double nan1 = Double.valueOf(Double.NaN);
        final Double nan2 = Double.valueOf(Double.NaN);
        assertTrue(nan1.equals(nan2));
    }



    /*
        +------+-------+-------+-------+-------+-------+-------+-------+
        |      | -INF  | -1.0D | -0.0D | +0.0D | +1.0D | +INF  |  NaN  |
        +------+-------+-------+-------+-------+-------+-------+-------+
        |-INF  |  NaN  | +Inf  | +Inf  | +Inf  | +Inf  | +Inf  |  NaN  |
        |-1.0D | -Inf  | +0.0  | +1.0  | +1.0  | +2.0  | +Inf  |  NaN  |
        |-0.0D | -Inf  | -1.0  | +0.0  | +0.0  | +1.0  | +Inf  |  NaN  |
        |+0.0D | -Inf  | -1.0  | -0.0  | +0.0  | +1.0  | +Inf  |  NaN  |
        |+1.0D | -Inf  | -2.0  | -1.0  | -1.0  | +0.0  | +Inf  |  NaN  |
        |+INF  | -Inf  | -Inf  | -Inf  | -Inf  | -Inf  |  NaN  |  NaN  |
        | NaN  |  NaN  |  NaN  |  NaN  |  NaN  |  NaN  |  NaN  |  NaN  |
        +------+-------+-------+-------+-------+-------+-------+-------+
     */
    @Test
    void subtractionTable() {
        bar();
        p(String.format("|%-5s |", " "));
        namedDoubles.forEach(d -> p(String.format(" %-5s |", d.shr())));
        nl();
        bar();
        for (final NamedDouble a : namedDoubles) {
            p(String.format("|%-5s |", a.shr()));
            for (final NamedDouble b : namedDoubles) {
                final Pair p = new Pair(a, b);
                p(String.format(" %s  |", p.subtraction()));
            }
            nl();
        }
        bar();
    }



    @Test
    // min of +0 and -0 is always -0
    // max of +0 and -0 is always +0
    // abs(+0)==+0
    // abs(-0)==+0
    // -(-0)==+0, -(+0)==-0
    void opsOnZeroes() {
        assertEquals(NEGATIVE_ZERO, Math.min(NEGATIVE_ZERO, POSITIVE_ZERO));
        assertEquals(NEGATIVE_ZERO, Math.min(POSITIVE_ZERO, NEGATIVE_ZERO));
        assertNotEquals(POSITIVE_ZERO, Math.min(NEGATIVE_ZERO, POSITIVE_ZERO));
        assertNotEquals(POSITIVE_ZERO, Math.min(POSITIVE_ZERO, NEGATIVE_ZERO));
        assertNotEquals(NEGATIVE_ZERO, Math.max(NEGATIVE_ZERO, POSITIVE_ZERO));
        assertNotEquals(NEGATIVE_ZERO, Math.max(POSITIVE_ZERO, NEGATIVE_ZERO));
        assertEquals(POSITIVE_ZERO, Math.max(NEGATIVE_ZERO, POSITIVE_ZERO));
        assertEquals(POSITIVE_ZERO, Math.max(POSITIVE_ZERO, NEGATIVE_ZERO));

        assertEquals(POSITIVE_ZERO, Math.abs(POSITIVE_ZERO));
        assertEquals(POSITIVE_ZERO, Math.abs(NEGATIVE_ZERO));

        assertEquals(POSITIVE_ZERO, -NEGATIVE_ZERO);
        assertEquals(NEGATIVE_ZERO, -POSITIVE_ZERO);
    }



    /*
        +------+-------+-------+-------+-------+-------+-------+-------+
        |      | -INF  | -1.0D | -0.0D | +0.0D | +1.0D | +INF  |  NaN  |
        +------+-------+-------+-------+-------+-------+-------+-------+
        |-INF  |  0    | -1    | -1    | -1    | -1    | -1    | -1    |
        |-1.0D |  1    |  0    | -1    | -1    | -1    | -1    | -1    |
        |-0.0D |  1    |  1    |  0    | -1    | -1    | -1    | -1    |
        |+0.0D |  1    |  1    |  1    |  0    | -1    | -1    | -1    |
        |+1.0D |  1    |  1    |  1    |  1    |  0    | -1    | -1    |
        |+INF  |  1    |  1    |  1    |  1    |  1    |  0    | -1    |
        | NaN  |  1    |  1    |  1    |  1    |  1    |  1    |  0    |
        +------+-------+-------+-------+-------+-------+-------+-------+
     */
    @Test
    void subtractionAsIntTable() {
        bar();
        p(String.format("|%-5s |", " "));
        namedDoubles.forEach(d -> p(String.format(" %-5s |", d.shr())));
        nl();
        bar();
        for (final NamedDouble a : namedDoubles) {
            p(String.format("|%-5s |", a.shr()));
            for (final NamedDouble b : namedDoubles) {
                final Pair p = new Pair(a, b);
                p(String.format(" %s  |", p.subtractionAsInt()));
            }
            nl();
        }
        bar();
    }


    // not used
    void compareTable() {
        p("+"+"-".repeat(62)+"+");
        nl();
        p(String.format("| %-60s |", "Double.compare(row,column)"));
        nl();

        bar();

        p(String.format("|%-5s |", " "));
        for (int i = 0; i < doubles.length; ++i) {
            p(String.format(" %-5s |", shortlabels[i]));
        }
        nl();

        bar();

        for (int i = 0; i < doubles.length; ++i) {
            p(String.format("|%-5s |", shortlabels[i]));
            for (int j = 0; j < doubles.length; ++j) {
                p(String.format(" %+d    |", Double.compare(doubles[i], doubles[j])));
            }
            nl();
        }

        bar();
    }

    @Test
    void asLong() {
        p("asLong(Double.NEGATIVE_INFINITY): "+asLong(Double.NEGATIVE_INFINITY)); nl();
        p("asLong(-1.0D): "+asLong(-1.0D)); nl();
        p("asLong(NEGATIVE_ZERO): "+asLong(NEGATIVE_ZERO)); nl();
        p("asLong(POSITIVE_ZERO): "+asLong(POSITIVE_ZERO)); nl();
        p("asLong(+1.0D): "+asLong(+1.0D)); nl();
        p("asLong(Double.POSITIVE_INFINITY): "+asLong(Double.POSITIVE_INFINITY)); nl();
        p("asLong(Double.NaN): "+asLong(Double.NaN)); nl();
    }


    @Test
    void near0() {
        final double d000 = Math.nextDown(NEGATIVE_ZERO);
        final double d010 = Math.nextDown(POSITIVE_ZERO);

        final double d00  = NEGATIVE_ZERO;
        final double d01  = POSITIVE_ZERO;

        final double d001 = Math.nextUp(NEGATIVE_ZERO);
        final double d011 = Math.nextUp(POSITIVE_ZERO);

        // next down from pos and neg zero result in the same number
        assertTrue(d000 == d010);
        // next up from pos and neg zero result in the same number
        assertTrue(d001 == d011);

        // each next down is less than zero (either pos or neg)
        assertTrue(d000 < d00);
        assertTrue(d000 < d01);
        assertTrue(d010 < d00);
        assertTrue(d010 < d01);
        // each next up is greater than zero (either pos or neg)
        assertTrue(d001 > d00);
        assertTrue(d001 > d01);
        assertTrue(d011 > d00);
        assertTrue(d011 > d01);

        // d000==d010 < d00==d01 < d001==d011
        // d0x0       < d0x      < d0x1





        assertFalse(d00  == d000);
        assertTrue (d00  == d00);
        assertFalse(d00  == d001);
        assertFalse(d00  == d010);
        assertTrue (d00  == d01);
        assertFalse(d00  == d011);

        assertTrue (d000 == d000);
        assertFalse(d000 == d00);
        assertFalse(d000 == d001);
        assertTrue (d000 == d010); // ***
        assertFalse(d000 == d01);
        assertFalse(d000 == d011);

        assertFalse(d000 < d000);
        assertTrue (d000 < d00);
        assertTrue (d000 < d001);
        assertFalse(d000 < d010);
        assertTrue (d000 < d01);
        assertTrue (d000 < d011);

        assertFalse(d000 > d010);
        assertTrue(d001 > d010);

        assertTrue (d001 == d011); // ***
        assertFalse(d001 < d000);
        assertFalse(d001 < d00);
        assertFalse(d001 < d001);
        assertFalse(d001 < d010);
        assertFalse(d001 < d01);
        assertFalse(d001 < d011);

        assertTrue (d01  < d001);
    }


    @Test
    // NaN is always positive
    void signedNaN() {
        final double nan = Double.NaN;
        assertTrue(isPositive(nan));
        assertFalse(isNegative(nan));

        final double nnan = -nan;
        assertTrue(isPositive(nnan));
        assertFalse(isNegative(nnan));

        final double p0_over_p0 = +0.0D / +0.0D;
        final double p0_over_n0 = +0.0D / -0.0D;
        final double n0_over_p0 = -0.0D / +0.0D;
        final double n0_over_n0 = -0.0D / -0.0D;
        assertTrue(isPositive(p0_over_p0));
        assertTrue(isPositive(p0_over_n0));
        assertTrue(isPositive(n0_over_p0));
        assertTrue(isPositive(n0_over_n0));
        assertFalse(isNegative(p0_over_p0));
        assertFalse(isNegative(p0_over_n0));
        assertFalse(isNegative(n0_over_p0));
        assertFalse(isNegative(n0_over_n0));
    }


    private static void nl() {
        System.out.println();
    }

    private static void p(final String s) {
        System.out.print(s);
    }

    private static void bar() {
        p("+------+");
        for (int i = 0; i < doubles.length; ++i) {
            p("-------+");
        }
        nl();
    }
}
