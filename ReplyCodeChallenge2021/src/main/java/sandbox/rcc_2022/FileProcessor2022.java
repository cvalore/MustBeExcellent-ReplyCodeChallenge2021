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
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
public class FileProcessor2022 implements FileProcessorInterface {

    private static final Logger LOGGER = LogManager.getLogger(FileProcessor2022.class);

    //region INPUT
    private CommonUtils.IntegerWrapper currentStamina;
    private CommonUtils.IntegerWrapper maxStamina;
    private CommonUtils.IntegerWrapper turnsAvailable;
    private CommonUtils.IntegerWrapper demonsAvailable;
    @NotNull
    private List<Demon> inputDemons;
    //endregion

    //region OTHERS
    private boolean processed;
    private int lineRead;

    private Map<Integer, List<Demon>> defeatedDemonsPerTurn = new HashMap<>();
    private boolean defeatedDemonThisTurn;

    private int currentTurn = 1;
    private int totalScore;
    //endregion

    private List<Demon> demonsByStamina;
    private List<Demon> demonsByFinalReward;
    private List<Demon> demonsByStaminaRecoveryRate;


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

        inputDemons.sort(Demon::compareByStamina);
        demonsByStamina = new ArrayList<>(inputDemons);
        Collections.reverse(demonsByStamina);


        inputDemons.sort((o1, o2) -> o1.compareByFinalReward(o2, turnsAvailable.getValue()));
        demonsByFinalReward = new ArrayList<>(inputDemons);;
        Collections.reverse(demonsByFinalReward);

        inputDemons.sort(Demon::compareByFinalStaminaRecoveryRate);
        demonsByStaminaRecoveryRate = new ArrayList<>(inputDemons);;
        Collections.reverse(demonsByStaminaRecoveryRate);

        gameLoop();

    }

    private void gameLoop() {
        recoverStamina();
        chooseDemonToFace();
    }

    private void recoverStamina() {

    }

    private void chooseDemonToFace() {
        if(currentTurn > this.turnsAvailable.getValue()) {
            return;
        }
        //TODO..
    }

    private void collectFragments() {
        if(currentTurn > this.turnsAvailable.getValue()) {
            return;
        }
        //TODO..
    }

    @Override
    public void setProcessed() {
        this.processed = true;
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

    private boolean isStaminaEnough(int staminaNeeded) {
        return this.currentStamina.getValue() >= staminaNeeded;
    }

    private boolean canFaceDemon(Demon d) {
        return !d.isFaced();
    }

    private void faceDemon(Demon d) {
        //TODO other logic if needed..
        boolean win = false;

        //..

        if(win) {
            //check if correct
            defeatedDemonsPerTurn.computeIfPresent(currentTurn, (t, list) -> {
                list.add(d);
                return list;
            });
            defeatedDemonsPerTurn.putIfAbsent(currentTurn, new ArrayList<>(Collections.singleton(d)));
        }

        d.setFaced(true);
    }
}
