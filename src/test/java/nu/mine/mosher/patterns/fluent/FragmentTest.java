package nu.mine.mosher.patterns.fluent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FragmentTest
{
    @Test
    void empty() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .toString();
        assertTrue(actual.isEmpty());
    }

    @Test
    void text() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .text("foobar")
            .toString();
        assertEquals("foobar", actual);
    }

    @Test
    void emptyElement() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foobar")
            .toString();
        assertEquals("<foobar/>", actual);
    }

    @Test
    void emptyElementEnd() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foobar")
            .end()
            .toString();
        assertEquals("<foobar/>", actual);
    }

    @Test
    void textEmptyElement() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .text("quux")
            .elem("foobar")
            .toString();
        assertEquals("quux<foobar/>", actual);
    }

    @Test
    void textEmptyElementEnd() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .text("quux")
            .elem("foobar")
            .end()
            .toString();
        assertEquals("quux<foobar/>", actual);
    }

    @Test
    void emptyElementText() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foobar")
            .end()
            .text("quux")
            .toString();
        assertEquals("<foobar/>quux", actual);
    }

    @Test
    void elementWithText() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foobar")
            .text("snafu")
            .toString();
        assertEquals("<foobar>snafu</foobar>", actual);
    }

    @Test
    void emptyElementWithEmptyAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("bar")
            .toString();
        assertEquals("<foo bar=\"\"/>", actual);
    }

    @Test
    void emptyElementWithAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("xml:id", "101027")
            .toString();
        assertEquals("<foo xml:id=\"101027\"/>", actual);
    }

    @Test
    void elementWithAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("abc", "def")
            .text("quuz")
            .toString();
        assertEquals("<foo abc=\"def\">quuz</foo>", actual);
    }

    @Test
    void elementWithAttributeNestedEmptyElement() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("abc", "def")
            .elem("quux")
            .toString();
        assertEquals("<foo abc=\"def\"><quux/></foo>", actual);
    }

    @Test
    void elementWithAttributeNestedElementWithAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("abc", "def")
            .elem("quux")
            .attr("uvw", "xyz")
            .toString();
        assertEquals("<foo abc=\"def\"><quux uvw=\"xyz\"/></foo>", actual);
    }

    @Test
    void elementWithAttributeNestedTextAndElementWithAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("abc", "def")
            .text("snafu")
            .elem("quux")
            .attr("uvw", "xyz")
            .toString();
        assertEquals("<foo abc=\"def\">snafu<quux uvw=\"xyz\"/></foo>", actual);
    }

    @Test
    void elementWithAttributeNestedText2AndElementWithAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("abc", "def")
            .text("sn")
            .text("afu")
            .elem("quux")
            .attr("uvw", "xyz")
            .toString();
        assertEquals("<foo abc=\"def\">snafu<quux uvw=\"xyz\"/></foo>", actual);
    }

    @Test
    void elementWithAttributeNestedTextEndText() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("foo")
            .attr("abc", "def")
            .text("snafu")
            .end()
            .text("bar")
            .toString();
        assertEquals("<foo abc=\"def\">snafu</foo>bar", actual);
    }

    @Test
    void threeNestedElements() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("a")
            .elem("b")
            .elem("c")
            .toString();
        assertEquals("<a><b><c/></b></a>", actual);
    }

    @Test
    void threeNestedElementsWithText() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("a")
            /**/.elem("b")
            /**//**/.elem("c")
            /**//**//**/.text("d")
            /**//**/.end()
            /**/.end()
            .end()
            .toString();
        assertEquals("<a><b><c>d</c></b></a>", actual);
    }

    @Test
    void threeNestedElementsWithAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("a")
            /**/.elem("b")
            /**//**/.elem("c")
            /**//**//**/.attr("d", "e")
            /**//**/.end()
            /**/.end()
            .end()
            .toString();
        assertEquals("<a><b><c d=\"e\"/></b></a>", actual);
    }

    @Test
    void fourLevelsWithEverything() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .text("1")
            .elem("ABC")
            .end()
            .text("2")
            .elem("DEF")
            .text("3")
            .elem("GHI")
            .attr("abc")
            .text("4")
            .elem("JKL")
            .attr("def", "ghi")
            .text("5")
            .end()
            .text("6")
            .end()
            .text("7")
            .end()
            .text("8")
            .toString();
        assertEquals("1<ABC/>2<DEF>3<GHI abc=\"\">4<JKL def=\"ghi\">5</JKL>6</GHI>7</DEF>8", actual);
    }

    @Test
    void extraEnds() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("a")
            .end()
            .end()
            .end()
            .end()
            .end()
            .toString();
        assertEquals("<a/>", actual);
    }

    @Test
    void missingEnds() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("a")
            .elem("b")
            .elem("c")
            .elem("d")
            .elem("e")
            .elem("f")
            .toString();
        assertEquals("<a><b><c><d><e><f/></e></d></c></b></a>", actual);
    }

    @Test
    void ltText() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .text("<")
            .toString();
        assertEquals("&lt;", actual);
    }

    @Test
    void newlineAttribute() {
        final Fragment uut = new Fragment();
        final String actual = uut
            .elem("strange")
            .attr("newline-here", "\n")
            .toString();
        assertEquals("<strange newline-here=\"&#xA;\"/>", actual);
    }
}
