package sandbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sandbox.rcc_2020.Sandbox2020;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Sandbox sandbox2020 = new Sandbox2020();
        sandbox2020.run();
    }
}
