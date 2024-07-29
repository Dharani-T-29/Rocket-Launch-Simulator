// Singleton Pattern

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

// Integration into Satellite Launch Simulation

public class Main {
    public static void main(String[] args) {
        MissionControl missionControl1 = MissionControl.getInstance();
        MissionControl missionControl2 = MissionControl.getInstance();
        missionControl1.report("All systems go.");

        if (missionControl1 == missionControl2) {
            System.out.println("Both instances are the same.");
        }
    }
}
