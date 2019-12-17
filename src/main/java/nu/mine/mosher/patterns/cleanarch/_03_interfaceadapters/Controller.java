package nu.mine.mosher.cleanarch._03_interfaceadapters;

import nu.mine.mosher.cleanarch._02_apprules.UseCase;
import nu.mine.mosher.cleanarch._02_apprules.UseCaseOutputPort;

public class Controller {
    private final UseCaseOutputPort presenter;
    private final DataBaseAccess dataBaseAccess;

    public Controller(final UseCaseOutputPort presenter, final DataBaseAccess dataBaseAccess) {
        this.presenter = presenter;
        this.dataBaseAccess = dataBaseAccess;
    }

    public void create() {
        final UseCase useCase = new UseCase(this.presenter, this.dataBaseAccess);
        useCase.run();
    }
}
