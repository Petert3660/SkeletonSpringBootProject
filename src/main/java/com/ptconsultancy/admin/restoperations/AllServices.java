package com.ptconsultancy.admin.restoperations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class AllServices {

    private static final String SERVICES_NAME = "services";

    private ResourceBundleMessageSource allServicesSource;

    Map<String, Service> allServices = new HashMap<String, Service>();

    @Autowired
    public AllServices(ResourceBundleMessageSource allServicesSource) {
        this.allServicesSource = allServicesSource;
        this.allServicesSource.setBasename("services");
        int i = 1;
        String prop = "";
        do {
            String key = "service" + String.valueOf(i++);
            try {
                prop = this.allServicesSource.getMessage(key, new Object[]{}, null);
            } catch (Exception e) {
                break;
            }
            Service service = new Service(prop, false);
            allServices.put(key, service);
        } while (!StringUtils.isEmpty(prop));
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
