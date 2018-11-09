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

    private RestTemplate restTemplate;

    @Autowired
    public RestOperations(AllServices allServices, RestTemplate restTemplate) {
        this.allServices = allServices;
        this.restTemplate = restTemplate;
    }

    public <T> T get(Service service, String endpoint, Class<T> responseType) {
        String url = getUrl(service.getName(), endpoint);
        return restTemplate.getForObject(url, responseType);
    }

    public String post(Service service, String endpoint, String body) throws IOException {
        String url = getUrl(service.getName(), endpoint);
        return restTemplate.postForObject(url, createRequest(body), String.class);
    }

    public void delete(Service service, String endpoint) throws URISyntaxException {
        URI uri = new URI(getUrl(service.getName(), endpoint));
        restTemplate.delete(uri);
    }

    public void put(Service service, String endpoint, String body) throws URISyntaxException, IOException {
        URI uri = new URI(getUrl(service.getName(), endpoint));
        restTemplate.put(uri, createRequest(body));
    }

    private JsonNode createRequest(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(body);
    }

    private String getSecurityToken(Service service) {
        return restTemplate.getForObject(service.getUrl() + STANDARD_SEPARATOR  + SECURITY_TOKEN, String.class);
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
