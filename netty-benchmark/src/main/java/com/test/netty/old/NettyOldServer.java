package com.test.netty.old;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyOldServer {

  static final Logger logger = LogManager.getLogger(NettyOldServer.class.getName());

  private static NioServerSocketChannelFactory factory;

  public String nettyHost = "127.0.0.1";

  public int nettyPort = 8190;

  public int workerThreadCount = Runtime.getRuntime().availableProcessors() * 2;

  public void run() {
    if (workerThreadCount == 0)
      workerThreadCount = 10;
    factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
        Executors.newCachedThreadPool(), workerThreadCount);
    ServerBootstrap bootstrap = new ServerBootstrap(factory);
    bootstrap.setPipelineFactory(new HttpChannelPipelineFactory(workerThreadCount));
    logger.info("Starting Tracker server on {} {}", nettyHost, nettyPort);
    bootstrap.bind(new InetSocketAddress(nettyHost, nettyPort));
    addShutdownHook();
    logger.info("Tracker server Chal Gya)");
  }

  public void addShutdownHook() {
    // Do Nothing
  }

  public static void runOldNettyServer() {
    logger.info(" Starting runOldNettyServer :");
    new NettyOldServer().run();
  }
}
