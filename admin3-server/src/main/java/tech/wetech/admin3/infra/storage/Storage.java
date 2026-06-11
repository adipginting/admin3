package tech.wetech.admin3.infra.storage;


import java.io.InputStream;

/**
 * @author cjbi
 */
public interface Storage {

  String getId();

  /**
   * Store a file object
   *
   * @param inputStream   File input stream
   * @param contentLength File length
   * @param contentType   File type
   * @param filename      File name
   */
  void store(InputStream inputStream, long contentLength, String contentType, String filename);

  InputStream getFileContent(String filename);

  void delete(String filename);

  String getUrl(String filename);
}
