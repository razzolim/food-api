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

import lombok.extern.slf4j.Slf4j;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

    @Override
    public void enviar(Mensagem mensagem) {
        
        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }
}
