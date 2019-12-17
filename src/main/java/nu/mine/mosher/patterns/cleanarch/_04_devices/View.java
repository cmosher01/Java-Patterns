package nu.mine.mosher.cleanarch._04_devices;

import nu.mine.mosher.cleanarch._03_interfaceadapters.ViewModel;
import nu.mine.mosher.cleanarch._03_interfaceadapters.ViewPort;

public class View implements ViewPort {
    @Override
    public void show(final ViewModel viewModel) {
        System.out.println(viewModel);
    }
}
