
package com.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomTest {

  private static Logger logger = LogManager.getLogger(RandomTest.class);

  public static void main(String[] args) {
    logger.info("In Main Class");
    logger.debug(" In Main Class Debug");
  }
}
