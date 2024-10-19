package market.lib.config.webmvc;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import market.lib.constant.Constant;

public class EncodingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    request.setCharacterEncoding(Constant.ENCODING_UTF8);
    chain.doFilter(request, response);
  }
}