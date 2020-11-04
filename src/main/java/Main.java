import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.prefs.*;

public class Main
{
    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.FINEST);
    }

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(final String... args) throws Throwable {
        LOG.trace("Hello!");

        showAsPathUrlPolygotArg("foo.bar");
        showAsPathUrlPolygotArg("./foo.bar");
        showAsPathUrlPolygotArg("../foobar/foo.bar");
        showAsPathUrlPolygotArg("/foo.bar");
        showAsPathUrlPolygotArg("http://///////junk(*&^%*&^%$%$@#%");
        showAsPathUrlPolygotArg("file:///foo.bar");
        showAsPathUrlPolygotArg("file://foo.bar");
        showAsPathUrlPolygotArg("http://example.com/foo.bar");

        //        prefs();

        System.err.flush();
        System.out.flush();
    }

    private static void showAsPathUrlPolygotArg(final String arg) throws MalformedURLException {
        LOG.trace("------------------------------------------------------");
        LOG.trace("input  : "+arg);

        // best way to handle a string argument that could be
        // either a URL or a path:
        try {
            URL url;
            try {
                url = new URI(arg).toURL();
            } catch (Throwable e) {
                try {
                    url = Paths.get(arg).toUri().toURL();
                } catch (Throwable e2) {
                    LOG.error("cannot parse URL/path: "+arg);
                    throw e2;
                }
            }
            LOG.trace("algrthm: "+url.toString());
            final InputStream in = url.openStream();
            final BufferedReader b = new BufferedReader(new InputStreamReader(in));
            for (String s = b.readLine(); Objects.nonNull(s); s = b.readLine()) {
                LOG.info(">>>>>>>: "+s);
            }
            b.close();
        } catch (Throwable x) {
            LOG.warn(">>>>>>>", x);
        }

        try {
            final URL url = URI.create(arg).toURL();
            LOG.trace("urluri : "+url.toString());
        } catch (Throwable x) {
            LOG.warn("urluri : "+x.getMessage());
        }

        try {
            final URL path = Paths.get(arg).normalize().toUri().toURL();
            LOG.trace("psuun  : "+path.toString());
        } catch (Throwable x) {
            LOG.warn("psuun  : "+x.getMessage());
        }

        try {
            final URI uri = URI.create(arg);
            LOG.trace("uri    : "+uri.toString());
        } catch (Throwable x) {
            LOG.warn("uri    : "+x.getMessage());
        }

        try {
            final Path path = Paths.get(arg);
            LOG.trace("pathstr: "+path.toString());
        } catch (Throwable x) {
            LOG.warn("pathstr: "+x.getMessage());
        }

        try {
            final URI path = Paths.get(arg).toUri();
            LOG.trace("pstruri: "+path.toString());
        } catch (Throwable x) {
            LOG.warn("pathstr: "+x.getMessage());
        }

        try {
            final Path path = Paths.get(URI.create(arg));
            LOG.trace("pathuri: "+path.toString());
        } catch (Throwable x) {
            LOG.warn("pathuri: "+x.getMessage());
        }

        try {
            final URL url = URI.create("file://"+arg).toURL();
            LOG.trace("file://: "+url.toString());
        } catch (Throwable x) {
            LOG.warn("file://: "+x.getMessage());
        }
    }

    private static void prefs() throws BackingStoreException {
        LOG.trace("------------------------");
        Preferences p = Preferences.userRoot();
        LOG.trace("\"{}\"", p.name());
        Arrays.stream(p.keys()).forEach(LOG::trace);
        LOG.trace("------------------------");
        Arrays.stream(p.childrenNames()).forEach(LOG::trace);
    }
}
