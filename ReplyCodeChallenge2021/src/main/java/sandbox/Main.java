package sandbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.rcc_2020.Sandbox2020;
import sandbox.rcc_2022.Sandbox2022;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Sandbox sandbox2022 = new Sandbox2022();
        sandbox2022.run();
    }
}
