package tech.wetech.admin3.sys.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import tech.wetech.admin3.common.Constants;
import tech.wetech.admin3.common.SessionItemHolder;
import tech.wetech.admin3.sys.service.dto.UserinfoDTO;

/**
 * @author cjbi
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"key"})})
public class StorageFile extends BaseEntity {

  /** Unique index of the file */
  @Column(name = "`key`")
  private String key;

  /** File name */
  private String name;

  /** File type */
  private String type;

  /** File size */
  private Long size;

  private String createUser;

  private LocalDateTime createTime;

  private String storageId;

  @PrePersist
  protected void onCreate() {
    createTime = LocalDateTime.now();
    UserinfoDTO userInfo = (UserinfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
    createUser = userInfo.username();
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public String getStorageId() {
    return storageId;
  }

  public void setStorageId(String storageId) {
    this.storageId = storageId;
  }
}
