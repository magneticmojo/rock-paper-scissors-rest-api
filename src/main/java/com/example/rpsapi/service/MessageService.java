package com.example.rpsapi.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String id) {
        Locale locale = LocaleContextHolder.getLocale();
        // todo -> locale and null might not be necessary
        return messageSource.getMessage(id, null, locale);
    }
}

