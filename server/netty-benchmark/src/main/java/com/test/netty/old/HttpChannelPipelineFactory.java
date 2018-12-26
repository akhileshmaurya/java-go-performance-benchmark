package com.test.netty.old;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;

public class HttpChannelPipelineFactory implements ChannelPipelineFactory {

	int workerThreadCount;

	public HttpChannelPipelineFactory(int workerThreadCount) {
		this.workerThreadCount = workerThreadCount;
	}

	/**
	 * 
	 */
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		addHandlers(pipeline);
		return pipeline;
	}

	/**
	 * 
	 * @param pipeline
	 */
	public void addHandlers(ChannelPipeline pipeline) {
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("deflater", new HttpContentCompressor());
		pipeline.addLast("aggregator", new HttpChunkAggregator(65536));
		pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());
		pipeline.addLast("handler", new HttpRequestHandler(workerThreadCount));
	}
}
