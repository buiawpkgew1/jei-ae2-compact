public class NetworkMonitorGui {
    private final IStorageService storage;
    private final IEnergyService energy;
    private long lastUpdateTime = 0;
    private static final long UPDATE_INTERVAL = 1000;

    public NetworkMonitorGui(IStorageService storage, IEnergyService energy) {
        this.storage = storage;
        this.energy = energy;
    }
}