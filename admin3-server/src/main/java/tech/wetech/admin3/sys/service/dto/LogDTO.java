package tech.wetech.admin3.sys.service.dto;

import java.time.LocalDateTime;
import tech.wetech.admin3.sys.model.User;

/**
 * @author cjbi
 */
public record LogDTO(
    Long id,
    String content,
    String eventBody,
    String typeName,
    LocalDateTime occurredOn,
    User user) {}
