package com.ptconsultancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by Peter Thomson on 13/04/2018.
 */
@SuppressWarnings("ALL")
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final String PROPS_FILENAME = "application";
    private static final String SERVER_PORT_PROPERTY = "server.port";

    private static final int EXIT_STATUS = 0;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
            .headless(false)
            .run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("This is a working program - replace this code with your own code to create required Application!");
        System.out.println("EXITING NOW!");
        System.exit(EXIT_STATUS);
    }
}
