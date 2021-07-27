/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.infrastructure.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.razzolim.food.core.email.EmailProperties;
import com.razzolim.food.domain.service.EnvioEmailService;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Service
public class SmtpEnvioEmailService implements EnvioEmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setFrom(emailProperties.getRemetente());
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);
            
            mailSender.send(mimeMessage);
        } catch (Exception error) {
            throw new EmailException("Não foi possível enviar e-mail.", error);
        }
    }

}
