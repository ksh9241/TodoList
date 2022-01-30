package com.toy.todo.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.toy.todo.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO implements UserDetails{

	private String userId;
	
	private String userName;
	
	private String password;
	
	private String phoneNumber;
	
	private LocalDateTime cretDt;
	
	private LocalDateTime chgDt;
	
	private Double achievementRate;
	
	private List<Board> boards;
	
	// Security Instance
	private String AUTHORITY;
	private boolean ENABLED;

	// 계정을 갖고있는 권한 목록을 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority(AUTHORITY));
		return auth;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	// 계정이 만료되지 않았는지 리턴한다. ( true : 만료안됨 )
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않았는지 리턴한다. ( true : 잠기지 않음 )
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다. ( true : 만료안됨 )
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용가능) 인지 리턴한다. ( true : 활성화 )
	@Override
	public boolean isEnabled() {
		return ENABLED;
	}
}
