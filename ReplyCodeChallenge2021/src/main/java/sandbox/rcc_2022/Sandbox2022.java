package sandbox.rcc_2022;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.AbstractWriter;
import sandbox.CommonUtils;
import sandbox.Reader;
import sandbox.Sandbox;
import sandbox.exceptions.RCCException;

public class Sandbox2022 implements Sandbox {

    private static final Logger LOGGER = LogManager.getLogger(Sandbox2022.class);

    private static final String COMMON_INPUT_PATH = "input/2022/";
    private static final String COMMON_OUTPUT_PATH = "2022/";

    private static final String INPUT_FILENAME = "00-example.txt";

    @Override
    public void run() {
        boolean readSuccess = true;

        LOGGER.debug("Starting sandbox run");

        FileProcessor2022 processor2022 = new FileProcessor2022();
        Reader reader = new Reader(processor2022);
        String filename = COMMON_INPUT_PATH + INPUT_FILENAME;

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
            processor2022.run();
        } catch (RCCException e) {
            LOGGER.error("Exception while running processor: [{}]", e.getErrorCode().code(), e);
        }

        AbstractWriter writer = new Writer2022();
        try {
            writer.initWriter(INPUT_FILENAME);
        } catch (RCCException e) {
            LOGGER.error("Write line throws error: {}", e.getErrorCode().code(), e);
        }

        writer.closeWriter();

        LOGGER.debug("End sandbox");
    }
}
