package com.ptconsultancy.admin.restoperations;

import static com.ptconsultancy.application.ApplicationConstants.HEALTHCHECK;
import static com.ptconsultancy.application.ApplicationConstants.SECURITY_TOKEN;
import static com.ptconsultancy.application.ApplicationConstants.STANDARD_SEPARATOR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RestOperations {

    private AllServices allServices;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public RestOperations(AllServices allServices) {
        this.allServices = allServices;
    }

    public <T> T getForObject(Service service, String endpoint, Class<T> responseType) {
        String url = getUrl(service.getName(), endpoint);
        return restTemplate.getForObject(url, responseType);
    }

    private String getSecurityToken(Service service) {
        String secureUrl = service.getUrl() + STANDARD_SEPARATOR  + SECURITY_TOKEN;
        return restTemplate.getForObject(secureUrl, String.class);
    }

    private String getUrl(String serviceName, String endpoint) {

        Service service = allServices.getServiceByName(serviceName);
        String url = service.getUrl() + STANDARD_SEPARATOR + endpoint;
        if (!endpoint.equals(HEALTHCHECK)) {
            url = url + STANDARD_SEPARATOR + getSecurityToken(service);
        }

        return url;
    }
}
