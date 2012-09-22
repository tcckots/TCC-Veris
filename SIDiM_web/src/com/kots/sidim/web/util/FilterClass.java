package com.kots.sidim.web.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kots.sidim.web.model.Cidade;

public class FilterClass implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		String loginURL = req.getContextPath() + "/Login.jsf";
		String uri = req.getRequestURI();
		// lista de excecoes:
		List<String> lstUrl = new ArrayList<String>();

		Boolean retorna = true;

		try {
			String user = (String) session.getAttribute("user");
			if (user != null) {
				if (user.trim().length() >= 0) {
					retorna = false;
				}
			}
		} catch (Exception e) {
			retorna = true;
		}
		
		if (uri.equals(loginURL)) {
			retorna = false;
		}

		if (retorna) {
			for (String item : lstUrl) {
				if (uri.equals(item)) {
					retorna = false;
				}
			}
		}
		


		if (retorna) {
			res.sendRedirect(loginURL);
			return;
		}

		if (!uri.equals(loginURL)) {
			session.setAttribute("user", "usuarioo");
		}
		
		filter.doFilter(req, res);

	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
