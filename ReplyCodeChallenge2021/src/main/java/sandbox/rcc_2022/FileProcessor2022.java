package sandbox.rcc_2022;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StopWatch;
import sandbox.CommonUtils;
import sandbox.FileProcessorInterface;
import sandbox.exceptions.RCCException;
import sandbox.exceptions.errors.ProcessorErrorCode;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.*;

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

    private List<Demon> defeatedDemonsPerStamina = new ArrayList<>();
    private List<Demon> defeatedDemonsPerFragments = new ArrayList<>();
    private Map<Integer, List<Demon>> defeatedDemonsPerTurn = new HashMap<>();
    private boolean defeatedDemonThisTurn;

    private int currentTurn = 1;
    private int totalScore;
    //endregion

    private List<Demon> demonsByStamina;
    private List<Demon> demonsByFinalReward;
    private List<Demon> demonsByStaminaRecoveryRate;


    private List<Integer> usedDemons;
    int notUsedDemons;

    @Override
    public void process(InputStream inputStream) {

    }

    @Override
    public void process(String line) {
        LOGGER.debug("Read: {}", line);
        String[] splitted = line.split(" ");
        if (lineRead == 0) {
            currentStamina.setValue(Integer.parseInt(splitted[0]));
            maxStamina.setValue(Integer.parseInt(splitted[1]));
            turnsAvailable.setValue(Integer.parseInt(splitted[2]));
            demonsAvailable.setValue(Integer.parseInt(splitted[3]));
        } else {
            Demon demon = Demon.createFromStringArray(splitted);
            if(demon.getRewardMap().size() > 0 && demon.getStaminaConsumedToFace() < maxStamina.getValue() &&
                demon.getRewardMap().get(demon.getRewardMap().size()) > demon.getRewardMap().size()
            )  {
                inputDemons.add(demon);
                demon.setId();
            } else {
                notUsedDemons ++;
            }
        }
        lineRead++;
    }

    @Override
    public void run() throws RCCException {
        if (!processed) {
            LOGGER.error("run invoked on processor without previous processing");
            throw new RCCException(ProcessorErrorCode.RUN_WITHOUT_PROCESSING);
        }

        LOGGER.info("Not used demons: [{}] / [{}]", notUsedDemons, demonsAvailable.getValue());

        defeatedDemonsPerStamina = new ArrayList<>();
        defeatedDemonsPerFragments = new ArrayList<>();
        defeatedDemonsPerTurn = new HashMap();

        usedDemons = new ArrayList<>();

        inputDemons.sort(Demon::compareByStamina);
        demonsByStamina = new ArrayList<>(inputDemons);
        Collections.reverse(demonsByStamina);


        inputDemons.sort(Demon::compareByFinalStaminaRecoveryRate);
        demonsByStaminaRecoveryRate = new ArrayList<>(inputDemons);

        Collections.reverse(demonsByStaminaRecoveryRate);

        inputDemons.sort((o1, o2) -> o1.compareByFinalReward(o2, turnsAvailable.getValue() - currentTurn));
        demonsByFinalReward = new ArrayList<>(inputDemons);

        Collections.reverse(demonsByFinalReward);

        gameLoop();

    }

    private void gameLoop() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < this.turnsAvailable.getValue(); i++) {

            if(currentStamina.getValue() < 0 || currentStamina.getValue() >= maxStamina.getValue()){
                System.err.println("Errore stamina");
            }


            inputDemons.sort((o1, o2) -> o1.compareByFinalReward(o2, turnsAvailable.getValue() - currentTurn));
            demonsByFinalReward = new ArrayList<>(inputDemons);

            Collections.reverse(demonsByFinalReward);

            recoverStamina();

            LOGGER.debug("turn: {}, stamina: {}", i, currentStamina);
            Demon d = chooseDemonToFace();

            if (d != null) {
                usedDemons.add(d.getId());
                faceDemon(d);
            }

            collectFragments();

            currentTurn++;
        }

        stopWatch.stop();
        LOGGER.info("Processing took: {} seconds", stopWatch.getTotalTimeSeconds());
        LOGGER.info("totalScore: [{}]", totalScore);
    }

    private void recoverStamina() {
        for(int i = 0; i < defeatedDemonsPerStamina.size(); i++) {
            Demon d = defeatedDemonsPerStamina.get(i);
            int remainingTurns = d.decreaseTurnToRecoverStamina(1);
            if (remainingTurns == 0) {
                defeatedDemonsPerStamina.remove(i);
                i--;
                increaseStamina(d.getRecoveredStamina());
                LOGGER.debug("Recovered {} stamina form deamon {}", d.getRecoveredStamina(), d.getId());
            }
        }
    }

    private Demon chooseDemonToFace() {
        if (currentTurn > this.turnsAvailable.getValue()) {
            return null;
        }

        Map<Integer, Integer> pointsByDemon = new HashMap<>();

        for (int i = 0; i < demonsByStamina.size(); i++) {
            Demon d = demonsByStamina.get(i);
            int id = d.getId();
            if (pointsByDemon.containsKey(id)) {
                pointsByDemon.put(id, pointsByDemon.get(id) + i);
            } else {
                pointsByDemon.put(id, i);
            }

            Demon d1 = demonsByFinalReward.get(i);
            if (pointsByDemon.containsKey(d1.getId())) {
                pointsByDemon.put(d1.getId(), pointsByDemon.get(d1.getId()) + i);
            } else {
                pointsByDemon.put(d1.getId(), i);
            }

            Demon d2 = demonsByStaminaRecoveryRate.get(i);
            if (pointsByDemon.containsKey(d2.getId())) {
                pointsByDemon.put(d2.getId(), pointsByDemon.get(d2.getId()) + i);
            } else {
                pointsByDemon.put(d2.getId(), i);
            }
        }

        List<Map.Entry<Integer, Integer>> demonsByPoints = new ArrayList<>(pointsByDemon.entrySet());
        demonsByPoints.sort(Map.Entry.comparingByValue());

        for (Map.Entry<Integer, Integer> demoncandidate : demonsByPoints) {
            Demon candidate = inputDemons.get(demoncandidate.getKey());

            if (canFaceDemon(candidate) && isStaminaEnough(candidate.getStaminaConsumedToFace())) {
                return candidate;
            }
        }
        return null;
    }

    private void collectFragments() {
        if (currentTurn > this.turnsAvailable.getValue()) {
            return;
        }

        List<Demon> toBeRemoved = new ArrayList<>();

        totalScore = 0;
        for(int i = 0; i < defeatedDemonsPerFragments.size(); i++) {
            Demon d = defeatedDemonsPerFragments.get(i);
            int key = currentTurn - d.getTurnDefeated() + 1;
            if(d.getRewardMap().get(key) == null) {
                defeatedDemonsPerFragments.remove(i);
                i--;
            } else {
                totalScore += d.getRewardMap().get(key);
            }
        }
    }

    @Override
    public void setProcessed() {
        this.processed = true;
    }

    private int loseStamina(int value) {
        int newStamina = this.currentStamina.getValue() - value;
        if (newStamina < 0) {
            newStamina = 0;
        }
        this.currentStamina.setValue(newStamina);
        return newStamina;
    }

    private int increaseStamina(int value) {
        int newStamina = this.currentStamina.getValue() + value;
        if (newStamina > this.maxStamina.getValue()) {
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

        LOGGER.debug("Fight demon {}, required stamina [{}]",d.getId(), d.getStaminaConsumedToFace());

        defeatedDemonsPerTurn.computeIfPresent(currentTurn, (t, list) -> {
            list.add(d);
            return list;
        });
        defeatedDemonsPerTurn.putIfAbsent(currentTurn, new ArrayList<>(Collections.singleton(d)));
        d.setTurnDefeated(currentTurn);
        defeatedDemonsPerStamina.add(d);
        defeatedDemonsPerFragments.add(d);


        loseStamina(d.getStaminaConsumedToFace());

        d.setFaced(true);

    }
}
