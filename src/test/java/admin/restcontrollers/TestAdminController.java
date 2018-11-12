package admin.restcontrollers;

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

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldRespondOkForValidRequest() throws Exception {
        getAssignmentRequest()
            .andExpect(status().isOk());
    }

    private ResultActions getAssignmentRequest() throws Exception, IOException {
        return mockMvc.perform(MockMvcRequestBuilders.get("/healthcheck")
            .contentType(MediaType.TEXT_PLAIN));
    }
}
