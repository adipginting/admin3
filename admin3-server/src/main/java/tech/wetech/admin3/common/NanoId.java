package tech.wetech.admin3.common;

import java.security.SecureRandom;
import java.util.Random;

/**
 * NanoId, a small, secure, URL-friendly unique string ID generator, features:
 *
 * <ul>
 *     <li>Secure: It uses cryptographically strong random APIs and guarantees proper symbol distribution</li>
 *     <li>Small: Only 258 bytes (compressed), zero dependencies</li>
 *     <li>Compact: It uses more symbols than UUID (A-Za-z0-9_~)</li>
 * </ul>
 *
 * <p>
 * This implementation is based on the JavaScript NanoId implementation, see: https://github.com/ai/nanoid
 *
 * @author David Klebanoff
 */
public class NanoId {

  /**
   * Default random number generator, uses {@link SecureRandom} for robustness
   */
  private static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();

  /**
   * Default random alphabet, uses URL-safe Base64 characters
   */
  private static final char[] DEFAULT_ALPHABET =
    "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  /**
   * Default length
   */
  public static final int DEFAULT_SIZE = 21;

  /**
   * Generate a pseudo-random NanoId string with the default length {@link #DEFAULT_SIZE}, using a cryptographically secure pseudo-random generator
   *
   * @return Pseudo-random NanoId string
   */
  public static String randomNanoId() {
    return randomNanoId(DEFAULT_SIZE);
  }

  /**
   * Generate a pseudo-random NanoId string
   *
   * @param size ID length
   * @return Pseudo-random NanoId string
   */
  public static String randomNanoId(int size) {
    return randomNanoId(null, null, size);
  }

  /**
   * Generate a pseudo-random NanoId string
   *
   * @param random   Random number generator
   * @param alphabet Random alphabet
   * @param size     ID length
   * @return Pseudo-random NanoId string
   */
  public static String randomNanoId(Random random, char[] alphabet, int size) {
    if (random == null) {
      random = DEFAULT_NUMBER_GENERATOR;
    }

    if (alphabet == null) {
      alphabet = DEFAULT_ALPHABET;
    }

    if (alphabet.length == 0 || alphabet.length >= 256) {
      throw new IllegalArgumentException("Alphabet must contain between 1 and 255 symbols.");
    }

    if (size <= 0) {
      throw new IllegalArgumentException("Size must be greater than zero.");
    }

    final int mask = (2 << (int) Math.floor(Math.log(alphabet.length - 1) / Math.log(2))) - 1;
    final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);

    final StringBuilder idBuilder = new StringBuilder();

    while (true) {
      final byte[] bytes = new byte[step];
      random.nextBytes(bytes);
      for (int i = 0; i < step; i++) {
        final int alphabetIndex = bytes[i] & mask;
        if (alphabetIndex < alphabet.length) {
          idBuilder.append(alphabet[alphabetIndex]);
          if (idBuilder.length() == size) {
            return idBuilder.toString();
          }
        }
      }
    }
  }

}
