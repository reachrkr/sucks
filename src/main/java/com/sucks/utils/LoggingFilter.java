package com.sucks.utils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public final class LoggingFilter implements Filter 
{
  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
    FilterChain chain)
    throws IOException, ServletException 
  {

    long start = System.currentTimeMillis();
    String address =  request.getRemoteAddr();
    String file = ((HttpServletRequest) request).getRequestURI();
        
    chain.doFilter(request, response);

    filterConfig.getServletContext().log(
        "User access! " +      
        " User IP: " + address +      
        " Resource: " + file + 
        " Milliseconds used: " + (System.currentTimeMillis() - start) 
    );
  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }
}
