package com.office.test.config;

import com.office.test.board.BoardAccessDeniedHandler;
import com.office.test.board.BoardAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.office.test.admin.AdminAccessDeniedHandler;
import com.office.test.admin.AdminAuthenticationEntryPoint;
import com.office.test.admin.AdminDetailService;
import com.office.test.member.MemberAccessDeniedHandler;
import com.office.test.member.MemberAuthenticationEntryPoint;
import com.office.test.member.MemberDetailService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	final private AdminDetailService adminDetailService;
	final private MemberDetailService memberDetailService;
	
	
	public SecurityConfig(AdminDetailService adminDetailService, MemberDetailService memberDetailService) {
		this.adminDetailService = adminDetailService;
		this.memberDetailService = memberDetailService;
		
	}
	
	@Bean PasswordEncoder passwordEncoder() {
		log.info("passwordEncoder()");
		
		return new BCryptPasswordEncoder();
		
	}
	
	// For Admin
	@Bean 
	@Order(1)
	SecurityFilterChain filterChainForAdmin(HttpSecurity http) throws Exception {
		log.info("filterChainForAdmin()");
		
		http
		.cors(cors -> cors.disable())
		.csrf(csrf -> csrf.disable());
		
		http
		.securityMatcher("/admin/**")
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/admin", 
						"/admin/create_admin_form", 
						"/admin/create_admin_confirm", 
						"/admin/create_admin_ok", 
						"/admin/create_admin_ng", 
						"/admin/login_admin_form",
						"/admin/authentication_entry_point").permitAll()
				.requestMatchers("/admin/service1/**").hasRole("ADMIN")
				.requestMatchers("/admin/service2/**").hasRole("ADMIN")
				.requestMatchers("/admin/member_list/**").hasRole("ADMIN")
				.anyRequest().authenticated());
		
		http
		.formLogin(login -> login
				.loginPage("/admin/login_admin_form")
				.loginProcessingUrl("/admin/login_admin_confirm")
				.usernameParameter("a_id")
				.passwordParameter("a_pw")
				.successHandler((request, response, authentication) -> {
					log.info("ADMIN LOGIN SUCCESS!!");
					
					response.sendRedirect("/admin");
					
				})
				.failureHandler((request, response, exception) -> {
					log.info("ADMIN LOGIN FAIL!!");
					
					response.sendRedirect("/admin/login_admin_form");
					
				}));
		
		http
		.logout(logout -> logout
				.logoutUrl("/admin/logout_admin_confirm")
				.logoutSuccessHandler((request, response, authentication) -> {
					log.info("ADMIN LOGOUT SUCCESS!!");
					
					response.sendRedirect("/admin");
					
				}));
		
		http
		.exceptionHandling(exceptionConfig -> exceptionConfig
				.authenticationEntryPoint(new AdminAuthenticationEntryPoint())
				.accessDeniedHandler(new AdminAccessDeniedHandler()));
		
		http
		.userDetailsService(adminDetailService);
		
		return http.build();
		
	}
	
	// For Member
	@Bean 
	@Order(2)
	SecurityFilterChain filterChainForMember(HttpSecurity http) throws Exception {
		log.info("filterChainForMember()");
		
		http
		.cors(cors -> cors.disable())
		.csrf(csrf -> csrf.disable());
		
		http
		.securityMatcher("/member/**")
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(
						"/member", 
						"/member/create_member_form", 
						"/member/create_member_confirm", 
						"/member/create_member_ok", 
						"/member/create_member_ng", 
						"/member/login_member_form",
						"/member/authentication_entry_point")
				.permitAll()
				.requestMatchers("/member/service1/**").hasRole("USER")
				.requestMatchers("/member/service2/**").hasRole("USER")
				.anyRequest().authenticated());
		
		http
		.formLogin(login -> login
				.loginPage("/member/login_member_form")
				.loginProcessingUrl("/member/login_member_confirm")
				.usernameParameter("m_id")
				.passwordParameter("m_pw")
				.successHandler((request, response, authentication) -> {
					log.info("MEMBER LOGIN SUCCESS!!");
					
					response.sendRedirect("/");
					
				})
				.failureHandler((request, response, exception) -> {
					log.info("MEMBER LOGIN FAIL!!");
					
					response.sendRedirect("/member/login_member_form");
					
				}));
		
		http
		.exceptionHandling(exceptionConfig -> exceptionConfig
				.authenticationEntryPoint(new MemberAuthenticationEntryPoint())
				.accessDeniedHandler(new MemberAccessDeniedHandler()));
		
		http
		.logout(logout -> logout
				.logoutUrl("/member/logout_member_confirm")
				.logoutSuccessHandler((request, response, authentication) -> {
					log.info("MEMBER LOGOUT SUCCESS!!");
					
					response.sendRedirect("/");
					
				}));
		
		http
		.userDetailsService(memberDetailService);
		
		return http.build();
		
	}

	// For board
	@Bean
	@Order(3)
	SecurityFilterChain filterChainForBoard(HttpSecurity http) throws Exception {
		log.info("filterChainForBoard()");

		http
				.cors(cors -> cors.disable())
				.csrf(csrf -> csrf.disable());

		http
				.securityMatcher("/board/**")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/board/authentication_entry_point").permitAll()
						.requestMatchers(
								"/board/**"
						).hasAnyRole("USER", "ADMIN"));

		http
				.exceptionHandling(exceptionConfig -> exceptionConfig
						.authenticationEntryPoint(new BoardAuthenticationEntryPoint())
						.accessDeniedHandler(new BoardAccessDeniedHandler()));

		return http.build();
	}
	
}
