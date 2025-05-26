// Add proper field declarations
private final IStorageService storage;
private final IEnergyService energy;

public NetworkMonitorGui(IStorageService storage, IEnergyService energy) {
    this.storage = storage;
    this.energy = energy;
}