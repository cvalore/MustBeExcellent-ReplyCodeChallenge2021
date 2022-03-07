package sandbox.rcc_2020;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.CommonUtils;
import sandbox.FileProcessorInterface;

import java.io.InputStream;

public class FileProcessor2020 implements FileProcessorInterface {

    private static final Logger LOGGER = LogManager.getLogger(FileProcessor2020.class);

    private CommonUtils.IntegerWrapper gridWidth;
    private CommonUtils.IntegerWrapper gridHeight;
    private CommonUtils.IntegerWrapper maximumReward;
    private BuildingOrchestrator buildingOrchestrator;
    private AntennasOrchestrator antennasOrchestrator;

    public FileProcessor2020(
            CommonUtils.IntegerWrapper gridWidth, CommonUtils.IntegerWrapper gridHeight, CommonUtils.IntegerWrapper maximumReward,
            BuildingOrchestrator buildingOrchestrator, AntennasOrchestrator antennasOrchestrator
    ) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.maximumReward = maximumReward;
        this.buildingOrchestrator = buildingOrchestrator;
        this.antennasOrchestrator = antennasOrchestrator;
    }

    @Override
    public void process(InputStream inputStream) {
        LOGGER.info("Starting file processing");
        LOGGER.info("File processing ended");
    }

    @Override
    public void process(String line) {
        LOGGER.info("Starting file processing - Line by line");
        LOGGER.info("File processing ended - Line by line");
    }
}
