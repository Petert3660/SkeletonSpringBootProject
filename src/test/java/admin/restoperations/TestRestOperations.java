package admin.restoperations;

import static com.ptconsultancy.application.ApplicationConstants.SECURITY_TOKEN;
import static com.ptconsultancy.application.ApplicationConstants.STANDARD_SEPARATOR;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptconsultancy.admin.restoperations.AllServices;
import com.ptconsultancy.admin.restoperations.RestOperations;
import com.ptconsultancy.admin.restoperations.Service;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TestRestOperations {

    private static final String PROTOCOL = "http://";
    private static final String TEST_SERVICE_NAME = "testServiceName";
    private static final String TEST_ENDPOINT = "testEndpoint";
    private static final String RESPONSE = "Everything OK";
    private static final String RETURNED_SECURITY_TOKEN = "a1b2c3";
    private static final String URL = PROTOCOL + TEST_SERVICE_NAME + STANDARD_SEPARATOR + TEST_ENDPOINT + STANDARD_SEPARATOR + RETURNED_SECURITY_TOKEN;

    private AllServices allServices = mock(AllServices.class);
    private Service service = mock(Service.class);
    private RestTemplate restTemplate = mock(RestTemplate.class);
    private RestOperations restOperations;
    private String body;
    private JsonNode bodyAsJson;

    @Before
    public void setup() throws IOException {
        restOperations = new RestOperations(allServices, restTemplate);
        body = "{\n\"name\":\"Jim\"\n}";
        ObjectMapper mapper = new ObjectMapper();
        bodyAsJson = mapper.readTree(body);
        when(service.getName()).thenReturn(TEST_SERVICE_NAME);
        when(service.getUrl()).thenReturn(PROTOCOL + TEST_SERVICE_NAME);
        when(allServices.getServiceByName(TEST_SERVICE_NAME)).thenReturn(service);
        when(restTemplate.getForObject(URL, String.class)).thenReturn(RESPONSE);
        when(restTemplate.getForObject(PROTOCOL + TEST_SERVICE_NAME + STANDARD_SEPARATOR + SECURITY_TOKEN, String.class)).thenReturn(RETURNED_SECURITY_TOKEN);
        when(restTemplate.postForObject(URL, bodyAsJson, String.class)).thenReturn(RESPONSE);
    }

    @Test
    public void test_get() {
        String response = restOperations.get(service, TEST_ENDPOINT, String.class);

        assertThat(response, is(RESPONSE));
    }

    @Test
    public void test_post() throws IOException {
        String response = restOperations.post(service, TEST_ENDPOINT, body);

        assertThat(response, is(RESPONSE));
    }

    @Test
    public void test_put() {

    }

    @Test
    public void test_delete() {

    }
}
