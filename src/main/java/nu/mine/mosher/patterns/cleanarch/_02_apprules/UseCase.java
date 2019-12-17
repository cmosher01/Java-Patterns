package nu.mine.mosher.cleanarch._02_apprules;

import nu.mine.mosher.cleanarch._01_businessrules.Entity;
import nu.mine.mosher.cleanarch._03_interfaceadapters.DataBaseAccess;

import java.time.Clock;

public class UseCase implements UseCaseInputPort {
    private final UseCaseOutputPort useCaseOutputPort;
    private final DataBaseAccess dataBaseAccess;

    public UseCase(final UseCaseOutputPort useCaseOutputPort, DataBaseAccess dataBaseAccess) {
        this.useCaseOutputPort = useCaseOutputPort;
        this.dataBaseAccess = dataBaseAccess;
    }

    @Override
    public void run() {
        final Entity entity = Entity.createNew(Clock.systemUTC());
        this.useCaseOutputPort.present(new ResponseModel(entity.createdAt));
    }
}
