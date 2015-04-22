package nio.netty.example.chapter1;


import nio.netty.example.SimpleServer;
import nio.netty.example.chapter1.handler.TimeDecoder;
import nio.netty.example.chapter1.handler.TimeServerHandler;

public class TimeServer {

	public static void main(String[] args) throws Exception {
		new SimpleServer(8080).start(
				new TimeDecoder(),
				new TimeServerHandler()
		);
	}
}