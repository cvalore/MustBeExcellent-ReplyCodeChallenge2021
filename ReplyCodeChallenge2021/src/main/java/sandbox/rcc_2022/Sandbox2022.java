package sandbox.rcc_2022;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.AbstractWriter;
import sandbox.CommonUtils;
import sandbox.Reader;
import sandbox.Sandbox;
import sandbox.exceptions.RCCException;

import java.util.ArrayList;
import java.util.List;

public class Sandbox2022 implements Sandbox {

    private static final Logger LOGGER = LogManager.getLogger(Sandbox2022.class);

    private static final String COMMON_INPUT_PATH = "input/2022/";

    private static final String INPUT_FILENAME = "00-example.txt";


    //region INPUT FIELDS
    private CommonUtils.IntegerWrapper currentStamina = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);
    private CommonUtils.IntegerWrapper maxStamina = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);
    private CommonUtils.IntegerWrapper turnsAvailable = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);
    private CommonUtils.IntegerWrapper demonsAvailable = new CommonUtils.IntegerWrapper(CommonUtils.NOT_INIT_INT_VALUE);
    private List<Demon> inputDemons = new ArrayList<>();
    //endregion


    @Override
    public void run() {
        boolean readSuccess = true;

        LOGGER.debug("Starting sandbox run");

        FileProcessor2022 processor2022 = new FileProcessor2022.FileProcessor2022Builder()
                .currentStamina(currentStamina)
                .maxStamina(maxStamina)
                .turnsAvailable(turnsAvailable)
                .demonsAvailable(demonsAvailable)
                .inputDemons(inputDemons)
                .build();
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

        checkAssertions();

        AbstractWriter writer = new Writer2022();
        try {
            writer.initWriter(INPUT_FILENAME);
        } catch (RCCException e) {
            LOGGER.error("Write line throws error: {}", e.getErrorCode().code(), e);
        }

        writer.closeWriter();

        LOGGER.debug("End sandbox");
    }

    private void checkAssertions() {
        assert (this.currentStamina.getValue() != CommonUtils.NOT_INIT_INT_VALUE);
        assert (this.maxStamina.getValue() != CommonUtils.NOT_INIT_INT_VALUE);
        assert (this.turnsAvailable.getValue() != CommonUtils.NOT_INIT_INT_VALUE);
        assert (this.demonsAvailable.getValue() != CommonUtils.NOT_INIT_INT_VALUE);
        assert (this.inputDemons.size() == this.demonsAvailable.getValue());
    }

    private int loseStamina(int value) {
        int newStamina = this.currentStamina.getValue() - value;
        if(newStamina < 0) {
            newStamina = 0;
        }
        this.currentStamina.setValue(newStamina);
        return newStamina;
    }

    private int increaseStamina(int value) {
        int newStamina = this.currentStamina.getValue() + value;
        if(newStamina > this.maxStamina.getValue()) {
            newStamina = this.maxStamina.getValue();
        }
        this.currentStamina.setValue(newStamina);
        return newStamina;
    }
}
