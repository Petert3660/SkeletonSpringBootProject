package admin.restoperations;

import static com.ptconsultancy.application.ApplicationConstants.SERVICES_RESOURCE_FILE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.ptconsultancy.admin.restoperations.AllServices;
import com.ptconsultancy.admin.restoperations.Service;
import com.ptconsultancy.domain.utilities.FileUtilities;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ResourceLoader;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"com.ptconsultancy.*", "org.springframework.context.support.ResourceBundleMessageSource"})
public class TestAllServices {

    private static final int NUM_SERVICES = 2;
    private static final String SERVICE1_KEY = "service1";
    private static final String SERVICE2_KEY = "service2";
    private static final String SERVICE3_KEY = "Service3";
    private static final String SERVICE1_NAME = "Service1";
    private static final String SERVICE2_NAME = "Service2";
    private static final String SERVICE3_NAME = "Service3";

    private AllServices allServices;

    private ResourceLoader resourceLoader = PowerMockito.mock(ResourceLoader.class);
    private ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();

    @Before
    public void setup() throws IOException {
        PowerMockito.mockStatic(FileUtilities.class);
        PowerMockito.when(FileUtilities.getFileLengthInLines(SERVICES_RESOURCE_FILE)).thenReturn(NUM_SERVICES);

        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    }

    @Test
    public void test_map_size() throws IOException {
        allServices = new AllServices(resourceBundleMessageSource, resourceLoader);
        assertThat(allServices.getAllServices().size(), is(NUM_SERVICES));
    }

    @Test
    public void test_map_contents() throws IOException {
        allServices = new AllServices(resourceBundleMessageSource, resourceLoader);
        Service service = allServices.getAllServices().get(SERVICE2_KEY);
        assertThat(service.getName(), is(SERVICE2_NAME));
        service = allServices.getAllServices().get(SERVICE1_KEY);
        assertThat(service.getName(), is(SERVICE1_NAME));
    }

    @Test
    public void test_add_service() throws IOException {
        allServices = new AllServices(resourceBundleMessageSource, resourceLoader);
        Service service = new Service("filepath\\" + SERVICE3_NAME, false);
        allServices.addService(service);
        assertThat(allServices.getAllServices().size(), is(NUM_SERVICES + 1));
        assertThat(allServices.getAllServices().get(SERVICE3_KEY).getName(), is(SERVICE3_NAME));
    }

    @Test
    public void test_remove_service() throws IOException {
        allServices = new AllServices(resourceBundleMessageSource, resourceLoader);
        Service service = new Service("filepath\\" + SERVICE3_NAME, false);
        allServices.addService(service);
        assertThat(allServices.getAllServices().size(), is(NUM_SERVICES + 1));
        assertThat(allServices.getAllServices().get(SERVICE3_KEY).getName(), is(SERVICE3_NAME));
        allServices.removeService(SERVICE1_NAME);
        assertThat(allServices.getAllServices().size(), is(NUM_SERVICES));
        assertNull(allServices.getAllServices().get(SERVICE1_KEY));
    }
}
