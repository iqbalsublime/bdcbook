package com.bdcyclists.bdcbook.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
public class ServletUtils {
    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    public static String getContextURL(String appendPath) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(appendPath)
                .build()
                .toUriString();
    }
}
