package tech.wetech.admin3.sys.service.dto;

import java.time.LocalDateTime;
import java.util.Set;
import tech.wetech.admin3.sys.model.Role;
import tech.wetech.admin3.sys.model.User;

/**
 * @author cjbi
 */
public record RoleUserDTO(
    Long id,
    String username,
    String avatar,
    User.Gender gender,
    User.State state,
    Set<Role> roles,
    LocalDateTime createdTime) {}
