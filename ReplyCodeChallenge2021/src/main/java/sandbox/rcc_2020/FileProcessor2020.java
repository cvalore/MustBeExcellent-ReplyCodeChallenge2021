package sandbox.rcc_2020;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.CommonUtils;
import sandbox.FileProcessorInterface;
import sandbox.exceptions.RCCException;
import sandbox.exceptions.errors.ProcessorErrorCode;

import javax.validation.constraints.NotNull;
import java.io.InputStream;

@AllArgsConstructor
@Builder
@Data
public class FileProcessor2020 implements FileProcessorInterface {

    private static final Logger LOGGER = LogManager.getLogger(FileProcessor2020.class);

    @NotNull
    private CommonUtils.IntegerWrapper gridWidth;
    @NotNull
    private CommonUtils.IntegerWrapper gridHeight;
    @NotNull
    private CommonUtils.IntegerWrapper maximumReward;
    @NotNull
    private BuildingOrchestrator buildingOrchestrator;
    @NotNull
    private AntennasOrchestrator antennasOrchestrator;


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
        if (!processed) {
            LOGGER.error("run invoked on processor without previous processing");
            throw new RCCException(ProcessorErrorCode.RUN_WITHOUT_PROCESSING);
        }
        LOGGER.info("TODO: TO BE IMPLEMENTED - do algorithm implementation here");
    }
}

