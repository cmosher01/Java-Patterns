package nu.mine.mosher.patterns.cleanarch._05_main;

import nu.mine.mosher.patterns.cleanarch._03_interfaceadapters.Controller;
import nu.mine.mosher.patterns.cleanarch._03_interfaceadapters.Presenter;
import nu.mine.mosher.patterns.cleanarch._04_devices.Commander;
import nu.mine.mosher.patterns.cleanarch._04_devices.DataBase;
import nu.mine.mosher.patterns.cleanarch._04_devices.View;

public class Orchestrator {
    public void run() {
//        final DataBase dataBase = new DataBase();
//        final View view = new View();
//        final Presenter presenter = new Presenter(view);
//        final Controller controller = new Controller(presenter, dataBase);
//        final Commander commander = new Commander(controller);
//
//        commander.run();

        new Commander(new Controller(new Presenter(new View()), new DataBase())).run();
    }
}
