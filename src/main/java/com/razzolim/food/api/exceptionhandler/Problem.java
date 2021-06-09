/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.api.exceptionhandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Renan Azzolim
 *
 * @since
 * 
 */
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

    /* padrão RFC 7807 */
    private Integer status;    
    private String type;    
    private String title;    
    private String detail;
    
    private String userMessage;
    private LocalDateTime timestamp;

}
