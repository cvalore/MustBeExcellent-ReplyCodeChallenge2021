package sandbox.rcc_2020;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.AbstractWriter;
import sandbox.CommonUtils;
import sandbox.Reader;
import sandbox.Sandbox;
import sandbox.exceptions.RCCException;

public class Sandbox2020 implements Sandbox {

    private static final Logger LOGGER = LogManager.getLogger(Sandbox2020.class);

    //region INPUT FIELDS
    private CommonUtils.IntegerWrapper gridWidth = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);
    private CommonUtils.IntegerWrapper gridHeight = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);

    private CommonUtils.IntegerWrapper maximumReward = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);

    private BuildingOrchestrator buildingOrchestrator = new BuildingOrchestrator();
    private AntennasOrchestrator antennasOrchestrator = new AntennasOrchestrator();
    //endregion

    //region OUTPUT_FIELD
    private CommonUtils.IntegerWrapper placedAntennas = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);
    private AntennasOrchestrator outputAntennas = new AntennasOrchestrator();
    //endregion

    @Override
    public void run() {
        boolean readSuccess = true;

        LOGGER.debug("Starting sandbox run");

        FileProcessor2020 processor2020 = new FileProcessor2020.FileProcessor2020Builder()
                .gridWidth(gridWidth)
                .gridHeight(gridHeight)
                .maximumReward(maximumReward)
                .buildingOrchestrator(buildingOrchestrator)
                .antennasOrchestrator(antennasOrchestrator)
                .build();
        Reader reader = new Reader(processor2020);
        String filename = "data_scenarios_a_example.in";

        try {
            reader.readFile(filename);
        } catch (RCCException e) {
            readSuccess = false;
            LOGGER.error("Exception while reading file: [{}]", e.getErrorCode().code(), e);
        }

        if(!readSuccess) {
            return;
        }

        try {
            processor2020.run();
        } catch (RCCException e) {
            LOGGER.error("Exception while running processor: [{}]", e.getErrorCode().code(), e);
        }

        AbstractWriter writer = new Writer2020();
        try {
            writer.initWriter("prova_output");

            String processed = writer.processWriting(CommonUtils.asObjectsArray(placedAntennas, outputAntennas));

            writer.writeLine(processed);
        } catch (RCCException e) {
            LOGGER.error("Write line throws error: {}", e.getErrorCode().code(), e);
        }

        writer.closeWriter();

        LOGGER.debug("End sandbox");
    }
}
