import model.Island;
import service.SimulationService;
import utils.Logs;
import utils.SimulationSettings;

public class Main {
    public static void main(String[] args) {

        Island island = new Island();
        SimulationService simulationService = new SimulationService(island);

        simulationService.startSimulation();

        try {
            Thread.sleep(
                    SimulationSettings.SIMULATION_DURATION_MINUTES * 60 * 1000L
            );
        } catch (InterruptedException e) {
            Logs.logError("Симуляция прервана досрочно",e);
            Thread.currentThread().interrupt();
        }

        simulationService.stopSimulation();
    }
}
