package com.office.test.board;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Log4j2
public class BoardAccessDeniedHandler implements AccessDeniedHandler {

	// PRE_USER, PRE_ADMIN
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.info("handle()");

		StringBuffer authorityNames = new StringBuffer();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority authority : authentication.getAuthorities())
			authorityNames.append(authority.getAuthority());

		if (authorityNames.toString().contains("ADMIN"))
			response.sendRedirect("/admin/access_denied");

		if (authorityNames.toString().contains("USER"))
		response.sendRedirect("/member/access_denied");



	}

}
