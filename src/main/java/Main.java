import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Main
{
    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.FINEST);
    }

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(final String... args) {
        LOG.trace("Hello!");
        System.err.flush();
        System.out.flush();
    }
}
