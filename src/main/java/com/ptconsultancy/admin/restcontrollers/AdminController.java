package com.ptconsultancy.admin.restcontrollers;

import com.ptconsultancy.admin.adminsupport.ControllerConstants;
import com.ptconsultancy.messages.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private MessageHandler messageHandler;

    @Autowired
    public AdminController(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @RequestMapping(path="/healthcheck", method=RequestMethod.GET)
    public String healthcheck() {
        return "Running OK";
    }

    @RequestMapping(path="/shutdown/{id}/{pass}", method=RequestMethod.POST)
    public void shutdown(@PathVariable String id, @PathVariable String pass) {
        if (id.equals(messageHandler.getMessage(ControllerConstants.ID_KEY)) && pass.equals(ControllerConstants.PASS_KEY)) {
            System.exit(ControllerConstants.EXIT_STATUS);
        }
    }
}
