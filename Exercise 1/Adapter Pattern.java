// Adapter Pattern

interface NewTelemetrySystem {
    void newUpdate(int stage, double fuel, double altitude, double speed);
}

class OldTelemetrySystem {
    public void oldUpdate(int stage, double fuel, double altitude, double speed) {
        System.out.println("Old Telemetry - Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
    }
}

class TelemetryAdapter implements NewTelemetrySystem {
    private OldTelemetrySystem oldSystem;

    public TelemetryAdapter(OldTelemetrySystem oldSystem) {
        this.oldSystem = oldSystem;
    }

    @Override
    public void newUpdate(int stage, double fuel, double altitude, double speed) {
        oldSystem.oldUpdate(stage, fuel, altitude, speed);
    }
}

// Integration into Satellite Launch Simulation

public class Main {
    public static void main(String[] args) {
        OldTelemetrySystem oldSystem = new OldTelemetrySystem();
        NewTelemetrySystem newSystem = new TelemetryAdapter(oldSystem);

        newSystem.newUpdate(1, 90, 10, 1000);
    }
}
