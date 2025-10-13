package volenguyenbao.example;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InterfaceFieldModificationExample {
    private static final Logger logger =
            Logger.getLogger(InterfaceFieldModificationExample.class.getName());

    // Constants để trong cùng file, KHÔNG public
    static final class Constants {
        private Constants() {}
        static final int MAX = 10;
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "MAX value: {0}", Constants.MAX);
    }
}
