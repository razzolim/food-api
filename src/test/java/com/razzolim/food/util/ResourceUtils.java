/*
 * COPYRIGHT RENAN AZZOLIM 2021 - ALL RIGHTS RESERVED.
 * 
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the prior
 * written consent of Renan Azzolim.
 */
package com.razzolim.food.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

/**
 * @author Renan Azzolim
 *
 * @since 
 * 
 */
public class ResourceUtils {
    
    public static String getContentFromResource(String resourceName) {
	    try {
	        InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
	        return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}      

}
