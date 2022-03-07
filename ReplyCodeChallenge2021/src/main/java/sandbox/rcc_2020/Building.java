package sandbox.rcc_2020;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Building {

    private int id;
    private int x;
    private int y;
    private int latency;
    private int connectionSpeed;

}
