package tech.wetech.admin3.infra.service;

import java.io.Serializable;
import tech.wetech.admin3.sys.model.UserCredential;

/**
 * @author cjbi
 */
public interface SessionManager {

  void store(String key, UserCredential credential, Serializable value);

  void invalidate(String key);

  Object get(String key);

  void refresh();
}
