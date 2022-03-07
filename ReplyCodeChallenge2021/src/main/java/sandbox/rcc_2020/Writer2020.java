package sandbox.rcc_2020;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.AbstractWriter;
import sandbox.CommonUtils;
import sandbox.StringUtils;

public class Writer2020 extends AbstractWriter {

    private final Logger LOGGER = LogManager.getLogger(Writer2020.class);

    public String processWriting(CommonUtils.IntegerWrapper placedAntennas, AntennasOrchestrator outputAntennas) {
        LOGGER.info("PROCESS WRITING");
        return "Ciao prova\nciao 16 ancora ciao\nfine .";
    }

    @Override
    public String processWriting(Object[] objects) {
        if(objects.length == 2 &&
                objects[0] instanceof CommonUtils.IntegerWrapper &&
                objects[1] instanceof AntennasOrchestrator
        ) {
            return this.processWriting((CommonUtils.IntegerWrapper) objects[0], (AntennasOrchestrator) objects[1]);
        }

        return StringUtils.EMPTY;
    }
}
