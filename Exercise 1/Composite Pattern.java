// Composite Pattern

import java.util.ArrayList;
import java.util.List;

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

// Integration into Satellite Launch Simulation

public class Main {
    public static void main(String[] args) {
        RocketPart engine = new RocketPart("Engine");
        RocketPart wing = new RocketPart("Wing");

        CompositeRocketPart stage1 = new CompositeRocketPart();
        stage1.addPart(engine);
        stage1.addPart(wing);

        CompositeRocketPart rocket = new CompositeRocketPart();
        rocket.addPart(stage1);

        rocket.showDetails();
    }
}
