package com.ptconsultancy.admin.restoperations;

import static com.ptconsultancy.application.ApplicationConstants.SERVICES_RESOURCE_FILE;

import com.ptconsultancy.domain.utilities.FileUtilities;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class AllServices {

    private static final String SERVICE_NAME = "service";

    private ResourceBundleMessageSource allServicesSource;

    Map<String, Service> allServices = new HashMap<String, Service>();

    @Autowired
    public AllServices(ResourceBundleMessageSource allServicesSource) throws IOException {
        this.allServicesSource = allServicesSource;
        this.allServicesSource.setBasename("services");
        int filelength = FileUtilities.getFileLengthInLines(SERVICES_RESOURCE_FILE);
        for (int i = 1; i <= filelength; i++) {
            String key = SERVICE_NAME + String.valueOf(i);
            String prop = "";
            prop = this.allServicesSource.getMessage(key, new Object[]{}, null);
            Service service = new Service(prop, false);
            allServices.put(key, service);
        }
    }

    public Map<String, Service> getAllServices() {
        return allServices;
    }

    public void addService(Service service) {
        allServices.put(service.getName(), service);
    }

    public void removeService(String serviceName) {
        allServices.remove(serviceName);
    }

    public Service getServiceByName(String serviceName) {
        return allServices.get(serviceName);
    }

    public void displayAllServices() {
        Set<String> keys = allServices.keySet();
        Iterator iter = keys.iterator();
        System.out.println("All related services loaded through services.properties");
        while (iter.hasNext()) {
            String serviceIdentifier = (String) iter.next();
            System.out.println(allServices.get(serviceIdentifier).getName());
        }
    }
}
