/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@Validated // anotado pq precisa pra @NotNull
@Getter
@Setter
@Component
@ConfigurationProperties("food.email")
public class EmailProperties {
    
    private Implementacao impl = Implementacao.FAKE;

    @NotNull
    private String remetente;

    public enum Implementacao {
        SMTP, FAKE
    }

}
