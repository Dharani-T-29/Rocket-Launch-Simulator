// Observer Pattern

import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(int stage, double fuel, double altitude, double speed);
}

class TelemetrySystem implements Observer {
    @Override
    public void update(int stage, double fuel, double altitude, double speed) {
        System.out.println("Telemetry Update - Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
    }
}

class MissionControl implements Observer {
    @Override
    public void update(int stage, double fuel, double altitude, double speed) {
        System.out.println("Mission Control Update - Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
    }
}

class LaunchControl {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(int stage, double fuel, double altitude, double speed) {
        for (Observer observer : observers) {
            observer.update(stage, fuel, altitude, speed);
        }
    }
}

// Integration into Satellite Launch Simulation

class SatelliteLaunch {
    private int stage = 1;
    private double fuel = 100;
    private double altitude = 0;
    private double speed = 0;
    private LaunchControl launchControl;

    public SatelliteLaunch(LaunchControl launchControl) {
        this.launchControl = launchControl;
    }

    public void startChecks() {
        System.out.println("All systems are 'Go' for launch.");
    }

    public void launch() {
        System.out.println("Launch initiated.");
        while (fuel > 0 && altitude < 100) {
            simulateStep();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (fuel == 0) {
            System.out.println("Mission Failed due to insufficient fuel.");
        } else {
            System.out.println("Mission Successful!");
        }
    }

    private void simulateStep() {
        fuel -= 5;
        altitude += 1;
        speed += 100;
        if (altitude == 10) {
            stage = 2;
            fuel -= 10;
        }
        launchControl.notifyObservers(stage, fuel, altitude, speed);
    }
}

// Usage

public class Main {
    public static void main(String[] args) {
        LaunchControl launchControl = new LaunchControl();
        TelemetrySystem telemetrySystem = new TelemetrySystem();
        MissionControl missionControl = new MissionControl();

        launchControl.addObserver(telemetrySystem);
        launchControl.addObserver(missionControl);

        SatelliteLaunch launch = new SatelliteLaunch(launchControl);
        launch.startChecks();
        launch.launch();
    }
}
