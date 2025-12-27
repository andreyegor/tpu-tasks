package lab.algebra.infra.view;

public interface SettingsView {
    enum DisplayMode {
        CANVAS, GRID
    }
    
    void open(SettingsCallback callback);

    interface SettingsCallback {
        void onSettingsConfirmed(int size, int bombs, DisplayMode displayMode);
    }
}
