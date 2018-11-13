package messages;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.ptconsultancy.messages.MessageHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

public class TestMessageHandler {

    private static final String TEST_KEY = "messages.something.something";
    private static final String PARAM_1 = "parameter1";
    private static final String PARAM_2 = "parameter2";

    private ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    private MessageHandler messagehandler;

    @Before
    public void setup() {
        messagehandler = new MessageHandler(messageSource);
        messageSource.setUseCodeAsDefaultMessage(true);
    }

    @Test
    public void testGetMessagesWithKeyOnly() {
        String message = messagehandler.getMessage(TEST_KEY);
        assertThat(message, is(TEST_KEY));
    }

    @Test
    public void testGetMessagesWithParameters() {
        String message = messagehandler.getMessage(TEST_KEY, new String[]{PARAM_1, PARAM_2});
        assertThat(message, is(TEST_KEY));
    }
}
