package com.ptconsultancy.admin.restoperations;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class AllServices {

    Map<String, Service> allServices = new HashMap<String, Service>();

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
}
