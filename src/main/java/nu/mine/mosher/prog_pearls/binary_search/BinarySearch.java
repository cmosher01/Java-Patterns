package nu.mine.mosher.prog_pearls.binary_search;

import java.util.List;

import static java.util.Arrays.asList;

public final class BinarySearch {
    private static final class Foo implements Comparable<Foo> {
        private final int n;

        private Foo(int n) {
            this.n = n;
        }

        @Override
        public int compareTo(final Foo that) {
            return Integer.compare(this.n, that.n);
        }
    }

    public static void main(final String... args) {
        {
            final int[] x = new int[]{2, 5, 7, 9, 14};
            final int t = 9;
            final int p = binarySearch(t, x);
            System.out.println(p);
        }

        {
            final List<Foo> x = asList(new Foo(2), new Foo(5), new Foo(7), new Foo(9), new Foo(14));
            final Foo t = new Foo(9);
            final int p = binarySearch(t, x);
            System.out.println(p);
        }
    }

    public static int binarySearch(final int t, final int[] x) {
        final int n = x.length;
        int l = 0;
        int u = n-1;
        while (l <= u) {
            final int m = (l + u) / 2;
            if (x[m] < t) {
                l = m + 1;
            } else if (t < x[m]) {
                u = m - 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    public static <T extends Comparable<? super T>> int binarySearch(final T t, final List<T> x) {
        final int n = x.size();
        int l = 0;
        int u = n-1;
        while (l <= u) {
            final int m = (l + u) / 2;
            final int cmp = x.get(m).compareTo(t);
            if (cmp < 0) {
                l = m + 1;
            } else if (0 < cmp) {
                u = m - 1;
            } else {
                return m;
            }
        }
        return -1;
    }
}
