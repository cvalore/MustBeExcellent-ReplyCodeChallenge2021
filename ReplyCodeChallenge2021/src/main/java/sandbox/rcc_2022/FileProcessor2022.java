package sandbox.rcc_2022;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.CommonUtils;
import sandbox.FileProcessorInterface;
import sandbox.exceptions.RCCException;
import sandbox.exceptions.errors.ProcessorErrorCode;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class FileProcessor2022 implements FileProcessorInterface {

    private static final Logger LOGGER = LogManager.getLogger(FileProcessor2022.class);

    private CommonUtils.IntegerWrapper currentStamina;
    private CommonUtils.IntegerWrapper maxStamina;
    private CommonUtils.IntegerWrapper turnsAvailable;
    private CommonUtils.IntegerWrapper demonsAvailable;
    @NotNull
    private List<Demon> inputDemons;

    private boolean processed;

    private int lineRead;

    @Override
    public void process(InputStream inputStream) {

    }

    @Override
    public void process(String line) {
        LOGGER.info("Read: {}", line);
        String[] splitted = line.split(" ");
        if(lineRead == 0) {
            currentStamina.setValue(Integer.parseInt(splitted[0]));
            maxStamina.setValue(Integer.parseInt(splitted[1]));
            turnsAvailable.setValue(Integer.parseInt(splitted[2]));
            demonsAvailable.setValue(Integer.parseInt(splitted[3]));
        } else {
            Demon demon = Demon.createFromStringArray(splitted);
            inputDemons.add(demon);
        }
        lineRead++;
    }

    @Override
    public void run() throws RCCException {
        if (!processed) {
            LOGGER.error("run invoked on processor without previous processing");
            throw new RCCException(ProcessorErrorCode.RUN_WITHOUT_PROCESSING);
        }
        LOGGER.info("TODO: TO BE IMPLEMENTED - do algorithm implementation here");
    }

    @Override
    public void setProcessed() {
        this.processed = true;
    }
}
