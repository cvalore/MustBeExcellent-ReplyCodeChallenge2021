package sandbox.rcc_2020;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sandbox.CommonUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingOrchestrator {

    private int numberOfBuildings = CommonUtils.NOT_INIT_INT_VALUE;
    private List<Building> buildings = null;
}
