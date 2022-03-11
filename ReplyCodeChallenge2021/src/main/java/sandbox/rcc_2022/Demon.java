package sandbox.rcc_2022;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Demon {
    private static int idGen = 0;

    private int id;
    private int staminaConsumedToFace;
    private int turnsToRecoverStamina;
    private int recoveredStamina;
    private int turnsEarningFragments;
    private double recoveryRate;
    private List<Integer> fragmentsEarnt = new ArrayList<>();
    private Map<Integer, Integer> rewardMap = new HashMap<>();
    private boolean faced;
    private int turnDefeated;

    public static Demon createFromStringArray(String[] a) {
        Demon d = new Demon();
        assert(a.length >= 4);
        int turnsEarningFragments = Integer.parseInt(a[3]);
        assert (a.length == 4 + turnsEarningFragments);
        d.staminaConsumedToFace = Integer.parseInt(a[0]);
        d.turnsToRecoverStamina = Integer.parseInt(a[1]);
        d.recoveredStamina = Integer.parseInt(a[2]);
        d.turnsEarningFragments = turnsEarningFragments;
        d.recoveryRate = 1.0*d.recoveredStamina/d.turnsToRecoverStamina;
        for (int i = 4; i < a.length; i++) {
            d.fragmentsEarnt.add(Integer.parseInt(a[i]));
        }

        int sum = 0;
        d.rewardMap.clear();
        for (int i = 0; i < d.fragmentsEarnt.size(); i++) {
            sum = sum + d.fragmentsEarnt.get(i);
            d.rewardMap.put(i + 1, sum);
        }

        d.faced = false;
        d.turnDefeated = 0;

        return d;
    }

    public int compareByStamina(Demon o) {
        return Integer.compare(this.staminaConsumedToFace, o.getStaminaConsumedToFace());
    }

    public int compareByFinalReward(Demon o, int turns) {

        return Integer.compare(this.getMaxReward(turns), o.getMaxReward(turns));
    }

    public int compareByFinalStaminaRecoveryRate(Demon o) {
        if (Double.compare(this.getStaminaRecoveryRate(), o.getStaminaRecoveryRate()) == 0) {
            return Integer.compare(this.turnsToRecoverStamina, o.getTurnsToRecoverStamina());
        } else {
            return Double.compare(this.getStaminaRecoveryRate(), o.getStaminaRecoveryRate());
        }
    }

    public double getStaminaRecoveryRate() {
        return recoveryRate;
    }

    public int getMaxReward(int turns) {

        if(rewardMap.size() == 0){
            return 0;
        }

        if (rewardMap.containsKey(turns)) {
            return rewardMap.get(turns);
        } else {
            return rewardMap.get(rewardMap.size());
        }
    }

    public int decreaseTurnToRecoverStamina(int value) {
        this.turnsToRecoverStamina -= value;
        if(this.turnsToRecoverStamina < 0) {
            this.turnsToRecoverStamina = 0;
        }
        return this.turnsToRecoverStamina;
    }

    public void setId() {
        this.setId(idGen);
        idGen++;
    }
}
