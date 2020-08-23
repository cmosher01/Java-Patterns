package nu.mine.mosher.patterns.cleanarch._04_devices;

import nu.mine.mosher.patterns.cleanarch._03_interfaceadapters.Controller;

public class Commander {
    private final Controller controller;

    public Commander(final Controller controller) {
        this.controller = controller;
    }

    public void run() {
        // for this test, we just run one hard-coded command ("create"), and then we're done
        this.controller.create();
    }
}
