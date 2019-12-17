package nu.mine.mosher.cleanarch._02_apprules;

import java.time.Instant;

public class ResponseModel {
    public final Instant createdAt;

    public ResponseModel(final Instant createdAt) {
        this.createdAt = createdAt;
    }
}
