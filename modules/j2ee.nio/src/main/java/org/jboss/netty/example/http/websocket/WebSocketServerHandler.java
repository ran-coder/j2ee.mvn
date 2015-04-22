/* Copyright 2010 Red Hat, Inc. Red Hat licenses this file to you under the Apache License, version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at: http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.jboss.netty.example.http.websocket;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocket.DefaultWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocket.WebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.setContentLength;
import static org.jboss.netty.handler.codec.http.HttpMethod.GET;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 * @version $Rev: 2314 $, $Date: 2010-06-22 16:02:27 +0900 (Tue, 22 Jun 2010) $
 */
@SuppressWarnings("deprecation")
public class WebSocketServerHandler extends SimpleChannelUpstreamHandler {
	protected Logger						log							=LoggerFactory.getLogger(getClass());
	private static final String	WEBSOCKET_PATH	="/websocket";
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Object msg=e.getMessage();
		log.debug("{}",msg.getClass());
		if(msg instanceof HttpRequest){
			handleHttpRequest(ctx,(HttpRequest)msg);
		}else if(msg instanceof WebSocketFrame){
			handleWebSocketFrame(ctx,(WebSocketFrame)msg);
		}
	}
	
	private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
		log.debug(req.toString());
		// Allow only GET methods.
		if(req.getMethod() != GET){
			sendHttpResponse(ctx,req,new DefaultHttpResponse(HTTP_1_1,FORBIDDEN));
			return;
		}
		
		// Send the demo page.
		if(req.getUri().equals("/")){
			HttpResponse res=new DefaultHttpResponse(HTTP_1_1,OK);
			
			ChannelBuffer content=WebSocketServerIndexPage.getContent(getWebSocketLocation(req));
			
			res.setHeader(CONTENT_TYPE,"text/html; charset=UTF-8");
			setContentLength(res,content.readableBytes());
			
			res.setContent(content);
			sendHttpResponse(ctx,req,res);
			return;
		}
		//Websocket 握手开始  
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, false);
		WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
		if(handshaker == null){  
			wsFactory.sendUnsupportedWebSocketVersionResponse(ctx.getChannel());
		}else{
			handshaker.handshake(ctx.getChannel(), req).addListener(  
					WebSocketServerHandshaker.HANDSHAKE_LISTENER);
		}
		// Serve the WebSocket handshake request.
		/*boolean isWebSocket=req.getUri().equals(WEBSOCKET_PATH) 
						&& StringUtil.isIndexIgnoreCase(req.getHeader(CONNECTION),Values.UPGRADE)
						&& StringUtil.isIndexIgnoreCase(req.getHeader(Names.UPGRADE),WEBSOCKET)
						//&& WEBSOCKET.equalsIgnoreCase(req.getHeader(Names.UPGRADE))
						;
		if(isWebSocket){
			// Create the WebSocket handshake response.
			HttpResponse res=new DefaultHttpResponse(HTTP_1_1,new HttpResponseStatus(101,"Web Socket Protocol Handshake"));
			res.addHeader(Names.UPGRADE,WEBSOCKET);
			res.addHeader(CONNECTION,Values.UPGRADE);
			
			// Fill in the headers and contents depending on handshake method.
			if(req.containsHeader(SEC_WEBSOCKET_KEY1) && req.containsHeader(SEC_WEBSOCKET_KEY2)){
				// New handshake method with a challenge:
				res.addHeader(SEC_WEBSOCKET_ORIGIN,req.getHeader(ORIGIN));
				res.addHeader(SEC_WEBSOCKET_LOCATION,getWebSocketLocation(req));
				String protocol=req.getHeader(SEC_WEBSOCKET_PROTOCOL);
				if(protocol != null){
					res.addHeader(SEC_WEBSOCKET_PROTOCOL,protocol);
				}
				
				// Calculate the answer of the challenge.
				String key1=req.getHeader(SEC_WEBSOCKET_KEY1);
				String key2=req.getHeader(SEC_WEBSOCKET_KEY2);
				int a=(int)(Long.parseLong(key1.replaceAll("[^0-9]","")) / key1.replaceAll("[^ ]","").length());
				int b=(int)(Long.parseLong(key2.replaceAll("[^0-9]","")) / key2.replaceAll("[^ ]","").length());
				long c=req.getContent().readLong();
				ChannelBuffer input=ChannelBuffers.buffer(16);
				input.writeInt(a);
				input.writeInt(b);
				input.writeLong(c);
				ChannelBuffer output=ChannelBuffers.wrappedBuffer(MessageDigest.getInstance("MD5").digest(input.array()));
				res.setContent(output);
			}else{
				// Old handshake method with no challenge:
				res.addHeader(WEBSOCKET_ORIGIN,req.getHeader(ORIGIN));
				res.addHeader(WEBSOCKET_LOCATION,getWebSocketLocation(req));
				String protocol=req.getHeader(WEBSOCKET_PROTOCOL);
				if(protocol != null){
					res.addHeader(WEBSOCKET_PROTOCOL,protocol);
				}
			}
			
			// Upgrade the connection and send the handshake response.
			ChannelPipeline p=ctx.getChannel().getPipeline();
			p.remove("aggregator");
			p.replace("decoder","wsdecoder",new WebSocketFrameDecoder());
			
			ctx.getChannel().write(res);
			
			p.replace("encoder","wsencoder",new WebSocketFrameEncoder());
			return;
		}
		
		// Send an error page otherwise.
		sendHttpResponse(ctx,req,new DefaultHttpResponse(HTTP_1_1,FORBIDDEN));*/
	}
	
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		log.debug("handleWebSocketFrame");
		// Send the uppercased string back.
		ctx.getChannel().write(new DefaultWebSocketFrame(frame.getTextData().toUpperCase()));
	}
	
	private void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
		log.debug(res.toString());
		// Generate an error page if response status code is not OK (200).
		if(res.getStatus().getCode() != 200){
			res.setContent(ChannelBuffers.copiedBuffer(res.getStatus().toString(),CharsetUtil.UTF_8));
			setContentLength(res,res.getContent().readableBytes());
		}
		
		// Send the response and close the connection if necessary.
		ChannelFuture f=ctx.getChannel().write(res);
		if(!isKeepAlive(req) || res.getStatus().getCode() != 200){
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		log.debug("exceptionCaught");
		e.getCause().printStackTrace();
		e.getChannel().close();
	}
	
	private String getWebSocketLocation(HttpRequest req) {
		return "ws://" + req.getHeader(HttpHeaders.Names.HOST) + WEBSOCKET_PATH;
	}
}
