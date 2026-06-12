package tech.wetech.admin3.common;

/**
 * @author cjbi
 */
public interface ResultStatus {
  /** Error code */
  int getCode();

  /** Error message */
  String getMessage();
}
