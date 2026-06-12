package tech.wetech.admin3.sys.service.dto;

import java.time.LocalDateTime;
import tech.wetech.admin3.sys.model.User;

/**
 * @author cjbi
 */
public record OrgUserDTO(
    Long id,
    String username,
    String avatar,
    User.Gender gender,
    User.State state,
    String orgFullName,
    LocalDateTime createdTime,
    LocalDateTime lastLoginTime) {}
