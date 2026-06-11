package tech.wetech.admin3.common;

/**
 * @author cjbi
 */
public enum CommonResultStatus implements ResultStatus {
  OK(1000, "Success"),

  FAIL(1001, "Failed"),

  PARAM_ERROR(1002, "Invalid parameter"),

  RECORD_NOT_EXIST(1003, "Record does not exist"),

  UNAUTHORIZED(1004, "Unauthorized"),

  FORBIDDEN(1005, "Forbidden"),

  SERVER_ERROR(-1, "Internal server error");

  private final int code;
  private final String message;

  CommonResultStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
