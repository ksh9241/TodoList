package com.toy.todo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) 
/**
 * EnableGlobalMethodSecurity 속성 3 가지
 * 1. securedEnabled : @Secured 어노테이션을 사용하여 인가 처리를 하고 싶을 때 사용하는 옵션 (기본값  : false) [특정 메서드 호출 이전에 권한을 확인한다.]
 * 2. prePostEnabled : @PreAuthorize, @PostAuthorize 어노테이션을 사용하여 인가 처리를 하고 싶을 때 사용하는 옵션 (기본값 : false) [특정 메서드 호출 전, 후 이전에 권한을 확인한다.]
 * 3. jsr250Enabled : @RolesAllowed 어노테이션을 사용하여 인가 처리를 하고 싶을 떄 사용하는 옵션 (기본값 : false) [특정 메서드 호출 이전에 권한을 확인한다.]
 * */
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 정적 데이터 Security 필터 예외 경로
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//.antMatchers("/**").authenticated()
				.antMatchers("/auth/**").permitAll();
		
		http.formLogin()
			.loginPage("/auth/login")
			.defaultSuccessUrl("/main")
			.permitAll();
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/auth/login")
			.invalidateHttpSession(true);
		
		// 권한이 없는 사용자가 접근했을 경우 이동할 경로 지정
		http.exceptionHandling()
			.accessDeniedPage("/auth/home"); 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService()
	}
}
