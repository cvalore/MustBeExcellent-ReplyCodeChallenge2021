package sandbox.rcc_2022;

import lombok.*;

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
    private int staminaConsumedToFace;
    private int turnsToRecoverStamina;
    private int recoveredStamina;
    private int turnsEarningFragments;
    private List<Integer> fragmentsEarnt = new ArrayList<>();
    private Map<Integer, Integer> rewardMap = new HashMap<>();
    private boolean faced;

    public static Demon createFromStringArray(String[] a) {
        Demon d = new Demon();

        assert(a.length >= 4);
        int turnsEarningFragments = Integer.parseInt(a[3]);
        assert(a.length == 4 + turnsEarningFragments);
        d.staminaConsumedToFace = Integer.parseInt(a[0]);
        d.turnsToRecoverStamina = Integer.parseInt(a[1]);
        d.recoveredStamina = Integer.parseInt(a[2]);
        d.turnsEarningFragments = turnsEarningFragments;
        for(int i = 4; i < a.length; i++) {
            d.fragmentsEarnt.add(Integer.parseInt(a[i]));
        }

        int sum = 0;
        d.rewardMap.clear();
        for(int i = 0; i < d.fragmentsEarnt.size(); i++) {
            sum = sum + d.fragmentsEarnt.get(i);
            d.rewardMap.put(i+1, sum);
        }

        d.faced = false;

        return d;
    }
}
