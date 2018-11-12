package admin.restcontrollers;

import static com.ptconsultancy.admin.adminsupport.ControllerConstants.DEFAULT_HEALTHCHECK_MESSAGE;
import static com.ptconsultancy.admin.adminsupport.ControllerConstants.NO_TOKEN_MESSAGE;
import static com.ptconsultancy.application.ApplicationConstants.GET_ADMIN_ID;
import static com.ptconsultancy.application.ApplicationConstants.GET_ADMIN_PASSWORD;
import static com.ptconsultancy.application.ApplicationConstants.HEALTHCHECK;
import static com.ptconsultancy.application.ApplicationConstants.SECURITY_TOKEN;
import static com.ptconsultancy.application.ApplicationConstants.STANDARD_SEPARATOR;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ptconsultancy.Application;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TestAdminController {

    private static final String TEST_SECURITY_TOKEN = "11111111";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRespondOkForHealthcheck() throws Exception {
        getAssignmentRequest(HEALTHCHECK)
            .andExpect(status().isOk());
    }

    @Test
    public void shouldRespondWithHealthcheckDefaultMessage() throws Exception {
        getAssignmentRequest(HEALTHCHECK)
            .andExpect(content().string  (DEFAULT_HEALTHCHECK_MESSAGE));
    }

    @Test
    public void shouldRespondOkForSecuritytoken() throws Exception {
        getAssignmentRequest(SECURITY_TOKEN)
            .andExpect(status().isOk());
    }

    @Test
    public void shouldRespondNoTokenMessageForGetId() throws Exception {
        getAssignmentRequest(GET_ADMIN_ID + STANDARD_SEPARATOR + TEST_SECURITY_TOKEN)
            .andExpect(content().string(NO_TOKEN_MESSAGE));
    }

    @Test
    public void shouldRespondNoTokenMessageForGetPassword() throws Exception {
        getAssignmentRequest(GET_ADMIN_PASSWORD + STANDARD_SEPARATOR + TEST_SECURITY_TOKEN)
            .andExpect(content().string(NO_TOKEN_MESSAGE));
    }

    private ResultActions getAssignmentRequest(String endpoint) throws Exception, IOException {
        return mockMvc.perform(MockMvcRequestBuilders.get(STANDARD_SEPARATOR + endpoint)
            .contentType(MediaType.TEXT_PLAIN));
    }
}
