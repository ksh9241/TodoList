package com.toy.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.AllArgsConstructor;

/**
 * 인증 관리자 사용자 정의
 * */

@Configuration
@EnableWebSecurity	// 스프링 시큐리티 Config 설정이 스프링 필터체인에 등록된다.
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
/**
 * EnableGlobalMethodSecurity 속성 3 가지
 * 1. securedEnabled : @Secured 어노테이션을 사용하여 인가 처리를 하고 싶을 때 사용하는 옵션 (기본값  : false) [특정 메서드 호출 이전에 권한을 확인한다.]
 * 2. prePostEnabled : @PreAuthorize, @PostAuthorize 어노테이션을 사용하여 인가 처리를 하고 싶을 때 사용하는 옵션 (기본값 : false) [특정 메서드 호출 전, 후 이전에 권한을 확인한다.]
 * 3. jsr250Enabled : @RolesAllowed 어노테이션을 사용하여 인가 처리를 하고 싶을 떄 사용하는 옵션 (기본값 : false) [특정 메서드 호출 이전에 권한을 확인한다.]
 * */
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private final UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// SpringSecurity에서 사용하는 비밀번호 암호화 객체
		return new BCryptPasswordEncoder();
	}

	// 정적 데이터 Security 필터 예외 경로
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
				.antMatchers("/user/**").authenticated()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll()
				;
		
		http.formLogin()
			.loginPage("/loginPage")
			.usernameParameter("userName") // login 시 인풋 데이터 name값을 변경할 때 사용
			.loginProcessingUrl("/login") // /login 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
			.defaultSuccessUrl("/")
			.permitAll();
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/loginPage")
			.invalidateHttpSession(true);
		
		// 권한이 없는 사용자가 접근했을 경우 이동할 경로 지정
		http.exceptionHandling()
			.accessDeniedPage("/main"); 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
}
