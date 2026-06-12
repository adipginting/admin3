package tech.wetech.admin3.sys.service.dto;

import java.io.Serializable;
import java.util.Set;
import tech.wetech.admin3.sys.model.UserCredential;

/**
 * @author cjbi
 */
public record UserinfoDTO(
    String token,
    Long userId,
    String username,
    String avatar,
    Credential credential,
    Set<String> permissions)
    implements Serializable {

  public record Credential(String identifier, UserCredential.IdentityType type) {}
}
