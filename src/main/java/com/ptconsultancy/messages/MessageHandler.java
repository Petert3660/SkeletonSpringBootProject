package com.ptconsultancy.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler {

    private ResourceBundleMessageSource messageSource;

    @Autowired
    public MessageHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        setMessageSourceSource(key.split("\\.")[0]);
        return messageSource.getMessage(key, null, null);
    }

    public String getMessage(String key, String[] params) {
        setMessageSourceSource(key.split("\\.")[0]);
        Object[] args = params;
        return messageSource.getMessage(key, args, null);
    }

    private void setMessageSourceSource(String newBaseName) {
        messageSource.setBasename(newBaseName);
    }
}
