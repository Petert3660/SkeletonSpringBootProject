package admin.restoperations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ptconsultancy.admin.restoperations.AllServices;
import com.ptconsultancy.admin.restoperations.RestOperations;
import com.ptconsultancy.admin.restoperations.Service;
import org.junit.Before;

public class TestRestOperations {

    private static final String TEST_SERVICE_NAME = "testServiceName";
    private static final String TEST_ENDPOINT = "testEndpoint";

    AllServices allServices = mock(AllServices.class);
    Service service = mock(Service.class);
    RestOperations restOperations;

    @Before
    public void setup() {
        restOperations = new RestOperations(allServices);
        when(service.getName()).thenReturn(TEST_SERVICE_NAME);
        when(allServices.getServiceByName(TEST_SERVICE_NAME)).thenReturn(service);
    }
}
