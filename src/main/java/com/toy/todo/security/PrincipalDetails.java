package com.toy.todo.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.toy.todo.domain.User;

/** 
* 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
* 로그인을 진행이 완료가 되면 시큐리티 session을 만들어준다. (Security ContextHolder)
* 오브젝트 => Authentication 타입 객체
* Authentication 안에 User정보가 있어야 됨.
* User오브젝트 타입 => UserDetails 타입 객체
* 
* Security Session(Authentication (UserDetails (PrincipalDetails))) => Authentication (UserDetails (PrincipalDetails)) => UserDetails (PrincipalDetails)
*/
public class PrincipalDetails implements UserDetails {
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	// 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new SimpleGrantedAuthority(user.getRole()));
		return collect;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserId();
	}

	// 계정 만료됐니?
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정 잠겼니?
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호 기간이 오래되었니?
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화 되어있니?
	@Override
	public boolean isEnabled() {
		return true;
	}

}
