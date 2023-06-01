package ru.effectivemobile.socialnetwork.security.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ERole {
  ROLE_USER(Set.of(Permission.READ)),
  ROLE_ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.ADMIN_READ, Permission.ADMIN_WRITE));

  private final Set<Permission> permissions;

  ERole(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getAuthorities() {
    return getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
  }
}