package nu.mine.mosher.cleanarch._01_businessrules;

import java.time.Clock;
import java.time.Instant;

public class Entity {
    public final Instant createdAt;

    public Entity(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public static Entity createNew(final Clock clock) {
        return new Entity(clock.instant());
    }
}
