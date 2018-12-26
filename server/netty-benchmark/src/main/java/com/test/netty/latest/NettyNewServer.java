package com.test.netty.latest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

public class NettyNewServer {
	static final Logger logger = LogManager.getLogger(NettyNewServer.class.getName());

	private Channel ch;

	public void run() throws Exception {
		final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		final EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
		try {
			final ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT);
			bootStrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new WebSocketServerInitializer());
			ch = bootStrap.bind("localhost", 8190).sync().channel();
			ch.closeFuture().sync();

		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void runNewNettyServer() throws Exception {
		logger.info(" Starting NettyNewServer");
		new NettyNewServer().run();
	}

	private class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
		@Override
		public void initChannel(final SocketChannel ch) throws Exception {
			final ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast("http-request-decoder", new HttpRequestDecoder());
			pipeline.addLast("aggregator", new HttpObjectAggregator(Integer.MAX_VALUE));
			pipeline.addLast("http-response-encoder", new HttpResponseEncoder());
			// pipeline.addLast("request-handler", new
			// WebSocketServerProtocolHandler("/websocket"));
			pipeline.addLast("handler", new HttpServerHandler());
		}
	}

	public class HttpServerHandler extends ChannelInboundHandlerAdapter {
		private static final String CONTENT = "SUCCESS";

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			logger.info(" Got Request Now Processing ");
			if (msg instanceof HttpRequest) {
				HttpRequest req = (HttpRequest) msg;
				final FullHttpRequest fReq = (FullHttpRequest) req;
				final ByteBuf buf = fReq.content();
				buf.release();
				boolean keepAlive = HttpUtil.isKeepAlive(req);
				FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
						Unpooled.wrappedBuffer(CONTENT.getBytes()));
				response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
				response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
				if (!keepAlive) {
					ctx.write(response).addListener(ChannelFutureListener.CLOSE);
				} else {
					response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
					ctx.write(response);
				}
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			// cause.printStackTrace();
			logger.info("Got UnHandled Exception");
			ctx.close();

		}

	}
}
