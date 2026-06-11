package tech.wetech.admin3.sys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import tech.wetech.admin3.common.BusinessException;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.common.SecurityUtil;

import java.security.NoSuchAlgorithmException;

/**
 * User credential
 *
 * @author cjbi
 */
@Entity
public class UserCredential extends BaseEntity {

  /**
   * Identifier (mobile, email, username or third-party app unique identifier)
   */
  @Column(nullable = false)
  private String identifier;
  /**
   * Password credential (save password for internal, don't save or save token for external)
   */
  @Column(nullable = false)
  private String credential;
  /**
   * Login type (mobile, email, username) or third-party app name (WeChat, Weibo, etc.)
   */
  private IdentityType identityType;

  @ManyToOne
  private User user;


  public UserCredential() {
  }

  public boolean doCredentialMatch(String credential) {
    try {
      //TODO Other login methods not yet implemented
      if (this.getIdentityType() != IdentityType.PASSWORD || !SecurityUtil.md5(identifier, credential).equals(this.getCredential())) {
        return false;
      }
    } catch (NoSuchAlgorithmException e) {
      throw new BusinessException(CommonResultStatus.FAIL, "Password encryption failed: " + e.getMessage());
    }
    return true;
  }

  public enum IdentityType {
    PASSWORD
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getCredential() {
    return credential;
  }

  public IdentityType getIdentityType() {
    return identityType;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public void setCredential(String credential) {
    this.credential = credential;
  }

  public void setIdentityType(IdentityType identityType) {
    this.identityType = identityType;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
