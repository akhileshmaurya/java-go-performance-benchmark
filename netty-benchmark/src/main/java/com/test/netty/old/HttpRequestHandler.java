package com.test.netty.old;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpHeaders.Values;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

public class HttpRequestHandler extends SimpleChannelUpstreamHandler {

  static final Logger LOGGER = LogManager.getLogger(NettyOldServer.class.getName());

  private int workerThreadCount;

  public HttpRequestHandler(int workerThreadCount) {
    this.workerThreadCount = workerThreadCount;

  }

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent messageEvent) {
    LOGGER.debug("message recieved In Tracker ", workerThreadCount);
    HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    HttpRequest request = (HttpRequest) messageEvent.getMessage();
    response.setContent(ChannelBuffers.copiedBuffer("SUCCESS".getBytes()));
    boolean keepAlive = HttpHeaders.isKeepAlive(request);
    response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
    if (!keepAlive) {
      ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
    } else {
      response.setHeader(CONNECTION, Values.KEEP_ALIVE);
      response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
      ctx.getChannel().write(response);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    try {
      LOGGER.error("error in Handler: {}", e.getCause());

      e.getChannel().close();
    } catch (Exception ex) {
      LOGGER.error("ERROR trying to close socket because we got an unhandled exception",
          e.getCause());
    }
  }
}
