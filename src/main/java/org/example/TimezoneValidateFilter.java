package org.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain)
            throws IOException, ServletException {
        String timezone = httpServletRequest.getParameter("timezone");
        if (timezone != null) {
            if (isValidTimezone(timezone)) {
                chain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.setStatus(400);
                httpServletResponse.setContentType("text/plain; charset=utf-8");
                httpServletResponse.getWriter().write("Invalid timezone");
                httpServletResponse.getWriter().close();
            }
        } else {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private boolean isValidTimezone(String timezone) {
        String id = timezone.replace("UTC", "Etc/GMT");
        return Set.of(TimeZone.getAvailableIDs()).contains(id);
    }

}
