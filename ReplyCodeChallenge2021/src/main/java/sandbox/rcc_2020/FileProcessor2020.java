package sandbox.rcc_2020;

import com.sun.istack.internal.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.CommonUtils;
import sandbox.FileProcessorInterface;
import sandbox.exceptions.RCCException;
import sandbox.exceptions.errors.ProcessorErrorCode;

import java.io.InputStream;

@RequiredArgsConstructor
public class FileProcessor2020 implements FileProcessorInterface {

    private static final Logger LOGGER = LogManager.getLogger(FileProcessor2020.class);

    @NotNull private CommonUtils.IntegerWrapper gridWidth;
    @NotNull private CommonUtils.IntegerWrapper gridHeight;
    @NotNull private CommonUtils.IntegerWrapper maximumReward;
    @NotNull private BuildingOrchestrator buildingOrchestrator;
    @NotNull private AntennasOrchestrator antennasOrchestrator;


    private boolean processed;

    @Override
    public void process(InputStream inputStream) {
        LOGGER.info("Starting file processing");

        LOGGER.info("TODO: TO BE IMPLEMENTED");

        setProcessed();
        LOGGER.info("File processing ended");
    }

    @Override
    public void process(String line) {
        LOGGER.info("Processing line: {}", line);
    }

    @Override
    public void setProcessed() {
        this.processed = true;
    }

    @Override
    public void run() throws RCCException {
        if(!processed) {
            LOGGER.error("run invoked on processor without previous processing");
            throw new RCCException(ProcessorErrorCode.RUN_WITHOUT_PROCESSING);
        }
        LOGGER.info("TODO: TO BE IMPLEMENTED - do algorithm implementation here");
    }

    public static class FileProcessor2020Builder {
        private CommonUtils.IntegerWrapper gridWidth;
        private CommonUtils.IntegerWrapper gridHeight;
        private CommonUtils.IntegerWrapper maximumReward;
        private BuildingOrchestrator buildingOrchestrator;
        private AntennasOrchestrator antennasOrchestrator;
        private boolean processed;

        FileProcessor2020Builder() {
        }

        public FileProcessor2020.FileProcessor2020Builder gridWidth(CommonUtils.IntegerWrapper gridWidth) {
            this.gridWidth = gridWidth;
            return this;
        }

        public FileProcessor2020.FileProcessor2020Builder gridHeight(CommonUtils.IntegerWrapper gridHeight) {
            this.gridHeight = gridHeight;
            return this;
        }

        public FileProcessor2020.FileProcessor2020Builder maximumReward(CommonUtils.IntegerWrapper maximumReward) {
            this.maximumReward = maximumReward;
            return this;
        }

        public FileProcessor2020.FileProcessor2020Builder buildingOrchestrator(BuildingOrchestrator buildingOrchestrator) {
            this.buildingOrchestrator = buildingOrchestrator;
            return this;
        }

        public FileProcessor2020.FileProcessor2020Builder antennasOrchestrator(AntennasOrchestrator antennasOrchestrator) {
            this.antennasOrchestrator = antennasOrchestrator;
            return this;
        }

        public FileProcessor2020 build() {
            return new FileProcessor2020(this.gridWidth, this.gridHeight, this.maximumReward, this.buildingOrchestrator, this.antennasOrchestrator);
        }

        public String toString() {
            return "FileProcessor2020.FileProcessor2020Builder(gridWidth=" + this.gridWidth + ", gridHeight=" + this.gridHeight + ", maximumReward=" + this.maximumReward + ", buildingOrchestrator=" + this.buildingOrchestrator + ", antennasOrchestrator=" + this.antennasOrchestrator + ")";
        }
    }
}

