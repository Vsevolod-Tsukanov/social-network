package ru.effectivemobile.socialnetwork.security.services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.effectivemobile.socialnetwork.model.User;
import ru.effectivemobile.socialnetwork.security.model.ERole;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

	private Long id;

	private String username;

	private String email;
	@JsonIgnore
	private String password;

	private ERole role;

	private Collection<? extends GrantedAuthority> authorities;



	public static UserDetailsImpl build(User user) {
		Set<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();

		return new UserDetailsImpl(
				user.getId(), 
				user.getUserName(),
				user.getEmail(),
				user.getPassword(),
				user.getRole(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
