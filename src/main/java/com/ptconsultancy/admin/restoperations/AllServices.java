package com.ptconsultancy.admin.restoperations;

import static com.ptconsultancy.application.ApplicationConstants.SERVICES_RESOURCE_FILE;

import com.ptconsultancy.domain.utilities.FileUtilities;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class AllServices {

    private static final String SERVICES_NAME = "services";
    private static final String SERVICE = "service";

    private ResourceBundleMessageSource allServicesSource;

    @Autowired
    private ResourceLoader resourceLoader;

    private Map<String, Service> allServices = new HashMap<String, Service>();

    @Autowired
    public AllServices(ResourceBundleMessageSource allServicesSource, ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        this.allServicesSource = allServicesSource;
        this.allServicesSource.setBasename(SERVICES_NAME);
        int filelength = getResourceLength();
        for (int i = 1; i <= filelength; i++) {
            Service service = new Service(this.allServicesSource.getMessage(SERVICE + String.valueOf(i), null, new Locale("gb_en")), false);
            allServices.put(SERVICE + String.valueOf(i), service);
        }
    }

    private int getResourceLength() throws IOException {
        try {
            return FileUtilities.getFileLengthInLines(SERVICES_RESOURCE_FILE);
        } catch (FileNotFoundException fnf) {
            return new String(FileCopyUtils.copyToByteArray(
                new ClassPathResource(SERVICES_NAME + ".properties").getInputStream()), StandardCharsets.UTF_8).split("\n").length;
        }
    }

    public Map<String, Service> getAllServices() {
        return allServices;
    }

    public void addService(Service service) {
        allServices.put(service.getName(), service);
    }

    public void removeService(String serviceName) {
        Iterator iter = allServices.keySet().iterator();
        while (iter.hasNext()) {
            String currentKey = (String) iter.next();
            if (allServices.get(currentKey).getName().equals(serviceName)) {
                allServices.remove(currentKey);
                break;
            }
        }
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
