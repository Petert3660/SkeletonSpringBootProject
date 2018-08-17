package com.ptconsultancy.admin.restcontrollers;

import com.ptconsultancy.admin.adminsupport.BuildVersion;
import com.ptconsultancy.admin.adminsupport.ControllerConstants;
import com.ptconsultancy.messages.MessageHandler;
import com.ptconsultancy.utilities.GenerateRandomKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private MessageHandler messageHandler;

    private String lastSecurityToken;

    private boolean tokenLock = false;

    @Autowired
    public AdminController(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @RequestMapping(path="/healthcheck", method=RequestMethod.GET)
    public String healthcheck() {
        if (BuildVersion.getProjectTitle() != null) {
            return BuildVersion.getProjectTitle() + " is running OK";
        } else {
            return "Application is running OK";
        }
    }

    @RequestMapping(path="/shutdown/{id}/{pass}/{token}", method=RequestMethod.POST)
    public void shutdown(@PathVariable String id, @PathVariable String pass, @PathVariable String token) {
        if (id.equals(messageHandler.getMessage(ControllerConstants.ID_KEY)) && pass.equals(messageHandler.getMessage(ControllerConstants.PASS_KEY))
            && token.equals(lastSecurityToken)) {
            tokenLock = false;
            System.exit(ControllerConstants.EXIT_STATUS);
        }
    }

    @RequestMapping(path="/securitytoken", method=RequestMethod.GET)
    public String getSecurityToken() {

        if (!tokenLock) {
            lastSecurityToken = GenerateRandomKeys.generateRandomKey(ControllerConstants.TOKEN_LENGTH, ControllerConstants.TOKEN_MODE);
            tokenLock = true;
            return lastSecurityToken;
        } else {
            return ControllerConstants.NO_TOKEN_MESSAGE;
        }
    }
}
