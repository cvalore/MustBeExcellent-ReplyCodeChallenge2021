package sandbox.rcc_2020;

import sandbox.CommonUtils;
import sandbox.Reader;
import sandbox.Sandbox;
import sandbox.exceptions.RCCException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Sandbox2020 implements Sandbox {

    private static final Logger LOGGER = LogManager.getLogger(Sandbox2020.class);


    private CommonUtils.IntegerWrapper gridWidth = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);
    private CommonUtils.IntegerWrapper gridHeight = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);

    private CommonUtils.IntegerWrapper maximumReward = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);

    private BuildingOrchestrator buildingOrchestrator = new BuildingOrchestrator();
    private AntennasOrchestrator antennasOrchestrator = new AntennasOrchestrator();


    @Override
    public void run() {
        boolean readSuccess = true;

        LOGGER.debug("Starting sandbox run");

        FileProcessor2020 processor2020 = new FileProcessor2020(
                gridWidth, gridHeight, maximumReward,
                buildingOrchestrator, antennasOrchestrator
        );
        Reader reader = new Reader(processor2020);
        String filename = "data_scenarios_a_example.in";

        LOGGER.info("Prev: {} | {}", gridWidth, buildingOrchestrator.getNumberOfBuildings());

        try {
            reader.readFile(filename);
        } catch (RCCException e) {
            readSuccess = false;
            LOGGER.error("Exception while reading file: [{}]", e.getErrorCode().description(), e);
        }

        if(!readSuccess) {
            return;
        }

        LOGGER.info("After: {} | {}", gridHeight, buildingOrchestrator.getNumberOfBuildings());

        LOGGER.debug("End sandbox");
    }
}
