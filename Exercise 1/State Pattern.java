// State Pattern

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

// Integration into Satellite Launch Simulation

public class Main {
    public static void main(String[] args) {
        RocketContext rocketContext = new RocketContext();
        rocketContext.request();  // Pre-Launch to Stage 1
        rocketContext.request();  // Stage 1 to Stage 2
        rocketContext.request();  // Stage 2 to Orbit
        rocketContext.request();  // Already in Orbit
    }
}
