package com.ptconsultancy.admin.restoperations;

import static com.ptconsultancy.application.ApplicationConstants.HEALTHCHECK;
import static com.ptconsultancy.application.ApplicationConstants.SECURITY_TOKEN;
import static com.ptconsultancy.application.ApplicationConstants.STANDARD_SEPARATOR;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class RestOperations {

    private AllServices allServices;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public RestOperations(AllServices allServices) {
        this.allServices = allServices;
    }

    public <T> T get(Service service, String endpoint, Class<T> responseType) {
        String url = getUrl(service.getName(), endpoint);
        return restTemplate.getForObject(url, responseType);
    }

    public String post(Service service, String endpoint, String body) throws IOException {
        String url = getUrl(service.getName(), endpoint);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode request = mapper.readTree(body);
        return restTemplate.postForObject(url, request, String.class);
    }

    public void delete(Service service, String endpoint) throws URISyntaxException {
        URI uri = new URI(getUrl(service.getName(), endpoint));
        restTemplate.delete(uri);
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
