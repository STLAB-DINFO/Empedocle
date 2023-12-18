package it.unifi.ing.stlab.commons.pdf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PDFRendererFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) 
			throws IOException, ServletException {
		
		String type = req.getParameter("type");
		if (type == null) {
			filterChain.doFilter(req, resp);
		} else {
			new PDFRendererForcedFilter().doFilter(req, resp, filterChain);
		}
	}

}