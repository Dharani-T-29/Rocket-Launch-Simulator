import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Observer Pattern - Behavioral
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

    public void notifyObservers(int stage, double fuel, double altitude, double speed) {
        for (Observer observer : observers) {
            observer.update(stage, fuel, altitude, speed);
        }
    }
}

// State Pattern - Behavioral
interface State {
    void handle(RocketContext context);
}

class PreLaunchState implements State {
    @Override
    public void handle(RocketContext context) {
        System.out.println("Handling Pre-Launch State");
        context.setState(new Stage1State());
    }
}

class Stage1State implements State {
    @Override
    public void handle(RocketContext context) {
        System.out.println("Handling Stage 1 State");
        context.setState(new Stage2State());
    }
}

class Stage2State implements State {
    @Override
    public void handle(RocketContext context) {
        System.out.println("Handling Stage 2 State");
        context.setState(new OrbitState());
    }
}

class OrbitState implements State {
    @Override
    public void handle(RocketContext context) {
        System.out.println("Handling Orbit State");
    }
}

class RocketContext {
    private State state;

    public RocketContext() {
        state = new PreLaunchState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void request() {
        state.handle(this);
    }
}

// Singleton Pattern - Creational
class MissionControl {
    private static MissionControl instance;

    private MissionControl() {
    }

    public static synchronized MissionControl getInstance() {
        if (instance == null) {
            instance = new MissionControl();
        }
        return instance;
    }

    public void report(String message) {
        System.out.println("Mission Control: " + message);
    }
}

// Factory Pattern - Creational
interface RocketStage {
    String getDescription();
}

class Stage1 implements RocketStage {
    @Override
    public String getDescription() {
        return "This is Stage 1";
    }
}

class Stage2 implements RocketStage {
    @Override
    public String getDescription() {
        return "This is Stage 2";
    }
}

class RocketStageFactory {
    public static RocketStage createStage(int stage) {
        switch (stage) {
            case 1:
                return new Stage1();
            case 2:
                return new Stage2();
            default:
                throw new IllegalArgumentException("Unknown stage: " + stage);
        }
    }
}

// Composite Pattern - Structural
interface Component {
    void showDetails();
}

class RocketPart implements Component {
    private String name;

    public RocketPart(String name) {
        this.name = name;
    }

    @Override
    public void showDetails() {
        System.out.println(name);
    }
}

class CompositeRocketPart implements Component {
    private List<Component> parts = new ArrayList<>();

    public void addPart(Component part) {
        parts.add(part);
    }

    public void removePart(Component part) {
        parts.remove(part);
    }

    @Override
    public void showDetails() {
        for (Component part : parts) {
            part.showDetails();
        }
    }
}

// Main Satellite Launch Simulation
class SatelliteLaunch {
    private int stage = 1;
    private double fuel = 100;
    private double altitude = 0;
    private double speed = 0;
    private LaunchControl launchControl;
    private RocketContext rocketContext;

    public SatelliteLaunch(LaunchControl launchControl, RocketContext rocketContext) {
        this.launchControl = launchControl;
        this.rocketContext = rocketContext;
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
        rocketContext.request(); // Handle state change
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        LaunchControl launchControl = new LaunchControl();
        TelemetrySystem telemetrySystem = new TelemetrySystem();
        MissionControl missionControl = new MissionControl();

        launchControl.addObserver(telemetrySystem);
        launchControl.addObserver(missionControl);

        RocketContext rocketContext = new RocketContext();
        SatelliteLaunch launch = new SatelliteLaunch(launchControl, rocketContext);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 'start_checks' to initiate system checks.");
        String input = scanner.nextLine();
        if (input.equals("start_checks")) {
            launch.startChecks();
        }

        System.out.println("Type 'launch' to begin the mission.");
        input = scanner.nextLine();
        if (input.equals("launch")) {
            launch.launch();
        }

        scanner.close();
    }
}
