package com.ptconsultancy;

import static com.ptconsultancy.application.ApplicationConstants.GET_ADMIN_ID;
import static com.ptconsultancy.application.ApplicationConstants.GET_ADMIN_PASSWORD;
import static com.ptconsultancy.application.ApplicationConstants.HEALTHCHECK;

import com.ptconsultancy.admin.adminsupport.BuildVersion;
import com.ptconsultancy.admin.restoperations.AllServices;
import com.ptconsultancy.admin.restoperations.RestOperations;
import com.ptconsultancy.admin.restoperations.Service;
import com.ptconsultancy.messages.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Peter Thomson on 13/04/2018.
 */
@SuppressWarnings("ALL")
@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static final String PROPS_FILENAME = "application";
    private static final String SERVER_HOST = "spring.data.rest.base-path";
    private static final String SERVER_PORT_PROPERTY = "server.port";

    private static final int EXIT_STATUS = 0;

    @Autowired
    private Environment env;

    @Autowired
    MessageHandler messageHandler;

    @Autowired
    AllServices allServices;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
            .headless(false)
            .run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(messageHandler.getMessage("messages.ptconsultancy.messages"));
        outputMessage();
        populateDatabase();
        /* Running this will break the AdminController test,
         * so only run when starting up to test REST endpoints,
         * then comment out or remove.
         */
        //testRestOperations();
    }

    private void testRestOperations() {
        Service service = new Service("\\testservice", true);
        service.setUrl(env.getProperty(SERVER_HOST) + ":"
            + env.getProperty(SERVER_PORT_PROPERTY));
        allServices.displayAllServices();
        allServices.addService(service);
        RestOperations restOperations = new RestOperations(allServices, new RestTemplate());
        System.out.println("Testing REST operations by running local healthcheck - " + restOperations.get(service, HEALTHCHECK, String.class));
        System.out.println("Testing REST operations by running local admin get userId - " + restOperations.get(service, GET_ADMIN_ID, String.class));
        System.out.println("Testing REST operations by running local admin get password - " + restOperations.get(service, GET_ADMIN_PASSWORD, String.class));
        allServices.removeService("testservice");
    }

    private void outputMessage() {
        String hostname = env.getProperty(SERVER_HOST) + ":";
        String serverPort = env.getProperty(SERVER_PORT_PROPERTY);
        System.out.println("************************************************************************");
        if (BuildVersion.getProjectTitle() != null && BuildVersion.getBuildVersion() != null) {
            System.out.println("* " + BuildVersion.getProjectTitle() + ", Version: " + BuildVersion.getBuildVersion());
            System.out.println("************************************************************************");
        }
        if (BuildVersion.getProjectTitle() != null) {
            System.out.println("* " + BuildVersion.getProjectTitle() + " is now running on:- " + hostname + serverPort);
        } else {
            System.out.println("* This application is now running on:- " + hostname + serverPort);
        }
        System.out.println("************************************************************************");
    }

    // Use this method to set up test data from the application.properties file
    private void populateDatabase() {

//        if (customerRepository.findCustomerById((long) 11111).size() == 0) {
//            String prop;
//            int i = 1;
//            do {
//                String address = "customer" + String.valueOf(i++);
//                prop = env.getProperty(address);
//                if (!StringUtils.isEmpty(prop)) {
//                    String[] custDetails = prop.split(", ");
//                    customerRepository.save(new Customer(Long.parseLong(custDetails[0]), custDetails[1], custDetails[2], new Address()));
//                }
//            } while (!StringUtils.isEmpty(prop));
//        }
    }
}
