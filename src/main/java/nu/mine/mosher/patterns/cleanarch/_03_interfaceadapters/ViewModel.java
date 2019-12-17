package nu.mine.mosher.cleanarch._03_interfaceadapters;

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
