/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.domain.service;

import com.razzolim.food.domain.filter.VendaDiariaFilter;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public interface VendaReportService {
    
    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

}
