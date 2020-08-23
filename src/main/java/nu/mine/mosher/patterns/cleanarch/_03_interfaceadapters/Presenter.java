package nu.mine.mosher.patterns.cleanarch._03_interfaceadapters;

import nu.mine.mosher.patterns.cleanarch._02_apprules.ResponseModel;
import nu.mine.mosher.patterns.cleanarch._02_apprules.UseCaseOutputPort;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Presenter implements UseCaseOutputPort {
    private static final DateTimeFormatter DATETIME_FORMATTER =
        DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.FULL)
            .withLocale(Locale.US)
            .withZone(ZoneId.of("Z"));

    private final ViewPort viewPort;

    public Presenter(final ViewPort viewPort) {
        this.viewPort = viewPort;
    }

    @Override
    public void present(final ResponseModel response) {
        final String sCreatedAt = Presenter.DATETIME_FORMATTER.format(response.createdAt);
        this.viewPort.show(new ViewModel(sCreatedAt));
    }
}
