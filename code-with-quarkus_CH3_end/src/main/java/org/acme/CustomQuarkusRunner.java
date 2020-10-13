package org.acme;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CustomQuarkusRunner {

    public static void main(String[] args) {
        Quarkus.run(StartupHandler.class,args);
    }

    public static class StartupHandler implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            handleStartup(args);
            Quarkus.waitForExit();
            return 0; //or 1, depending on the termination conditions
        }

        public static void handleStartup(String[] parameters) {
            //handle parameters or Quarkus.asyncExit();
        }
    }

}
