// Factory Pattern

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

// Integration into Satellite Launch Simulation

public class Main {
    public static void main(String[] args) {
        RocketStage stage1 = RocketStageFactory.createStage(1);
        RocketStage stage2 = RocketStageFactory.createStage(2);

        System.out.println(stage1.getDescription());
        System.out.println(stage2.getDescription());
    }
}
