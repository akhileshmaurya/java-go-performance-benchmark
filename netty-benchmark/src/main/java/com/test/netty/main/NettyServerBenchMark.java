package com.test.netty.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.test.netty.latest.NettyNewServer;
import com.test.netty.old.NettyOldServer;

public class NettyServerBenchMark {
  static final Logger logger = LogManager.getLogger(NettyServerBenchMark.class.getName());

  public static void main(String[] args) throws Exception {
    logger.info(" Calling NettyServerBenchMark ");
    boolean isNewServer = true;
    if (args != null && args.length > 0) {
      logger.info("The command line  arguments are:");
      if ("false".equalsIgnoreCase(args[0]) || "f".equalsIgnoreCase(args[0])) {
        isNewServer = false;
      }
    }
    if (isNewServer)
      NettyNewServer.runNewNettyServer();
    else
      NettyOldServer.runOldNettyServer();
  }
}
