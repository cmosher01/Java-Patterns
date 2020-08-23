package nu.mine.mosher.patterns.cleanarch._03_interfaceadapters;

public class ViewModel {
    private final String sCreatedAt;

    public ViewModel(final String sCreatedAt) {
        this.sCreatedAt = sCreatedAt;
    }

    @Override
    public String toString() {
        return this.sCreatedAt;
    }
}
