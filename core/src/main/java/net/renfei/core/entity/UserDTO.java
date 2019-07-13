package net.renfei.core.entity;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    Long id;
    String name;
    String username;
    String password;
    String email;
    private Set<RoleDTO> roles = new HashSet<>();
}
